package sample.hotplate.sample.source;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleReference implements SimpleSource {

    protected final Symbol symbol;

    public SimpleReference(Symbol symbol) {
        super();
        this.symbol = symbol;
    }

    @Override
    public Associable<Object, SimpleTemplate> getAssociable(Context<Object, SimpleTemplate> context) {
        return context.get(symbol);
    }

}
