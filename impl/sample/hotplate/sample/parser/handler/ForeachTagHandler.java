package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.SimpleForeachProcessor;

public class ForeachTagHandler implements TagHandler<Object, SimpleTemplate>{
    @Override
    public String[] tagNames() {
        return new String[] {"foreach"};
    }
    @Override
    public boolean requireContainerTag() {
        return true;
    }
    @Override
    public boolean requireSingleTag() {
        return false;
    }
    @Override
    public SimpleTemplate handleSingleTag(String tagName,
            List<Attribute> attributes) {
        throw new IllegalStateException("Foreach cannot use as a single tag.");
    }

    @Override
    public SimpleTemplate handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplate> elements) {
        Attribute itemsAttribute = Attribute.findAttribute("items", attributes);
        Attribute varAttribute = Attribute.findAttribute("var", attributes);
        return new SimpleForeachProcessor.Prototype(
                TagHandlerUtils.valueSource(itemsAttribute), 
                varAttribute.getValue().getSymbol(), 
                new SimpleContainer(elements));
    }

}
