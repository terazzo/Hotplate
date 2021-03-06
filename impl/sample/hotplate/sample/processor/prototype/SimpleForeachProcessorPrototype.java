package sample.hotplate.sample.processor.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.processor.SimpleForeachProcessor;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleForeachProcessorPrototype implements SimpleTemplatePrototype {
    private final SimpleSource items;
    private final Symbol var;
    private final SimpleTemplatePrototype contents;
    public SimpleForeachProcessorPrototype(SimpleSource items, Symbol var, SimpleTemplatePrototype contents) {
        super();
        this.items = items;
        this.var = var;
        this.contents = contents;
    }
    public SimpleTemplate instantiate(Context<Object, SimpleTemplate> lexicalContext) {
         return new SimpleForeachProcessor(
                 lexicalContext, items, var, 
                 contents.instantiate(lexicalContext));
    }
}

