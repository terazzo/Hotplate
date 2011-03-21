package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;

public abstract class ProcessorBase<V, T extends Template<V, T>> implements Template<V, T> {
    protected final Context<V, T> lexicalContext;

    protected ProcessorBase(Context<V, T> lexicalContext) {
        this.lexicalContext = lexicalContext;
    }
    
    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        Context<V, T> merged = ContextUtils.merge(context, lexicalContext);
        return doApply(merged);
    }

    protected abstract TemplatePair<V, T> doApply(Context<V, T> merged);
    @Override
    public boolean isReducible() {
        return true;
    }
    
}
