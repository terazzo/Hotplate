package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleNop extends AbstractSimpleTemplate implements SimpleTemplate {

    public SimpleNop() {
        super(ContextUtils.<Object, SimpleTemplate>emptyContext());
    }

    @Override
    protected TemplatePair<Object, SimpleTemplate> doApply(
            Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
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
