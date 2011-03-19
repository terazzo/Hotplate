package sample.hotplate.core.sample;

import java.util.ArrayList;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleContainer implements SimpleTemplate {
	private final List<SimpleTemplate> elements;
	private final boolean isReducible;
	public SimpleContainer(List<SimpleTemplate> elements) {
		this.elements = elements;
		for (SimpleTemplate element : elements) {
			if (element.isReducible()) {
				this.isReducible = true;
				return;
			}
		}
		isReducible = false;
	}

	@Override
	public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
		if (!isReducible()) {
			return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
		}
		List<SimpleTemplate> newElements = new ArrayList<SimpleTemplate>();
		for (SimpleTemplate element : elements) {
			newElements.add(element.apply(context).template());
		}
		return TemplatePairUtils.<Object, SimpleTemplate>pairOf(new SimpleContainer(newElements));
	}

	@Override
	public boolean isReducible() {
		return isReducible;
	}

	public String getString() {
		StringBuilder sb = new StringBuilder();
		for (SimpleTemplate element : elements) {
			sb.append(element.getString());
		}
		return sb.toString();
	}
	
}
