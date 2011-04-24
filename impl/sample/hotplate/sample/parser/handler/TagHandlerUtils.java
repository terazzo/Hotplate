package sample.hotplate.sample.parser.handler;


import sample.hotplate.core.Symbol;
import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.source.SimpleExpression;
import sample.hotplate.sample.source.SimpleReference;
import sample.hotplate.sample.source.SimpleSource;

public final class TagHandlerUtils {
    public static SimpleSource makeSource(Attribute attribute) {
        final SimpleSource source;
        if (attribute.getValue().isSymbol()) {
            Symbol symbol = attribute.getValue().getSymbol();
            source = new SimpleReference(symbol);
        } else {
            final String expression = attribute.getValue().getExpression();
            source = new SimpleExpression(expression);
        }
        return source;
    }
}
