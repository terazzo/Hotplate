package sample.hotplate.sample.processor.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.processor.SimpleInsertProcessor;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleInsertProcessorPrototype implements SimpleTemplatePrototype {
    private final SimpleSource source;
    private final SimpleTemplatePrototype contentPrototype;
    public SimpleInsertProcessorPrototype(SimpleSource source, SimpleTemplatePrototype contentPrototype) {
        super();
        this.source = source;
        this.contentPrototype = contentPrototype;
    }
    public SimpleTemplate instantiate(
            Context<Object, SimpleTemplate> lexicalContext) {
         return new SimpleInsertProcessor(
                 lexicalContext, source, 
                 contentPrototype.instantiate(lexicalContext));
    }
}

