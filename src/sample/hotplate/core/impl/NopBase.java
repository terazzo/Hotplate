package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class NopBase<V, T extends Template<V, T>> implements Template<V, T> {
    public NopBase() {
        super();
    }

    public TemplatePair<V, T> apply(Context<V, T> context) {
        return TemplatePairUtils.pairOf(concreteThis());
    }

    protected abstract T concreteThis();


}
