package sample.hotplate.core.sample2.parser.handler;

import java.util.List;

import sample.hotplate.core.Symbol;
import sample.hotplate.core.sample2.SimpleContainer;
import sample.hotplate.core.sample2.SimpleLiteral;
import sample.hotplate.core.sample2.SimpleReference;
import sample.hotplate.core.sample2.SimpleTemplate;
import sample.hotplate.core.sample2.parser.Attribute;
import sample.hotplate.core.sample2.parser.TagHandler;
import sample.hotplate.core.sample2.processor.SimpleDefineProcessor;

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
