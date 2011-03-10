package sample.hotplate.core.impl;

import sample.hotplate.core.Template;
import sample.hotplate.core.Value;

public class ValueBase<R, T extends Template<R, T>> implements Value<R, T> {
	
	private R value;

	public ValueBase(R value) {
		super();
		this.value = value;
	}
	@Override
	public Value<R, T> asValue() {
		return this;
	}
	@Override
	public R value() {
		return value;
	}
	@Override
	public T asTemplate() {
		throw new IllegalStateException("Not template:" + getClass());
	}
	@Override
	public boolean isTemplate() {
		return false;
	}
}
