package sample.hotplate.core.util;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public final class ContextUtils {
    private ContextUtils() {
    }
    public static <V, T extends Template<V, T>> Context<V, T>
            put(final Context<V, T> context, final Symbol symbol, final T value) {
        return new Context<V, T>() {
            public T get(Symbol s) {
                if (s.equals(symbol)) {
                    return value;
                }
                return context.get(s);
            }
            @Override
            public String toString() {
                return String.format("<Contexts: %s, {%s=%s}>", context, symbol, value);
            }
        };
    }
    public static <V, T extends Template<V, T>> Context<V, T>
            merge(final Context<V, T> first, final Context<V, T> next) {
        return new Context<V, T>() {
            public T get(Symbol s) {
                T value = first.get(s);
                if (value != null) {
                    return value;
                }
                return next.get(s);
            }
            @Override
            public String toString() {
                return String.format("<Contexts: %s, %s>", first, next);
            }
        };
    }
    @SuppressWarnings("rawtypes")
    private static Context EMPTY_CONTEXT = 
        new Context() {
            public Template get(Symbol name) {
                return null;
            }
        };
    @SuppressWarnings("unchecked")
    public static <V, T extends Template<V, T>>
            Context<V, T> emptyContext() {
        return EMPTY_CONTEXT;
    }
}
