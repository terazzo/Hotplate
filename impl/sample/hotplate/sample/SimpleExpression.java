package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleExpression implements SimpleTemplate {

    private String expression;
    public SimpleExpression(String expression) {
        this.expression = expression;
    }
    public boolean isReducible() {
        return false;
    }
    public String value() {
        return expression;
    }
    @Override
    public void traverse(SimpleTemplateWalker walker) {
        walker.process(this);
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
    }



}
