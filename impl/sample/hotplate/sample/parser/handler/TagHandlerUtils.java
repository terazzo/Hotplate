package sample.hotplate.sample.parser.handler;


import sample.hotplate.core.Symbol;
import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.source.SimpleExpression;
import sample.hotplate.sample.source.SimpleReference;
import sample.hotplate.sample.source.SimpleTemplateSource;

public final class TagHandlerUtils {
    public static SimpleTemplateSource valueSource(Attribute valueAttribute) {
        final SimpleTemplateSource value;
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
