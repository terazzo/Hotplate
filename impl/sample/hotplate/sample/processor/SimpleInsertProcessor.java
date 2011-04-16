package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.prototype.AbstractSimpleTemplatePrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;
import sample.hotplate.sample.source.SimpleTemplateSource;
import sample.hotplate.sample.source.SimpleWrapper;

public class SimpleInsertProcessor extends AbstractSimpleTemplate implements SimpleTemplate {

    protected final SimpleTemplateSource source;
    protected final SimpleTemplate content;

    public SimpleInsertProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleTemplateSource source, SimpleTemplate content) {
        super(lexicalContext);
        this.source = source;
        this.content = content;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context) {
        SimpleTemplateSource source = this.source;
        SimpleTemplate content = this.content;

        SimpleTemplate value = this.source.getTemplate(context);

        if (value != null) {
            Context<Object, SimpleTemplate> argumentContext =
                content.apply(context).context();
    
            TemplatePair<Object, SimpleTemplate> result = value.apply(argumentContext);

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

    public static class Prototype extends AbstractSimpleTemplatePrototype {
        private final SimpleTemplateSource source;
        private final SimpleTemplatePrototype contentPrototype;
        public Prototype(SimpleTemplateSource source, SimpleTemplatePrototype contentPrototype) {
            super();
            this.source = source;
            this.contentPrototype = contentPrototype;
        }
        public SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleInsertProcessor(
                     lexicalContext, source, 
                     contentPrototype.instantiate(lexicalContext));
        }
    }

}
