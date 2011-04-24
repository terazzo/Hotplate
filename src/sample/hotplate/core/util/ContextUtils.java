package sample.hotplate.core.util;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public final class ContextUtils {
    private ContextUtils() {
    }
    // symbol�ɑ΂���value��߂�Context�𐶐�����B
    public static <V, T extends Template<V, T>> Context<V, T>
        newContext(final Symbol symbol, final T value) {
        return new Context<V, T>() {
            public T get(Symbol s) {
                return symbol.equals(s) ? value : null;
            }
        };
    }
    // symbol�ɑ΂���value��߂��A����ȊO�ɂ�context�̖߂�l��߂�Context�𐶐�����B
    public static <V, T extends Template<V, T>> Context<V, T>
            put(final Context<V, T> context, final Symbol symbol, final T value) {
        return merge(newContext(symbol, value), context);
    }
    // ���Context����������Bfirst��T���A�l���������next��T���B
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
        };
    }
    @SuppressWarnings("rawtypes")
    private static Context EMPTY_CONTEXT = 
        new Context() {
            public Template get(Symbol name) {
                return null;
            }
        };
    // ���Context��߂��B
    @SuppressWarnings("unchecked")
    public static <V, T extends Template<V, T>>
            Context<V, T> emptyContext() {
        return EMPTY_CONTEXT;
    }
}
