package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.prototype.AbstractSimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleTemplateSource;

public class SimpleDefineProcessor extends AbstractSimpleTemplate implements SimpleTemplate {

    protected final Symbol symbol;
    protected final SimpleTemplateSource source;

    public SimpleDefineProcessor(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol, SimpleTemplateSource source) {
        super(lexicalScope);
        this.symbol = symbol;
        this.source = source;
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(final Context<Object, SimpleTemplate> context) {
        SimpleTemplate value = source.getTemplate(context);
        if (value != null) {
            Context<Object, SimpleTemplate> newContext = ContextUtils.newContext(symbol, value);
            return TemplatePairUtils.pairOf(new SimpleNop(), newContext);
        } else {
            return TemplatePairUtils.<Object, SimpleTemplate>pairOf(new SimpleDefineProcessor(context, symbol, source));
        }
    }
    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        return "";
    }

    public static class Prototype extends AbstractSimpleTemplatePrototype {
        private Symbol symbol;
        private SimpleTemplateSource source;
        public Prototype(Symbol symbol, SimpleTemplateSource source) {
            super();
            this.symbol = symbol;
            this.source = source;
        }
        public SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleDefineProcessor(lexicalContext, symbol, source);
        }
    }


}
