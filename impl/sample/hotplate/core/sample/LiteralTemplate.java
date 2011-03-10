package sample.hotplate.core.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.impl.TemplateBase;

public class LiteralTemplate extends TemplateBase<Object, SimpleTemplate> implements SimpleTemplate {

	private String token;
	public LiteralTemplate(String token) {
		this.token = token;
	}
	@Override
	public boolean isReducible() {
		return false;
	}
	@Override
	public String getString() {
		return token;
	}
	@Override
	public SimpleTemplate apply(Context<Object, SimpleTemplate> context) {
		return this;
	}

}
