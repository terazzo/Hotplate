package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleValue implements SimpleTemplate {

    private Object value;
    public SimpleValue(Object value) {
        this.value = value;
    }
    public boolean isReducible() {
        return false;
    }
    public Object value() {
        return value;
    }
    @Override
    public String getString() {
        return value.toString();
    }
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
    }



}
