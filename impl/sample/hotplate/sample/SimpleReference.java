package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleReference implements SimpleTemplate{

    protected final Symbol symbol;

    public SimpleReference(Symbol symbol) {
        super();
        this.symbol = symbol;
    }

    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.pairOf(context.get(symbol));
    }
    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unbound reference:" + symbol);
    }
    @Override
    public String toString() {
        return String.format("{*simple value=%s/}", symbol);
    }

}
