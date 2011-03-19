package sample.hotplate.sample;

import sample.hotplate.core.impl.LiteralBase;

public class SimpleLiteral extends LiteralBase<Object, SimpleTemplate> implements SimpleTemplate {

	public SimpleLiteral(Object value) {
		super(value);
	}
    @Override
    protected SimpleTemplate concreteThis() {
        return this;
    }
    public String toString() {
        return String.format("'%s'", value().toString());
    }
}
