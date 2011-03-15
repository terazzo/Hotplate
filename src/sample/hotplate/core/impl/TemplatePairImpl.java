package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;

public class TemplatePairImpl<V, T extends Template<V,T>> implements TemplatePair <V, T>{
    private final T template;
    private final Context<V, T> context;

    public TemplatePairImpl(T template, Context<V, T> context) {
        this.template = template;
        this.context = context;
    }
    public static <V, T extends Template<V, T>>
            TemplatePair<V, T> of(T template, Context<V, T> context) {
        return new TemplatePairImpl<V, T>(template, context);
    }

    @Override
    public T template() {
        return template;
    }

    @Override
    public Context<V, T> context() {
        return context;
    }

    @Override
    public int hashCode() {
        return template.hashCode() * 37 + context.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(getClass().isInstance(obj))) {
            return false;
        }
        return template.equals(getClass().cast(obj).template)
            && context.equals(getClass().cast(obj).context);
    }

}
