package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.prototype.SimpleForeachProcessorPrototype;
import sample.hotplate.sample.prototype.SimpleContainerPrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

public class ForeachTagHandler implements TagHandler{
    @Override
    public String[] tagNames() {
        return new String[] {"foreach"};
    }
    @Override
    public boolean requireSingleTag(String tagName) {
        return false;
    }
    @Override
    public boolean requireContainerTag(String tagName) {
        return true;
    }
    @Override
    public SimpleTemplatePrototype handleSingleTag(String tagName,
            List<Attribute> attributes) {
        throw new IllegalStateException("Foreach cannot use as a single tag.");
    }

    @Override
    public SimpleTemplatePrototype handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplatePrototype> elements) {
        Attribute itemsAttribute = Attribute.findAttribute("items", attributes);
        Attribute varAttribute = Attribute.findAttribute("var", attributes);
        return new SimpleForeachProcessorPrototype(
                TagHandlerUtils.makeSource(itemsAttribute), 
                varAttribute.getValue().getSymbol(), 
                new SimpleContainerPrototype(elements));
    }

}
