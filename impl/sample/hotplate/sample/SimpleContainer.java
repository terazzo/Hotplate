package sample.hotplate.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleContainer extends AbstractSimpleTemplate implements SimpleTemplate {
    protected final List<SimpleTemplate> elements;
    private final boolean isReducible;
    public SimpleContainer(Context<Object, SimpleTemplate> lexicalContext, List<SimpleTemplate> elements) {
        super(lexicalContext);
        this.elements = Collections.unmodifiableList(elements);
        for (SimpleTemplate element : elements) {
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
    protected TemplatePair<Object, SimpleTemplate> doApply(
            Context<Object, SimpleTemplate> context) {
        if (!isReducible()) {
            return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
        }
       
        List<SimpleTemplate> newElements = new ArrayList<SimpleTemplate>();
        Context<Object, SimpleTemplate> newContext = ContextUtils.emptyContext();
        for (SimpleTemplate element : elements) {
            Context<Object, SimpleTemplate> merged = ContextUtils.merge(newContext, context);
            TemplatePair<Object, SimpleTemplate> applied = element.apply(merged);
            newElements.add(applied.template());
            newContext = ContextUtils.merge(applied.context(), newContext);
        }
        return TemplatePairUtils.pairOf(new SimpleContainer(context, newElements), newContext);
    }

    @Override
    public String getString() {
        StringBuilder containerContents = new StringBuilder();
        for (SimpleTemplate element: elements) {
            containerContents.append(element.getString());
        }
        return containerContents.toString();
    }
    private static SimpleTemplate nop =
        new SimpleContainer(
                ContextUtils.<Object, SimpleTemplate>emptyContext(),
                Collections.<SimpleTemplate>emptyList());
    public static SimpleTemplate nop() {
        return nop;
    }
}
