package sample.hotplate.core.impl.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.TemplateWalker;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class DefineProcessorBase<V, T extends Template<V,T>> extends ProcessorBase<V, T> implements Template<V,T> {

    protected final Symbol symbol;
    protected final T source;

    public DefineProcessorBase(Context<V, T> lexicalScope, Symbol symbol, T source) {
        super(lexicalScope);
        this.symbol = symbol;
        this.source = source;
    }
    @Override
    public TemplatePair<V, T> apply(final Context<V, T> context) {
        Context<V, T> merged = ContextUtils.merge(context, lexicalContext);
        T value = source.apply(merged).template();
        if (value != null) {
            Context<V, T> newContext =
                ContextUtils.put(ContextUtils.<V, T>emptyContext(), symbol, value);
            return TemplatePairUtils.pairOf(getNop(), newContext);
        } else {
            return TemplatePairUtils.pairOf(newInstance(merged, symbol, source));
        }
    }
    protected abstract T getNop();
    protected abstract T newInstance(Context<V, T> lexicalScope, Symbol symbol, T value);

    @Override
    public void traverse(TemplateWalker<V, T> walker) {
        walker.process(this);
    }
}
