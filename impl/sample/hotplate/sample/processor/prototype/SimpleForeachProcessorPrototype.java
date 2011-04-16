package sample.hotplate.sample.processor.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.processor.SimpleForeachProcessor;
import sample.hotplate.sample.prototype.AbstractSimpleTemplatePrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleTemplateSource;

public class SimpleForeachProcessorPrototype extends AbstractSimpleTemplatePrototype {
    private final SimpleTemplateSource items;
    private final Symbol var;
    private final SimpleTemplatePrototype contents;
    public SimpleForeachProcessorPrototype(SimpleTemplateSource items, Symbol var, SimpleTemplatePrototype contents) {
        super();
        this.items = items;
        this.var = var;
        this.contents = contents;
    }
    public SimpleTemplate instantiate(
            Context<Object, SimpleTemplate> lexicalContext) {
         return new SimpleForeachProcessor(
                 lexicalContext, items, var, 
                 contents.instantiate(lexicalContext));
    }
    public String toString() {
        return String.format("{*foreach items=%s var=%s}%s{/*foreach}", items, var, contents);
    }
}

