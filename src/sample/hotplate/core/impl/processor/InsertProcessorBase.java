package sample.hotplate.core.impl.processor;

import java.util.ArrayList;
import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.TemplateWalker;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class InsertProcessorBase<V, T extends Template<V, T>> extends ProcessorBase<V, T> implements Template<V, T> {

    protected final T source;
    protected final List<T> elements;
    protected final Context<V, T> argumentContext;

    public InsertProcessorBase(Context<V, T>lexicalContext,
            T source, List<T> elements,
            Context<V, T>argumentContext) {
        super(lexicalContext);
        this.source = source;
        this.elements = elements;
        this.argumentContext = argumentContext;
    }

    @Override
    public TemplatePair<V, T> apply(Context<V, T> context) {
        Context<V, T> merged = ContextUtils.merge(context, lexicalContext);
        T source = this.source;
        Context<V, T> newArgumentContext = this.argumentContext;
        List<T> newElements = this.elements;

        T value = this.source.apply(merged).template();

        if (value != null) {
            newElements = new ArrayList<T>();
            for (T element : this.elements) {
                TemplatePair<V, T> applied = element.apply(merged);
                newElements.add(applied.template());
                newArgumentContext =
                    ContextUtils.merge(applied.context(), newArgumentContext);
            }
    
            TemplatePair<V, T> result = value.apply(newArgumentContext);

            if (result.template() != null && !result.template().isReducible()) {
                return TemplatePairUtils.pairOf(result.template());
            }
            source = wrap(value);
        }
        return TemplatePairUtils.pairOf(newInstance(merged, source, newElements, newArgumentContext));
    }

    protected abstract T wrap(T value);
    protected abstract T newInstance(
            Context<V, T> context, T source, List<T> elements,
            Context<V, T> argumentContext);

    @Override
    public void traverse(TemplateWalker<V, T> walker) {
        walker.process(this);
    }
}
