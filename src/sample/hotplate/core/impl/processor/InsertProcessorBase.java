package sample.hotplate.core.impl.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.TemplateWalker;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class InsertProcessorBase<V, T extends Template<V, T>> extends ProcessorBase<V, T> implements Template<V, T> {

    protected final T source;
    protected final T definitions;

    public InsertProcessorBase(Context<V, T>lexicalContext,
            T source, T definitions) {
        super(lexicalContext);
        this.source = source;
        this.definitions = definitions;
    }

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        Context<V, T> merged = ContextUtils.merge(context, lexicalContext);
        T source = this.source;
        T definitions = this.definitions;

        T value = this.source.apply(merged).template();

        if (value != null) {
            TemplatePair<V, T> applied = definitions.apply(merged);
            definitions = applied.template();
            Context<V, T> argumentContext = applied.context();
    
            TemplatePair<V, T> result = value.apply(argumentContext);

            if (!result.template().isReducible()) {
                return TemplatePairUtils.pairOf(result.template());
            }
            source = wrap(result.template());
        }
        return TemplatePairUtils.pairOf(newInstance(merged, source, definitions));
    }

    protected abstract T wrap(T value);
    protected abstract T newInstance(Context<V, T> context, T source, T container);

    @Override
    public void traverse(TemplateWalker<V, T> walker) {
        walker.process(this);
    }
}
