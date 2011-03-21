package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleLiteral implements SimpleTemplate {

	private Object value;
    public SimpleLiteral(Object value) {
		this.value = value;
	}
    public boolean isReducible() {
        return false;
    }
    public Object value() {
        return value;
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
    }

    @Override
    public void traverse(SimpleTemplateWalker walker) {
        walker.process(this);
    }
    public String toString() {
        return String.format("'%s'", value().toString());
    }
}
