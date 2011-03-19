package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class LiteralBase<V, T extends Template<V, T>> implements Template<V, T> {

	private V value;
	public LiteralBase(V value) {
		this.value = value;
	}
	public boolean isReducible() {
		return false;
	}
    public boolean isPrototype() {
        return false;
    }
	public V value() {
		return value;
	}
	@Override
	public TemplatePair<V, T> apply(Context<V, T> context) {
		return TemplatePairUtils.pairOf(concreteThis());
	}
	protected abstract T concreteThis();

}
