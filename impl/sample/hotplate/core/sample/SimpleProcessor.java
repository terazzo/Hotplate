package sample.hotplate.core.sample;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.impl.TemplateBase;

public class SimpleProcessor extends TemplateBase<Object, SimpleTemplate> implements SimpleTemplate{

	private final String varname;

	public SimpleProcessor(String varname) {
		this.varname = varname;
	}

	@Override
	public SimpleTemplate apply(Context<Object, SimpleTemplate> context) {
		Associable<Object, SimpleTemplate> associable = context.get(varname);
		if (associable != null) {
			return new LiteralTemplate(associable.asValue().value().toString());
		}
		return this;
	}

	@Override
	public boolean isReducible() {
		return true;
	}
	@Override
	public String getString() {
		throw new IllegalStateException("Unbound variable:" + varname);
	}

}
