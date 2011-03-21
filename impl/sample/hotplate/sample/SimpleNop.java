package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleNop implements SimpleTemplate{

    public SimpleNop() {
        super();
    }

    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
    }


    @Override
    public void traverse(SimpleTemplateWalker walker) {
        walker.process(this);
    }
    @Override
    public boolean isReducible() {
        return false;
    }

}
