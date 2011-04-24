package sample.hotplate.sample;

import sample.hotplate.core.Value;


public abstract class AbstractSimpleTemplate implements SimpleTemplate {
    @Override
    public boolean isTemplate() {
        return true;
    }
    @Override
    public Value<Object, SimpleTemplate> asValue() {
        return new SimpleValue(this);
    }
    @Override
    public SimpleTemplate asTemplate() {
        return this;
    }
}
