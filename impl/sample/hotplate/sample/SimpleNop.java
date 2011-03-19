package sample.hotplate.sample;

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
    public boolean isReducible() {
        return false;
    }

}
