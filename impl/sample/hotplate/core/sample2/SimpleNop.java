package sample.hotplate.core.sample2;

import sample.hotplate.core.impl.NopBase;

public class SimpleNop extends NopBase<Object, SimpleTemplate> implements SimpleTemplate{

    public SimpleNop() {
        super();
    }

    @Override
    protected SimpleTemplate concreteThis() {
        return this;
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
