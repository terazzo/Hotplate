package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.impl.processor.InsertProcessorBase;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleWrapper;

public class SimpleInsertProcessor extends InsertProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
                SimpleTemplate source, SimpleTemplate definitions) {
        super(lexicalContext, source, definitions);
    }

    @Override
    protected SimpleTemplate wrap(SimpleTemplate value) {
        return new SimpleWrapper(value);
    }
    @Override
    protected SimpleTemplate newInstance(Context<Object, SimpleTemplate> context,
            SimpleTemplate source,SimpleTemplate definitions) {
        return new SimpleInsertProcessor(context, source, definitions);
    }

    public String toString() {
        return String.format("{insert value=%s}%s{/insert}", source, definitions.toString());
    }

    public static class Prototype extends SimpleProcessorPrototype {
        private final SimpleTemplate source;
        private final SimpleTemplate definitions;
        public Prototype(SimpleTemplate source, SimpleTemplate definitions) {
            super();
            this.source = source;
            this.definitions = definitions;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleInsertProcessor(
                     lexicalContext, source, definitions);
        }
        public String toString() {
            return String.format("{*insert value=%s}%s{/*insert}", source, definitions);
        }
    }

}
