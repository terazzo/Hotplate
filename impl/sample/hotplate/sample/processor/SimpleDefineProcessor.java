package sample.hotplate.sample.processor;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleDefineProcessor extends AbstractSimpleTemplate implements SimpleTemplate {

    protected final Symbol symbol;
    protected final SimpleSource source;
    private Context<Object, SimpleTemplate> lexicalContext;

    public SimpleDefineProcessor(Context<Object, SimpleTemplate> lexicalContext, Symbol symbol, SimpleSource source) {
        super();
        this.lexicalContext = lexicalContext;
        this.symbol = symbol;
        this.source = source;
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> apply(final Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> merged = ContextUtils.merge(context, lexicalContext);

        Associable<Object, SimpleTemplate> value = source.getAssociable(merged);
        if (value == null) {
            return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
        }

        Context<Object, SimpleTemplate> newContext = ContextUtils.newContext(symbol, value);
        return TemplatePairUtils.pairOf(new SimpleNop(), newContext);
    }
    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        return "";
    }
}
