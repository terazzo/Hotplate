package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class ReferenceBase<V, T extends Template<V, T>> implements Template<V, T> {
    protected final Symbol symbol;

    public ReferenceBase(Symbol symbol) {
        super();
        this.symbol = symbol;
    }

    public TemplatePair<V, T> apply(Context<V, T> context) {
        return TemplatePairUtils.pairOf(context.get(symbol));
    }
    @Override
    public boolean isReducible() {
        return true;
    }
}
