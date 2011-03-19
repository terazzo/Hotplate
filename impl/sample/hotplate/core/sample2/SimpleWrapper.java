package sample.hotplate.core.sample2;

import sample.hotplate.core.impl.WrapperBase;

public class SimpleWrapper extends WrapperBase<Object, SimpleTemplate> implements SimpleTemplate {
    public SimpleWrapper(SimpleTemplate content) {
        super(content);
    }
    @Override
    public String getString() {
        return "";
    }
    public String toString() {
        return String.format("{*wrap value=%s/}", content);
    }

}
