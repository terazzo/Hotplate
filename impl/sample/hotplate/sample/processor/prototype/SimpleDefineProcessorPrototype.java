package sample.hotplate.sample.processor.prototype;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.processor.SimpleDefineProcessor;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleTemplateSource;

public class SimpleDefineProcessorPrototype implements SimpleTemplatePrototype {
    private Symbol symbol;
    private SimpleTemplateSource source;
    public SimpleDefineProcessorPrototype(Symbol symbol, SimpleTemplateSource source) {
        super();
        this.symbol = symbol;
        this.source = source;
    }
    public SimpleTemplate instantiate(
            Context<Object, SimpleTemplate> lexicalContext) {
         return new SimpleDefineProcessor(lexicalContext, symbol, source);
    }
}
