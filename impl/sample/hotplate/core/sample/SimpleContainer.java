package sample.hotplate.core.sample;

import java.util.ArrayList;
import java.util.List;

import sample.hotplate.core.Context;

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
	public SimpleContainer apply(Context<Object, SimpleTemplate> context) {
		if (!isReducible()) {
			return this;
		}
		List<SimpleTemplate> newElements = new ArrayList<SimpleTemplate>();
		for (SimpleTemplate element : elements) {
			newElements.add(element.apply(context));
		}
		return new SimpleContainer(newElements);
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
