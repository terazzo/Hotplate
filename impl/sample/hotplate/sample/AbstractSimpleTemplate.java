package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.Value;
import sample.hotplate.core.util.ContextUtils;


public abstract class AbstractSimpleTemplate implements SimpleTemplate {
    
    protected final Context<Object, SimpleTemplate> lexicalContext;
    public AbstractSimpleTemplate(Context<Object, SimpleTemplate> lexicalContext) {
        this.lexicalContext = lexicalContext;
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> merged = ContextUtils.merge(context, lexicalContext);
        return doApply(merged);
    }

    protected abstract TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context);
    @Override
    public boolean isTemplate() {
        return true;
    }
    @Override
    public Value<Object, SimpleTemplate> asValue() {
        return new SimpleValue(this);
    }
    @Override
    public SimpleTemplate asTemplate() {
        return this;
    }
}
