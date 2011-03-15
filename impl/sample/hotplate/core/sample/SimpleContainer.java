package sample.hotplate.core.sample;

import java.util.ArrayList;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;

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
			return new SimpleTemplatePair(this, context);
		}
		List<SimpleTemplate> newElements = new ArrayList<SimpleTemplate>();
		for (SimpleTemplate element : elements) {
			newElements.add(element.apply(context).template());
		}
		return new SimpleTemplatePair(new SimpleContainer(newElements), context);
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
    @Override
    public boolean isPrototype() {
        return false;
    }

	
	@Override
	public int hashCode() {
		return elements.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SimpleContainer)) {
			return false;
		}
		return elements.equals(((SimpleContainer) obj).elements);
	}
}
