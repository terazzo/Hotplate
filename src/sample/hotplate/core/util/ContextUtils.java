package sample.hotplate.core.util;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public final class ContextUtils {
    private ContextUtils() {
    }
    // symbolに対してvalueを戻すContextを生成する。
    public static <V, T extends Template<V, T>> Context<V, T>
        newContext(final Symbol symbol, final T value) {
        return new Context<V, T>() {
            public T get(Symbol s) {
                return symbol.equals(s) ? value : null;
            }
        };
    }
    // symbolに対してvalueを戻し、それ以外にはcontextの戻り値を戻すContextを生成する。
    public static <V, T extends Template<V, T>> Context<V, T>
            put(final Context<V, T> context, final Symbol symbol, final T value) {
        return merge(newContext(symbol, value), context);
    }
    // 二つのContextを合成する。firstを探し、値が無ければnextを探す。
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
    // 空のContextを戻す。
    @SuppressWarnings("unchecked")
    public static <V, T extends Template<V, T>>
            Context<V, T> emptyContext() {
        return EMPTY_CONTEXT;
    }
}
