package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleLiteral;
import sample.hotplate.sample.SimpleReference;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.SimpleDefineProcessor;

public class DefineTagHandler implements TagHandler<Object, SimpleTemplate>{
    @Override
    public String[] tagNames() {
        return new String[] {"define"};
    }
    @Override
    public boolean requireContainerTag() {
        return true;
    }
    @Override
    public boolean requireSingleTag() {
        return true;
    }
    @Override
    public SimpleTemplate handleSingleTag(String tagName,
            List<Attribute> attributes) {
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        final SimpleTemplate value;
        if (valueAttribute.getValue().isSymbol()) {
            Symbol symbol = valueAttribute.getValue().getSymbol();
            value = new SimpleReference(symbol);
        } else {
            final String expression = valueAttribute.getValue().getExpression();
            value = new SimpleLiteral(expression);
        }
        return new SimpleDefineProcessor.Prototype(
                Attribute.findAttribute("name", attributes).getValue().getSymbol(), value);
    }

    @Override
    public SimpleTemplate handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplate> elements) {
        return new SimpleDefineProcessor.Prototype(
                Attribute.findAttribute("name", attributes).getValue().getSymbol(), 
                new SimpleContainer(elements));
    }

}
