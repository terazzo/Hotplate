package sample.hotplate.sample.processor;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;
import sample.hotplate.sample.source.SimpleWrapper;

public class SimpleInsertProcessor extends AbstractSimpleTemplate implements SimpleTemplate {

    protected final SimpleSource source;
    protected final SimpleTemplate content;
    private Context<Object, SimpleTemplate> lexicalContext;

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleSource source, SimpleTemplate content) {
        super();
        this.lexicalContext = lexicalContext;
        this.source = source;
        this.content = content;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> apply(final Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> merged = ContextUtils.merge(context, lexicalContext);
        SimpleSource source = this.source;
        SimpleTemplate content = this.content;

        Associable<Object, SimpleTemplate> associable = this.source.getAssociable(merged);

        if (associable != null) {
            SimpleTemplate template = associable.asTemplate();
            Context<Object, SimpleTemplate> argumentContext = content.apply(context).context();
    
            TemplatePair<Object, SimpleTemplate> result = template.apply(argumentContext);

            if (!result.template().isReducible()) {
                return TemplatePairUtils.pairOf(result.template());
            }
            source = new SimpleWrapper(result.template());
        }
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(new SimpleInsertProcessor(context, source, content));
    }


    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated insert:" + this);
    }
    public String toString() {
        return String.format("{insert value=%s}%s{/insert}", source, content.toString());
    }

}
