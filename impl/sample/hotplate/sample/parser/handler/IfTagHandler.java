package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.prototype.SimpleIfProcessorPrototype;
import sample.hotplate.sample.prototype.SimpleContainerPrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

public class IfTagHandler implements TagHandler {
    @Override
    public String[] tagNames() {
        return new String[] {"if"};
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
        throw new IllegalStateException("If cannot use as a single tag.");
    }

    @Override
    public SimpleTemplatePrototype handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplatePrototype> elements) {
        Attribute conditionAttribute = Attribute.findAttribute("condition", attributes);
        return new SimpleIfProcessorPrototype(
                TagHandlerUtils.makeSource(conditionAttribute), 
                new SimpleContainerPrototype(elements));
    }

}
