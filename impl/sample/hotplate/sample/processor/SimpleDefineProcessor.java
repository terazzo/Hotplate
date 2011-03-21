package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleTemplateWalker;

public class SimpleDefineProcessor extends ProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    protected final Symbol symbol;
    protected final SimpleTemplate source;

    public SimpleDefineProcessor(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol, SimpleTemplate source) {
        super(lexicalScope);
        this.symbol = symbol;
        this.source = source;
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(final Context<Object, SimpleTemplate> context) {
        SimpleTemplate value = source.apply(context).template();
        if (value != null) {
            Context<Object, SimpleTemplate> newContext = ContextUtils.newContext(symbol, value);
            return TemplatePairUtils.pairOf(new SimpleNop(), newContext);
        } else {
            return TemplatePairUtils.<Object, SimpleTemplate>pairOf(new SimpleDefineProcessor(context, symbol, source));
        }
    }
    @Override
    public void traverse(SimpleTemplateWalker walker) {
        walker.process(this);
    }

    public static class Prototype extends SimpleProcessorPrototype {
        private Symbol symbol;
        private SimpleTemplate value;
        public Prototype(Symbol symbol, SimpleTemplate value) {
            super();
            this.symbol = symbol;
            this.value = value;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleDefineProcessor(lexicalContext, symbol, value);
        }
        public String toString() {
            return String.format("{*define name=%s value=%s/}", symbol, value);
        }
        @Override
        public void traverse(SimpleTemplateWalker walker) {
            walker.process(this);
        }
    }

}
