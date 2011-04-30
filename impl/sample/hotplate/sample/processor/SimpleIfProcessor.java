package sample.hotplate.sample.processor;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleIfProcessor extends AbstractSimpleTemplate implements SimpleTemplate {
    private final Context<Object, SimpleTemplate> lexicalContext;
    private final SimpleSource condition;
    private final SimpleTemplate content;

    public SimpleIfProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleSource condition, SimpleTemplate content) {
        super();
        this.lexicalContext = lexicalContext;
        this.condition = condition;
        this.content = content;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> apply(final Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> merged = ContextUtils.merge(context, lexicalContext);
        Associable<Object, SimpleTemplate> conditionAssociable = this.condition.getAssociable(merged);

        if (conditionAssociable == null) {
            return TemplatePair.<Object, SimpleTemplate>pairOf(this);
        }

        Object conditionValue = conditionAssociable.asValue().value();
        if (conditionValue != null && conditionValue.equals(true)) {
            return TemplatePair.pairOf(content.apply(context).template());
        } else {
            return TemplatePair.<Object, SimpleTemplate>pairOf(new SimpleNop());
        }
    }


    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated if:" + this);
    }
}
