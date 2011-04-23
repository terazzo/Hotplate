package sample.hotplate.sample.prototype;

import java.util.ArrayList;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleContainerPrototype
        implements SimpleTemplatePrototype, SimpleSource {
    protected final List<SimpleTemplatePrototype> elements;

    public SimpleContainerPrototype(List<SimpleTemplatePrototype> elements) {
        super();
        this.elements = elements;
    }

    @Override
    public SimpleTemplate instantiate(Context<Object, SimpleTemplate> lexicalContext) {
        List<SimpleTemplate> tempalteElements = new ArrayList<SimpleTemplate>();
        for (SimpleTemplatePrototype source : elements) {
            tempalteElements.add(source.instantiate(lexicalContext));
        }
        return new SimpleContainer(tempalteElements);
    }
    @Override
    public SimpleTemplate getAssociable(Context<Object, SimpleTemplate> context) {
        return instantiate(context);
    }
}
