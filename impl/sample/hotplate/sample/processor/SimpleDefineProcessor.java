package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.TemplatePair;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.source.SimpleSource;

public class SimpleDefineProcessor implements SimpleTemplate {
    /** 初期化時のContext */
    private final Context<Object, SimpleTemplate> lexicalContext;
    /** name属性で指定したSymbol */
    private final Symbol symbol;
    /** value属性またはコンテナの中身で定義した値 */
    private final SimpleSource source;

    public SimpleDefineProcessor(
            Context<Object, SimpleTemplate> lexicalContext, Symbol symbol, SimpleSource source) {
        super();
        this.lexicalContext = lexicalContext;
        this.symbol = symbol;
        this.source = source;
    }
    @Override
    public TemplatePair<Object, SimpleTemplate> apply(Context<Object, SimpleTemplate> context) {
        Context<Object, SimpleTemplate> totalContext = ContextUtils.merge(context, lexicalContext);

        SimpleTemplate definition = source.getTemplate(totalContext);
        if (definition == null) {
            return TemplatePair.<Object, SimpleTemplate>pairOf(this);
        }

        Context<Object, SimpleTemplate> newContext = ContextUtils.newContext(symbol, definition);
        return TemplatePair.pairOf(new SimpleNop(), newContext);
    }
    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public String getString() {
        throw new IllegalStateException("Unevaluated define:" + source);
    }
}
