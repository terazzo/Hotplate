package sample.hotplate.core.util;

import java.util.HashMap;
import java.util.Map;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public class ContextBuilder<V, T extends Template<V, T>> {
    private final Map<Symbol, Associable<V, T>> scope;
    public ContextBuilder() {
        this.scope = new HashMap<Symbol, Associable<V, T>>();
    }
    private ContextBuilder(Map<Symbol, Associable<V, T>> scope) {
        this.scope = scope;
    }
    public ContextBuilder<V, T> put(Symbol symbol, Associable<V, T> value) {
        HashMap<Symbol, Associable<V, T>> newScope = new HashMap<Symbol, Associable<V, T>>(scope);
        newScope.put(symbol, value);
        return new ContextBuilder<V, T>(newScope);
    }
    public Context<V, T> context() {
        return new ContextImpl<V, T>(scope);
    }
    
    private static class ContextImpl<V, T extends Template<V, T>> implements Context<V, T> {
        private final Map<Symbol, Associable<V, T>> scope;
        private ContextImpl(Map<Symbol, Associable<V, T>> scope) {
            this.scope = scope;
        }
        @Override
        public Associable<V, T> get(Symbol symbol) {
            return scope.get(symbol);
        }
    }

}
