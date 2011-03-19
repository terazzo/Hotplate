package sample.hotplate.core.util;

import java.util.HashMap;
import java.util.Map;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public class ContextBuilder<V, T extends Template<V, T>> {
    private final Map<Symbol, T> scope;
    public ContextBuilder() {
        this.scope = new HashMap<Symbol, T>();
    }
    private ContextBuilder(Map<Symbol, T> scope) {
        this.scope = scope;
    }
    public ContextBuilder<V, T> put(Symbol symbol, T value) {
        HashMap<Symbol, T> newScope = new HashMap<Symbol, T>(scope);
        newScope.put(symbol, value);
        return new ContextBuilder<V, T>(newScope);
    }
    public Context<V, T> context() {
        return new ContextImpl<V, T>(scope);
    }
    
    private static class ContextImpl<R, T extends Template<R, T>> implements Context<R, T> {
        private final Map<Symbol, T> scope;
        private ContextImpl(Map<Symbol, T> scope) {
            this.scope = scope;
        }
        @Override
        public T get(Symbol symbol) {
            return scope.get(symbol);
        }
    }

}
