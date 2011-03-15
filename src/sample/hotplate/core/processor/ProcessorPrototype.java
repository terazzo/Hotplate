package sample.hotplate.core.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.TemplatePairImpl;

public abstract class ProcessorPrototype<V, T extends Template<V, T>> implements Template<V, T> {

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        return TemplatePairImpl.of(create(context), context);
    }
    protected abstract T create(Context<V, T> lexicalContext);

    @Override
    public boolean isReducible() {
        return true;
    }

    @Override
    public boolean isPrototype() {
        return true;
    }

}
