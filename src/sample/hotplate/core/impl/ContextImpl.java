package sample.hotplate.core.impl;

import java.util.HashMap;
import java.util.Map;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.Template;

public class ContextImpl<R, T extends Template<R, T>> implements Context<R, T> {
	private Map<String, Associable<R, T>> scope = new HashMap<String, Associable<R,T>>();
	
	@Override
	public void put(String name, Associable<R, T> value) {
		scope.put(name, value);
	}
	@Override
	public Associable<R, T> get(String name) {
		return scope.get(name);
	}

}
