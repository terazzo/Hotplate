package sample.hotplate.sample;

import java.util.List;

import sample.hotplate.core.impl.ContainerBase;

public class SimpleContainer extends ContainerBase<Object, SimpleTemplate> implements SimpleTemplate {
	public SimpleContainer(List<SimpleTemplate> elements) {
	    super(elements);
	}

    @Override
    protected SimpleTemplate concreteThis() {
        return this;
    }

    @Override
    protected SimpleTemplate newInstance(List<SimpleTemplate> newElements) {
        return new SimpleContainer(newElements);
    }

	public String toString() {
	    return elements.toString();
	}



}
