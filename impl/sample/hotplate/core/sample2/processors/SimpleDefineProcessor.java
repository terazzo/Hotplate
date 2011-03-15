package sample.hotplate.core.sample2.processors;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.processor.DefineProcessorBase;
import sample.hotplate.core.sample2.SimpleTemplate;

public class SimpleDefineProcessor extends DefineProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {
    public SimpleDefineProcessor(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol, SimpleTemplate value) {
        super(lexicalScope, symbol, value);
    }

    @Override
    protected SimpleTemplate getConcrete() {
        return this;
    }

    @Override
    public String getString() {
        throw new IllegalStateException("Unuserd definition:" + symbol);
    }
    public String toString() {
        return String.format("{define name=\"%s\" value=%s/}", symbol,value.toString());
    }

}
