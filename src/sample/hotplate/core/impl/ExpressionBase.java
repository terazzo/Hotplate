package sample.hotplate.core.impl;

import sample.hotplate.core.Context;
import sample.hotplate.core.Template;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.TemplateWalker;
import sample.hotplate.core.util.TemplatePairUtils;

public abstract class ExpressionBase<V, T extends Template<V, T>> implements Template<V, T> {

	private V expression;
	public ExpressionBase(V expression) {
		this.expression = expression;
	}
	public boolean isReducible() {
		return false;
	}
	public V value() {
		return expression;
	}
	@Override
	public TemplatePair<V, T> apply(Context<V, T> context) {
		return TemplatePairUtils.pairOf(concreteThis());
	}
	protected abstract T concreteThis();

    @Override
    public void traverse(TemplateWalker<V, T> walker) {
        walker.process(this);
    }

}
