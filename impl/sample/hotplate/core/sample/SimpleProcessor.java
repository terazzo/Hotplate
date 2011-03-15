package sample.hotplate.core.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;

public class SimpleProcessor implements SimpleTemplate {

	private final Symbol symbol;

	public SimpleProcessor(Symbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
		SimpleTemplate value = context.get(symbol);
		if (value == null) {
			return new SimpleTemplatePair(this, context);
		}
		return new SimpleTemplatePair(value, context);
	}

	@Override
	public boolean isReducible() {
		return true;
	}
	@Override
	public String getString() {
		throw new IllegalStateException("Unbound variable:" + symbol);
	}

	@Override
	public int hashCode() {
		return symbol.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SimpleProcessor)) {
			return false;
		}
		return symbol.equals(((SimpleProcessor) obj).symbol);
	}

    @Override
    public boolean isPrototype() {
        return false;
    }

}
