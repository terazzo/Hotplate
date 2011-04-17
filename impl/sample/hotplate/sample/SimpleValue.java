package sample.hotplate.sample;

import sample.hotplate.core.Value;

public class SimpleValue implements Value<Object, SimpleTemplate> {

    private Object value;
    public SimpleValue(Object value) {
        this.value = value;
    }
    public Object value() {
        return value;
    }
    @Override
    public boolean isTemplate() {
        return false;
    }
    @Override
    public Value<Object, SimpleTemplate> asValue() {
        return this;
    }
    @Override
    public SimpleTemplate asTemplate() {
        return new SimpleLiteral(value);
    }

}
