package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;

public class SimpleNop extends AbstractSimpleTemplate implements SimpleTemplate {

    public SimpleNop() {
        super();
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePair.<Object, SimpleTemplate>pairOf(this);
    }


    @Override
    public String getString() {
        return "";
    }
    @Override
    public boolean isReducible() {
        return false;
    }

}
