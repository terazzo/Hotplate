package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;

/**
 * 何もしないTemplate。
 */
public class SimpleNop extends AbstractSimpleTemplate implements SimpleTemplate {
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
