package sample.hotplate.sample.processor;

import java.util.ArrayList;
import java.util.Collection;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.impl.ProcessorBase;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;
import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleTemplateWalker;
import sample.hotplate.sample.SimpleValue;

public class SimpleForeachProcessor extends ProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {

    protected final SimpleTemplate items;
    protected final Symbol var;
    protected final SimpleTemplate contents;

    public SimpleForeachProcessor(Context<Object, SimpleTemplate>lexicalContext,
            SimpleTemplate items, Symbol var, SimpleTemplate contents) {
        super(lexicalContext);
        this.items = items;
        this.var = var;
        this.contents = contents;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> doApply(Context<Object, SimpleTemplate> context) {

        SimpleTemplate result = this.items.apply(context).template();

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
                            new SimpleContainer(elements));
                }
            }
        }
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(
                new SimpleForeachProcessor(context, items, var, contents));
    }


    @Override
    public void traverse(SimpleTemplateWalker walker) {
        walker.process(this);
    }
    public String toString() {
        return String.format("{foreach items=%s var=%s}%s{/foreach}", items, var, contents);
    }

    public static class Prototype extends SimpleProcessorPrototype {
        private final SimpleTemplate items;
        private final Symbol var;
        private final SimpleTemplate contents;
        public Prototype(SimpleTemplate items, Symbol var, SimpleTemplate contents) {
            super();
            this.items = items;
            this.var = var;
            this.contents = contents;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleForeachProcessor(
                     lexicalContext, items, var, contents);
        }
        @Override
        public void traverse(SimpleTemplateWalker walker) {
            walker.process(this);
        }
        public String toString() {
            return String.format("{*foreach items=%s var=%s}%s{/*foreach}", items, var, contents);
        }
    }

}
