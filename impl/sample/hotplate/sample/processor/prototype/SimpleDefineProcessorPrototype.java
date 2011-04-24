package sample.hotplate.sample.processor.prototype;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.processor.SimpleDefineProcessor;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleDefineProcessorPrototype implements SimpleTemplatePrototype {
    private final Symbol symbol;
    private final SimpleSource source;
    public SimpleDefineProcessorPrototype(Symbol symbol, SimpleSource source) {
        super();
        this.symbol = symbol;
        this.source = source;
    }
    public SimpleTemplate instantiate(Context<Object, SimpleTemplate> lexicalContext) {
         return new SimpleDefineProcessor(lexicalContext, symbol, source);
    }
}
