package sample.hotplate.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.TemplateWalker;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class ContainerBase<V, T extends Template<V, T>> implements Template<V, T>{
    protected final List<T> elements;
    private final boolean isReducible;
    public ContainerBase(List<T> elements) {
        this.elements = Collections.unmodifiableList(elements);
        for (T element : elements) {
            if (element.isReducible()) {
                this.isReducible = true;
                return;
            }
        }
        isReducible = false;
    }
    @Override
    public boolean isReducible() {
        return isReducible;
    }

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        if (!isReducible()) {
            return TemplatePairUtils.pairOf(concreteThis());
        }
       
        List<T> newElements = new ArrayList<T>();
        Context<V, T> newContext = ContextUtils.emptyContext();
        for (T element : elements) {
            Context<V, T> merged = ContextUtils.merge(newContext, context);
            TemplatePair<V, T> applied = element.apply(merged);
            newElements.add(applied.template());
            newContext = ContextUtils.merge(applied.context(), newContext);
        }
        return TemplatePairUtils.pairOf(newInstance(newElements), newContext);
    }
    protected abstract T concreteThis();
    protected abstract T newInstance(List<T> newElements);

    @Override
    public void traverse(TemplateWalker<V, T> walker) {
        walker.process(this);
        for (T element : elements) {
            element.traverse(walker);
        }
    }
}
