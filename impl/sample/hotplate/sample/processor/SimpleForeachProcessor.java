package sample.hotplate.sample.processor;

import java.util.ArrayList;
import java.util.Collection;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleValue;
import sample.hotplate.sample.source.SimpleTemplateSource;

public class SimpleForeachProcessor extends AbstractSimpleTemplate implements SimpleTemplate {
    protected final SimpleTemplateSource items;
    protected final Symbol var;
    protected final SimpleTemplate contents;

    public SimpleForeachProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleTemplateSource items, Symbol var, SimpleTemplate contents) {
        super(lexicalContext);
        this.items = items;
        this.var = var;
        this.contents = contents;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context) {

        SimpleTemplate result = this.items.getTemplate(context);

        if (result != null) {
            if (result instanceof SimpleValue) {
                Object value = ((SimpleValue) result).value();
                if (value != null && value instanceof Collection) {
                    ArrayList<SimpleTemplate> elements = new ArrayList<SimpleTemplate>();
                    for (Object item : (Collection<Object>) value) {
                        Context<Object, SimpleTemplate> innerContents =
                            ContextUtils.put(context, var, new SimpleValue(item));
                        SimpleTemplate applies = contents.apply(innerContents).template();
                        elements.add(applies);
                    }
                    
                    return TemplatePairUtils.<Object, SimpleTemplate>pairOf(
                            new SimpleContainer(context, elements));
                }
            }
        }
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(
                new SimpleForeachProcessor(context, items, var, contents));
    }


    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated foreach:" + this);
    }
    public String toString() {
        return String.format("{foreach items=%s var=%s}%s{/foreach}", items, var, contents);
    }


}
