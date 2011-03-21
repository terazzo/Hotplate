package sample.hotplate.sample;

import sample.hotplate.core.impl.ExpressionBase;

public class SimpleExpression extends ExpressionBase<Object, SimpleTemplate> implements SimpleTemplate {

    public SimpleExpression(String expression) {
        super(expression);
    }
    @Override
    protected SimpleTemplate concreteThis() {
        return this;
    }
    public String toString() {
        return String.format("'%s'", value().toString());
    }

}
