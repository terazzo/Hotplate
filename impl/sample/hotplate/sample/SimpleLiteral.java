package sample.hotplate.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.TemplatePair;

public class SimpleLiteral extends AbstractSimpleTemplate implements SimpleTemplate {

	private Object value;
    public SimpleLiteral(Object value) {
        super();
		this.value = value;
	}
    public boolean isReducible() {
        return false;
    }
    public Object value() {
        return value;
    }
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        return TemplatePair.<Object, SimpleTemplate>pairOf(this);
    }

    @Override
    public String getString() {
        return value.toString();
    }
    public String toString() {
        return String.format("'%s'", value().toString());
    }
}
