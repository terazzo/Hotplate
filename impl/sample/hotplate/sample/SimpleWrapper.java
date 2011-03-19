package sample.hotplate.sample;

import sample.hotplate.core.impl.WrapperBase;

public class SimpleWrapper extends WrapperBase<Object, SimpleTemplate> implements SimpleTemplate {
    public SimpleWrapper(SimpleTemplate content) {
        super(content);
    }
    public String toString() {
        return String.format("{*wrap value=%s/}", content);
    }

}
