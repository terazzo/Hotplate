package sample.hotplate.core.processor;

import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.ContextImpl;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.impl.TemplatePairImpl;

public abstract class InsertProcessorBase<V, T extends Template<V, T>> extends ProcessorBase<V, T> implements Template<V, T> {

    protected final Symbol symbol;
    protected final List<T> definitions;

    public InsertProcessorBase(Context<V, T>lexicalContext, Symbol symbol, List<T> definitions) {
        super(lexicalContext);
        this.symbol = symbol;
        this.definitions = definitions;
    }

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        T value = resolve(symbol, context);
        if (value == null) {
            return TemplatePairImpl.of(getConcrete(), context);
        }
        Context<V, T> argContext = new ContextImpl<V, T>();
        for (T definition : definitions) {
            if (definition.isPrototype()) {
                TemplatePair<V, T> concrete = definition.apply(context);
                definition = concrete.template();
            }
            TemplatePair<V, T> result = definition.apply(argContext);
            argContext = result.context();
        }

        TemplatePair<V, T> result = value.apply(argContext);
        if (!result.template().isReducible()) {
            return new TemplatePairImpl<V, T>(result.template(), context);
        }
        return new TemplatePairImpl<V, T>(getConcrete(context, symbol, definitions), context);
    }

    protected abstract T getConcrete();
    protected abstract T getConcrete(Context<V, T> context, Symbol symbol2, List<T> definitions2);
    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(getClass().isInstance(obj))) {
            return false;
        }
        return symbol.equals(getClass().cast(obj).symbol)
                && definitions.equals(getClass().cast(obj).definitions);
    }



}
