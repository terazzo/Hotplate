package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.SimpleIfProcessor;

public class IfTagHandler implements TagHandler<Object, SimpleTemplate>{
    @Override
    public String[] tagNames() {
        return new String[] {"if"};
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
        throw new IllegalStateException("If cannot use as a single tag.");
    }

    @Override
    public SimpleTemplate handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplate> elements) {
        Attribute conditionAttribute = Attribute.findAttribute("condition", attributes);
        return new SimpleIfProcessor.Prototype(
                TagHandlerUtils.valueSource(conditionAttribute), 
                new SimpleContainer(elements));
    }

}
