package sample.hotplate.sample.parser.handler;


import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleExpression;
import sample.hotplate.sample.SimpleReference;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.parser.Attribute;

public final class TagHandlerUtils {
    public static SimpleTemplate valueSource(Attribute valueAttribute) {
        final SimpleTemplate value;
        if (valueAttribute.getValue().isSymbol()) {
            Symbol symbol = valueAttribute.getValue().getSymbol();
            value = new SimpleReference(symbol);
        } else {
            final String expression = valueAttribute.getValue().getExpression();
            value = new SimpleExpression(expression);
        }
        return value;
    }
}
