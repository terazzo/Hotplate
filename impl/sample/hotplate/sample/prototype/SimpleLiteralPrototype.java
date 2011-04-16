package sample.hotplate.sample.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleLiteral;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleLiteralPrototype extends AbstractSimpleTemplatePrototype {
    private final String text;
    public SimpleLiteralPrototype(String text) {
        super();
        this.text = text;
    }
    
    @Override
    public SimpleTemplate instantiate(Context<Object, SimpleTemplate> lexicalContext) {
        return new SimpleLiteral(text);
    }


}
