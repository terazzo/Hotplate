package sample.hotplate.core.sample;

import sample.hotplate.core.impl.LiteralBase;

public class SimpleLiteral extends LiteralBase<Object, SimpleTemplate> implements SimpleTemplate {

	public SimpleLiteral(Object value) {
		super(value);
	}
	@Override
    protected SimpleTemplate concreteThis() {
		return this;
	}
	@Override
	public String getString() {
		return value().toString();
	}
    @Override
    public boolean isPrototype() {
        return false;
    }
}
