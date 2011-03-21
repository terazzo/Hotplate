package sample.hotplate.sample.processor;

import java.util.List;

import sample.hotplate.core.Context;
import sample.hotplate.core.impl.processor.InsertProcessorBase;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleWrapper;

public class SimpleInsertProcessor extends InsertProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
                SimpleTemplate source, List<SimpleTemplate> elements) {
        super(lexicalContext, source, elements);
    }

    @Override
    protected SimpleTemplate wrap(SimpleTemplate value) {
        return new SimpleWrapper(value);
    }
    @Override
    protected SimpleTemplate newInstance(Context<Object, SimpleTemplate> context,
            SimpleTemplate source,List<SimpleTemplate> elements) {
        return new SimpleInsertProcessor(context, source, elements);
    }

    public String toString() {
        return String.format("{insert value=%s}%s{/insert}", source, elements.toString());
    }

    public static class Prototype extends SimpleProcessorPrototype {
        private final SimpleTemplate source;
        private final List<SimpleTemplate> elements;
        public Prototype(SimpleTemplate source, List<SimpleTemplate> elements) {
            super();
            this.source = source;
            this.elements = elements;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleInsertProcessor(
                     lexicalContext, source, elements);
        }
        public String toString() {
            return String.format("{*insert value=%s}%s{/*insert}", source, elements);
        }
    }

}
