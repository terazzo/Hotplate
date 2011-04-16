package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleValue;
import sample.hotplate.sample.source.SimpleTemplateSource;

public class SimpleIfProcessor extends AbstractSimpleTemplate implements SimpleTemplate {

    protected final SimpleTemplateSource condition;
    protected final SimpleTemplate contents;

    public SimpleIfProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleTemplateSource condition, SimpleTemplate contents) {
        super(lexicalContext);
        this.condition = condition;
        this.contents = contents;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context) {
        SimpleTemplate result = this.condition.getTemplate(context);

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
