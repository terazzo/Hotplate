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

    protected final SimpleSource condition;
    protected final SimpleTemplate contents;
    private Context<Object, SimpleTemplate> lexicalContext;

    public SimpleIfProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleSource condition, SimpleTemplate contents) {
        super();
        this.lexicalContext = lexicalContext;
        this.condition = condition;
        this.contents = contents;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> apply(final Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> merged = ContextUtils.merge(context, lexicalContext);
        Associable<Object, SimpleTemplate> associable = this.condition.getAssociable(merged);

        if (associable == null) {
            return TemplatePair.<Object, SimpleTemplate>pairOf(this);
        }

        Object value = associable.asValue().value();
        if (value != null && value.equals(true)) {
            return TemplatePair.pairOf(contents.apply(context).template());
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
    public String toString() {
        return String.format("{if condition=%s}%s{/if}", condition, contents);
    }


}
