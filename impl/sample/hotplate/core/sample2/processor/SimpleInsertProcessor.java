package sample.hotplate.core.sample2.processor;

import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.processor.InsertProcessorBase;
import sample.hotplate.core.sample2.SimpleReference;
import sample.hotplate.core.sample2.SimpleTemplate;
import sample.hotplate.core.sample2.SimpleWrapper;
import sample.hotplate.core.util.ContextUtils;

public class SimpleInsertProcessor extends InsertProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
                SimpleTemplate source, List<SimpleTemplate> elements,
                Context<Object, SimpleTemplate>argmunentContext) {
        super(lexicalContext, source, elements, argmunentContext);
    }

    @Override
    protected SimpleTemplate wrap(SimpleTemplate value) {
        return new SimpleWrapper(value);
    }
    @Override
    protected SimpleTemplate newInstance(Context<Object, SimpleTemplate> context,
            SimpleTemplate source,List<SimpleTemplate> elements,
            Context<Object, SimpleTemplate>argmunentContext) {
        return new SimpleInsertProcessor(context, source, elements, argmunentContext);
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unresolved insert tag:" + source);
    }

    public String toString() {
        return String.format("{insert value=%s}%s{/insert}", source, elements.toString());
    }

    public static class Prototype extends SimpleProcessorPrototype {
        private final SimpleTemplate source;
        private final List<SimpleTemplate> elements;
        public Prototype(Symbol symbol, List<SimpleTemplate> elements) {
            super();
            this.source = new SimpleReference(symbol);
            this.elements = elements;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleInsertProcessor(
                     lexicalContext, source, elements,
                     ContextUtils.<Object, SimpleTemplate>emptyContext());
        }
        public String toString() {
            return String.format("{*insert value=%s}%s{/*insert}", source, elements);
        }
    }

}
