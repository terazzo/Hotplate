package sample.hotplate.sample.source;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleWrapper implements SimpleSource {
    private final SimpleTemplate content;
    public SimpleWrapper(SimpleTemplate content) {
        this.content = content;
    }

    @Override
    public SimpleTemplate getAssociable(Context<Object, SimpleTemplate> context) {
        return content;
    }
}
