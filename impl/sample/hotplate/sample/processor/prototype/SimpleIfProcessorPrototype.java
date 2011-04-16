package sample.hotplate.sample.processor.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.processor.SimpleIfProcessor;
import sample.hotplate.sample.prototype.AbstractSimpleTemplatePrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleTemplateSource;

public class SimpleIfProcessorPrototype extends AbstractSimpleTemplatePrototype {
    private final SimpleTemplateSource condition;
    private final SimpleTemplatePrototype contents;
    public SimpleIfProcessorPrototype(SimpleTemplateSource condition, SimpleTemplatePrototype contents) {
        super();
        this.condition = condition;
        this.contents = contents;
    }
    public SimpleTemplate instantiate(
            Context<Object, SimpleTemplate> lexicalContext) {
         return new SimpleIfProcessor(
                 lexicalContext, condition, 
                 contents.instantiate(lexicalContext));
    }
}
