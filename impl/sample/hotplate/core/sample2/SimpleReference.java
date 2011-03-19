package sample.hotplate.core.sample2;

import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.ReferenceBase;

public class SimpleReference extends ReferenceBase<Object, SimpleTemplate> implements SimpleTemplate{

    public SimpleReference(Symbol symbol) {
        super(symbol);
    }

    @Override
    public String getString() {
        return "";
    }
    @Override
    public String toString() {
        return String.format("{*simple value=%s/}", symbol);
    }
}
