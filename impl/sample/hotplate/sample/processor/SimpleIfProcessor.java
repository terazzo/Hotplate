package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleValue;

public class SimpleIfProcessor extends ProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    protected final SimpleTemplate condition;
    protected final SimpleTemplate contents;

    public SimpleIfProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleTemplate condition, SimpleTemplate contents) {
        super(lexicalContext);
        this.condition = condition;
        this.contents = contents;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context) {
        SimpleTemplate result = this.condition.apply(context).template();

        if (result != null) {
            if (result instanceof SimpleValue) {
                Object value = ((SimpleValue) result).value();
                if (value != null && value.equals(true)) {
                    return TemplatePairUtils.pairOf(contents.apply(context).template());
                } else {
                    return TemplatePairUtils.<Object, SimpleTemplate>pairOf(new SimpleNop());
                }
            }
        }
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(
                new SimpleIfProcessor(context, condition, contents));
    }


    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated if:" + this);
    }
    public String toString() {
        return String.format("{if condition=%s}%s{/if}", condition, contents);
    }

    public static class Prototype extends SimpleProcessorPrototype {
        private final SimpleTemplate condition;
        private final SimpleTemplate contents;
        public Prototype(SimpleTemplate condition, SimpleTemplate contents) {
            super();
            this.condition = condition;
            this.contents = contents;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleIfProcessor(
                     lexicalContext, condition, contents);
        }
        public String toString() {
            return String.format("{*if condition=%s}%s{/*if}", condition, contents);
        }
    }

}
