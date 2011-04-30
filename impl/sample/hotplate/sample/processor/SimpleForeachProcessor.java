package sample.hotplate.sample.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.sample.AbstractSimpleTemplate;
import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleValue;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleForeachProcessor extends AbstractSimpleTemplate implements SimpleTemplate {
    private final Context<Object, SimpleTemplate> lexicalContext;
    private final SimpleSource items;
    private final Symbol var;
    private final SimpleTemplate content;

    public SimpleForeachProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleSource items, Symbol var, SimpleTemplate content) {
        super();
        this.lexicalContext = lexicalContext;
        this.items = items;
        this.var = var;
        this.content = content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TemplatePair<Object, SimpleTemplate> apply(final Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> merged = ContextUtils.merge(context, lexicalContext);

        Associable<Object, SimpleTemplate> itemsAssociable = this.items.getAssociable(merged);

        if (itemsAssociable == null) {
            return TemplatePair.<Object, SimpleTemplate>pairOf(this);
        }

        Object itemsValue = itemsAssociable.asValue().value();
        if (itemsValue == null || !(itemsValue instanceof Collection)) {
            throw new IllegalStateException("'items' is not collection");
        }
        List<SimpleTemplate> elements = new ArrayList<SimpleTemplate>();
        for (Object item : (Collection<Object>) itemsValue) {
            Context<Object, SimpleTemplate> innerContext = ContextUtils.put(context, var, new SimpleValue(item));
            elements.add(content.apply(innerContext).template());
        }
        
        return TemplatePair.<Object, SimpleTemplate>pairOf(new SimpleContainer(elements));
    }


    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated foreach:" + this);
    }
}
