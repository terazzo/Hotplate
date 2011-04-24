package sample.hotplate.core;

import sample.hotplate.core.util.ContextUtils;

public class TemplatePair<V, T extends Template<V, T>> {
    private final T template;
    private final Context<V, T> context;

    private TemplatePair(T template, Context<V, T> context) {
        this.template = template;
        this.context = context;
    }
    public T template() {
        return template;
    }
    public Context<V, T> context() {
        return context;
    }
    public static <V, T extends Template<V, T>>
    TemplatePair<V, T> pairOf(T template, Context<V, T> context) {
        return new TemplatePair<V, T>(template, context);
    }
    public static <V, T extends Template<V, T>>
    TemplatePair<V, T> pairOf(T template) {
        return pairOf(template, ContextUtils.<V, T>emptyContext());
    }
}
