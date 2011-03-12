package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;

public abstract class LiteralBase<V, T extends Template<V, T>> implements Template<V, T> {

	private V value;
	public LiteralBase(V value) {
		this.value = value;
	}
	@Override
	public boolean isReducible() {
		return false;
	}
	public V value() {
		return value;
	}
	@Override
	public T apply(Context<V, T> context) {
		return getConcrete();
	}
	protected abstract T getConcrete();

}
