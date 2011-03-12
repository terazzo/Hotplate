package sample.hotplate.core.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.impl.TemplateBase;

public class LiteralTemplate extends TemplateBase<Object, SimpleTemplate> implements SimpleTemplate {

	private String text;
	public LiteralTemplate(String text) {
		this.text = text;
	}
	@Override
	public boolean isReducible() {
		return false;
	}
	@Override
	public String getString() {
		return text;
	}
	@Override
	public SimpleTemplate apply(Context<Object, SimpleTemplate> context) {
		return this;
	}

}
