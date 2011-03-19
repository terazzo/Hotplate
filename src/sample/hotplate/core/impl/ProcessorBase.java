package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;

public abstract class ProcessorBase<V, T extends Template<V, T>> implements Template<V, T> {
    protected final Context<V, T> lexicalContext;

    protected ProcessorBase(Context<V, T> lexicalContext) {
        this.lexicalContext = lexicalContext;
    }
    
    @Override
    public boolean isReducible() {
        return true;
    }
    
}
