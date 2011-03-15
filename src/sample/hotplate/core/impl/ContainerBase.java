package sample.hotplate.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;

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
    public boolean isPrototype() {
        return false;
    }

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        if (!isReducible()) {
            return TemplatePairImpl.of(getConcrete(), context);
        }
        List<T> newElements = new ArrayList<T>();
        Context<V, T> lastContext = context;
        for (T element : elements) {
            if (element.isPrototype()) {
                TemplatePair<V, T> concreate = element.apply(lastContext);
                element = concreate.template();
            }
            TemplatePair<V, T> result = element.apply(lastContext);
            newElements.add(result.template());
            lastContext = result.context();
        }
        return new TemplatePairImpl<V, T>(getConcrete(newElements), lastContext);
    }
    protected abstract T getConcrete();
    protected abstract T getConcrete(List<T> newElements);

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(getClass().isInstance(obj))) {
            return false;
        }
        return elements.equals(getClass().cast(obj).elements);
    }

}
