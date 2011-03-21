package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;

public abstract class ProcessorPrototype<V, T extends Template<V, T>> implements Template<V, T> {

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        T processor = instantiate(context);
        return processor.apply(context);
    }
    protected abstract T instantiate(Context<V, T> lexicalContext);

    @Override
    public boolean isReducible() {
        return true;
    }
}
