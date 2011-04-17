package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.core.util.TemplatePairUtils;

public class SimpleLiteral extends AbstractSimpleTemplate implements SimpleTemplate {

	private Object value;
    public SimpleLiteral(Object value) {
        super(ContextUtils.<Object, SimpleTemplate>emptyContext());
		this.value = value;
	}
    public boolean isReducible() {
        return false;
    }
    public Object value() {
        return value;
    }
    @Override
    protected TemplatePair<Object, SimpleTemplate> doApply(
            Context<Object, SimpleTemplate> context) {
        return TemplatePairUtils.<Object, SimpleTemplate>pairOf(this);
    }

    @Override
    public String getString() {
        return value.toString();
    }
    public String toString() {
        return String.format("'%s'", value().toString());
    }
}
