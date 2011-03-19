package sample.hotplate.core.util;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;

public final class TemplatePairUtils {
    private TemplatePairUtils() {
    }
    public static <V, T extends Template<V, T>>
        TemplatePair<V, T> pairOf(T template, Context<V, T> context) {
        return new TemplatePairImpl<V, T>(template, context);
    }
    public static <V, T extends Template<V, T>>
        TemplatePair<V, T> pairOf(T template) {
        return pairOf(template, ContextUtils.<V, T>emptyContext());
    }
    
    private static class TemplatePairImpl<V, T extends Template<V,T>>
            implements TemplatePair <V, T>{
        private final T template;
        private final Context<V, T> context;

        public TemplatePairImpl(T template, Context<V, T> context) {
            this.template = template;
            this.context = context;
        }

        @Override
        public T template() {
            return template;
        }

        @Override
        public Context<V, T> context() {
            return context;
        }
    }

}
