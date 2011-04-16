package sample.hotplate.sample.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleTemplateSource;

public abstract class AbstractSimpleTemplatePrototype
        implements SimpleTemplatePrototype, SimpleTemplateSource {

    @Override
    public SimpleTemplate getTemplate(Context<Object, SimpleTemplate> context) {
        return instantiate(context);
    }
}
