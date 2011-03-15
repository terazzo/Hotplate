package sample.hotplate.core.sample2.processors;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.sample2.SimpleTemplate;

public class SimpleProcessor implements SimpleTemplate {

	private final Symbol symbol;

	public SimpleProcessor(Symbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public SimpleTemplate apply(Context<Object, SimpleTemplate> context) {
		SimpleTemplate value = context.get(symbol);
		if (value == null) {
			return this;
		}
		return value;
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

}
