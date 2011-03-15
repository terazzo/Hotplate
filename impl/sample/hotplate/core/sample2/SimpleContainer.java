package sample.hotplate.core.sample2;

import java.util.List;

import sample.hotplate.core.impl.ContainerBase;

public class SimpleContainer extends ContainerBase<Object, SimpleTemplate> implements SimpleTemplate {
	public SimpleContainer(List<SimpleTemplate> elements) {
	    super(elements);
	}

    @Override
    protected SimpleTemplate getConcrete() {
        return this;
    }

    @Override
    protected SimpleTemplate getConcrete(List<SimpleTemplate> newElements) {
        return new SimpleContainer(newElements);
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (SimpleTemplate element : elements) {
            sb.append(element.getString());
        }
        return sb.toString();
    }

	public String toString() {
	    return elements.toString();
	}


}
