package sample.hotplate.core.sample2.parser.handler;

import java.util.Collections;
import java.util.List;

import sample.hotplate.core.sample2.SimpleTemplate;
import sample.hotplate.core.sample2.parser.Attribute;
import sample.hotplate.core.sample2.parser.TagHandler;
import sample.hotplate.core.sample2.processor.SimpleInsertProcessor;

public class InsertTagHandler implements TagHandler<Object, SimpleTemplate>{
    @Override
    public String[] tagNames() {
        return new String[] {"insert"};
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
        return new SimpleInsertProcessor.Prototype(
                Attribute.findAttribute("value", attributes).getValue().getSymbol(), 
                Collections.<SimpleTemplate>emptyList());
    }

    @Override
    public SimpleTemplate handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplate> elements) {
        return new SimpleInsertProcessor.Prototype(
                Attribute.findAttribute("value", attributes).getValue().getSymbol(),
                elements);
    }

}
