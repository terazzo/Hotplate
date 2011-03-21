package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.SimpleContainer;
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
        Attribute nameAttribute = Attribute.findAttribute("name", attributes);
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        return new SimpleDefineProcessor.Prototype(
                nameAttribute.getValue().getSymbol(), 
                TagHandlerUtils.valueSource(valueAttribute));
    }

    @Override
    public SimpleTemplate handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplate> elements) {
        Attribute nameAttribute = Attribute.findAttribute("name", attributes);
        return new SimpleDefineProcessor.Prototype(
                nameAttribute.getValue().getSymbol(), new SimpleContainer(elements));
    }

}
