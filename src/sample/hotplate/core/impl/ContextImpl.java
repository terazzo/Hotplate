package sample.hotplate.core.impl;

import java.util.HashMap;
import java.util.Map;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public class ContextImpl<R, T extends Template<R, T>> implements Context<R, T> {
	private final Map<Symbol, T> scope;
    public ContextImpl() {
        this.scope = new HashMap<Symbol, T>();
    }
    private ContextImpl(Map<Symbol, T> scope) {
        this.scope = scope;
    }
	public ContextImpl<R, T> put(Symbol symbol, T value) {
	    HashMap<Symbol, T> newScope = new HashMap<Symbol, T>(scope);
	    newScope.put(symbol, value);
	    return new ContextImpl<R, T>(newScope);
	}
	@Override
	public T get(Symbol symbol) {
		return scope.get(symbol);
	}
	

}
