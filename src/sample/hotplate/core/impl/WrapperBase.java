package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class WrapperBase<V, T extends Template<V, T>> implements Template<V, T> {
    protected final T content;

    public WrapperBase(T content) {
        super();
        this.content = content;
    }
    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        return TemplatePairUtils.pairOf(content);
    }
    @Override
    public boolean isReducible() {
        return true;
    }
}
