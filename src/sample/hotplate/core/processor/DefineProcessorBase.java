package sample.hotplate.core.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.impl.TemplatePairImpl;
import sample.hotplate.core.util.ContextUtils;

public abstract class DefineProcessorBase<V, T extends Template<V,T>> extends ProcessorBase<V, T> implements Template<V,T> {

    protected final Symbol symbol;
    protected final T value;

    public DefineProcessorBase(Context<V, T> lexicalScope, Symbol symbol, T value) {
        super(lexicalScope);
        this.symbol = symbol;
        this.value = value;
    }
    @Override
    public TemplatePair<V, T> apply(final Context<V, T> context) {
        T value = this.value;
        Context<V, T> merged = ContextUtils.merge(context, lexicalContext);
        if (value.isPrototype()) {
            TemplatePair<V, T> concrete = value.apply(merged);
            value = concrete.template();
        }
        TemplatePair<V, T> applied = value.apply(merged);
        return TemplatePairImpl.of(getConcrete(), ContextUtils.put(context, symbol, applied.template()));
    }
    protected abstract T getConcrete();


    @Override
    public boolean isPrototype() {
        return false;
    }

}
