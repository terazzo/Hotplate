package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleWrapper;

public class SimpleInsertProcessor extends ProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    protected final SimpleTemplate source;
    protected final SimpleTemplate definitions;

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleTemplate source, SimpleTemplate definitions) {
        super(lexicalContext);
        this.source = source;
        this.definitions = definitions;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context) {
        SimpleTemplate source = this.source;
        SimpleTemplate definitions = this.definitions;

        SimpleTemplate value = this.source.apply(context).template();

        if (value != null) {
            TemplatePair<Object, SimpleTemplate> applied = definitions.apply(context);
            definitions = applied.template();
            Context<Object, SimpleTemplate> argumentContext = applied.context();
    
            TemplatePair<Object, SimpleTemplate> result = value.apply(argumentContext);

            if (!result.template().isReducible()) {
                return TemplatePairUtils.pairOf(result.template());
            }
            source = new SimpleWrapper(result.template());
        }
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(new SimpleInsertProcessor(context, source, definitions));
    }


    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated insert:" + this);
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
