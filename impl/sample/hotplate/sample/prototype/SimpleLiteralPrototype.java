package sample.hotplate.sample.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleLiteral;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleLiteralPrototype implements SimpleTemplatePrototype, SimpleSource {
    private final String text;
    public SimpleLiteralPrototype(String text) {
        super();
        this.text = text;
    }
    
    @Override
    public SimpleTemplate instantiate(Context<Object, SimpleTemplate> lexicalContext) {
        return new SimpleLiteral(text);
    }

    @Override
    public SimpleTemplate getTemplate(Context<Object, SimpleTemplate> context) {
        return instantiate(context);
    }
}
