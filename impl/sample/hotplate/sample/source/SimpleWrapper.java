package sample.hotplate.sample.source;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleWrapper implements SimpleTemplateSource {
    protected final SimpleTemplate content;
    public SimpleWrapper(SimpleTemplate content) {
        this.content = content;
    }

    @Override
    public SimpleTemplate getTemplate(Context<Object, SimpleTemplate> context) {
        return content;
    }
}
