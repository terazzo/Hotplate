package sample.hotplate.sample.parser.handler;

import java.util.Collections;
import java.util.List;

import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.prototype.SimpleInsertProcessorPrototype;
import sample.hotplate.sample.prototype.SimpleContainerPrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

public class InsertTagHandler implements TagHandler {
    @Override
    public String[] tagNames() {
        return new String[] {"insert"};
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
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        return new SimpleInsertProcessorPrototype(
                TagHandlerUtils.makeSource(valueAttribute),
                new SimpleContainerPrototype(Collections.<SimpleTemplatePrototype>emptyList()));
    }

    @Override
    public SimpleTemplatePrototype handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplatePrototype> elements) {
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        return new SimpleInsertProcessorPrototype(
                TagHandlerUtils.makeSource(valueAttribute), 
                new SimpleContainerPrototype(elements));
    }

}
