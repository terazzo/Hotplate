package sample.hotplate.core.sample2.processors;

import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.processor.InsertProcessorBase;
import sample.hotplate.core.sample2.SimpleTemplate;

public class SimpleInsertProcessor extends InsertProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
                Symbol symbol, List<SimpleTemplate> definitions) {
        super(lexicalContext, symbol, definitions);
    }
    @Override
    protected SimpleTemplate getConcrete() {
        return this;
    }

    @Override
    protected SimpleTemplate getConcrete(
            Context<Object, SimpleTemplate> context, Symbol symbol,
            List<SimpleTemplate> definitions) {
        return new SimpleInsertProcessor(context, symbol, definitions);
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unresolved insert tag:" + symbol);
    }

    public String toString() {
        return String.format("{insert value=%s}%s{/insert}", symbol, definitions.toString());
    }

}
