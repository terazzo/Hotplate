package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;

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
		return TemplatePairImpl.of(getConcrete(), context);
	}
	protected abstract T getConcrete();

	
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(getClass().isInstance(obj))) {
            return false;
        }
        return value.equals(getClass().cast(obj).value);
    }

}
