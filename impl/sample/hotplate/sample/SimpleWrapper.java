package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleWrapper implements SimpleTemplate {
    protected final SimpleTemplate content;
    public SimpleWrapper(SimpleTemplate content) {
        this.content = content;
    }

    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.pairOf(content);
    }
    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public void traverse(SimpleTemplateWalker walker) {
        walker.process(this);
    }
    public String toString() {
        return String.format("{*wrap value=%s/}", content);
    }

}
