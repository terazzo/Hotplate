package sample.hotplate.core.impl;

import sample.hotplate.core.Template;
import sample.hotplate.core.Value;

public abstract class TemplateBase<R, T extends Template<R, T>> implements Template<R, T> {

	@Override
	public boolean isTemplate() {
		return true;
	}

	@Override
	public Value<R, T> asValue() {
		throw new IllegalStateException("Not value:" + getClass());
	}

	@Override
	public Template<R, T> asTemplate() {
		return this;
	}
}
