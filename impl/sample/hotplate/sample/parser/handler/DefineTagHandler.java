package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.prototype.SimpleDefineProcessorPrototype;
import sample.hotplate.sample.prototype.SimpleContainerPrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

public class DefineTagHandler implements TagHandler {
    @Override
    public String[] tagNames() {
        return new String[] {"define"};
    }
    @Override
    public boolean requireSingleTag(String tagName) {
        return true;
    }
    @Override
    public boolean requireContainerTag(String tagName) {
        return true;
    }
    @Override
    public SimpleTemplatePrototype handleSingleTag(String tagName,
            List<Attribute> attributes) {
        Attribute nameAttribute = Attribute.findAttribute("name", attributes);
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        return new SimpleDefineProcessorPrototype(
                nameAttribute.getValue().getSymbol(), 
                TagHandlerUtils.makeSource(valueAttribute));
    }

    @Override
    public SimpleTemplatePrototype handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplatePrototype> elements) {
        Attribute nameAttribute = Attribute.findAttribute("name", attributes);
        return new SimpleDefineProcessorPrototype(
                nameAttribute.getValue().getSymbol(), new SimpleContainerPrototype(elements));
    }

}
