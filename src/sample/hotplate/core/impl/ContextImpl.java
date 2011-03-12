package sample.hotplate.core.impl;

import java.util.HashMap;
import java.util.Map;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;

public class ContextImpl<R, T extends Template<R, T>> implements Context<R, T> {
	private Map<Symbol, T> scope = new HashMap<Symbol, T>();
	
	public void put(Symbol symbol, T value) {
		scope.put(symbol, value);
	}
	@Override
	public T get(Symbol symbol) {
		return scope.get(symbol);
	}

}
