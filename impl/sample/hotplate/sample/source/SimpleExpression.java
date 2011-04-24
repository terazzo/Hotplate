package sample.hotplate.sample.source;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleLiteral;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleExpression implements SimpleSource {
    private final String expression;

    public SimpleExpression(String expression) {
        this.expression = expression;
    }
    
    @Override
    public SimpleTemplate getTemplate(Context<Object, SimpleTemplate> context) {
        return new SimpleLiteral(expression);
    }
}
