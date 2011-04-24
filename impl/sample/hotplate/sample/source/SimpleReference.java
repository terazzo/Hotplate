package sample.hotplate.sample.source;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleReference implements SimpleSource {
    private final Symbol symbol;

    public SimpleReference(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public SimpleTemplate getTemplate(Context<Object, SimpleTemplate> context) {
        return context.get(symbol);
    }

}
