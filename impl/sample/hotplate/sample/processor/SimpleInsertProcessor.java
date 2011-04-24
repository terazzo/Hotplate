package sample.hotplate.sample.processor;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;
import sample.hotplate.sample.source.SimpleWrapper;

public class SimpleInsertProcessor extends AbstractSimpleTemplate implements SimpleTemplate {
    /** 初期化時のContext */
    private final Context<Object, SimpleTemplate> lexicalContext;
    /** value属性で指定した値 */
    private final SimpleSource source;
    /** コンテナの中身を保持するSimpleTemplate */
    private final SimpleTemplate content;

    public SimpleInsertProcessor(
            Context<Object, SimpleTemplate>lexicalContext,
            SimpleSource source,
            SimpleTemplate content) {
        
        this.lexicalContext = lexicalContext;
        this.source = source;
        this.content = content;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> totalContext = ContextUtils.merge(context, lexicalContext);

        Associable<Object, SimpleTemplate> associable = this.source.getAssociable(totalContext);

        if (associable == null) {
            return TemplatePair.<Object, SimpleTemplate>pairOf(
                new SimpleInsertProcessor(totalContext, source, content));
        }
        TemplatePair<Object, SimpleTemplate> appliedContent = content.apply(totalContext);
        Context<Object, SimpleTemplate> argumentContext = appliedContent.context();

        SimpleTemplate template = associable.asTemplate();
        TemplatePair<Object, SimpleTemplate> result = template.apply(argumentContext);

        if (!result.template().isReducible()) {
            return TemplatePair.pairOf(result.template());
        }
        return TemplatePair.<Object, SimpleTemplate>pairOf(
                new SimpleInsertProcessor(
                        totalContext,
                        new SimpleWrapper(result.template()),
                        appliedContent.template()));
    }


    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated insert:" + this);
    }
}
