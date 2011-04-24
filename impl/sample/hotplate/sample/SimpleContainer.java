package sample.hotplate.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;

public class SimpleContainer implements SimpleTemplate {
    private final List<SimpleTemplate> elements;
    private final boolean isReducible;

    public SimpleContainer(List<SimpleTemplate> elements) {
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
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        if (!isReducible()) {
            return TemplatePair.<Object, SimpleTemplate>pairOf(this);
        }
       
        List<SimpleTemplate> newElements = new ArrayList<SimpleTemplate>();
        Context<Object, SimpleTemplate> newContext = ContextUtils.emptyContext();
        for (SimpleTemplate element : elements) {
            Context<Object, SimpleTemplate> merged = ContextUtils.merge(newContext, context);
            TemplatePair<Object, SimpleTemplate> applied = element.apply(merged);
            newElements.add(applied.template());
            newContext = ContextUtils.merge(applied.context(), newContext);
        }
        return TemplatePair.pairOf(new SimpleContainer(newElements), newContext);
    }

    @Override
    public String getString() {
        StringBuilder containerContents = new StringBuilder();
        for (SimpleTemplate element: elements) {
            containerContents.append(element.getString());
        }
        return containerContents.toString();
    }
}
