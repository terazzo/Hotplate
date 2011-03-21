package sample.hotplate.sample.parser.handler;

import java.util.List;

import sample.hotplate.sample.SimpleContainer;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.TagHandler;
import sample.hotplate.sample.processor.SimpleInsertProcessor;

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
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        return new SimpleInsertProcessor.Prototype(
                TagHandlerUtils.valueSource(valueAttribute),
                new SimpleNop());
    }

    @Override
    public SimpleTemplate handleContainerTag(String tagName,
            List<Attribute> attributes, List<SimpleTemplate> elements) {
        Attribute valueAttribute = Attribute.findAttribute("value", attributes);
        return new SimpleInsertProcessor.Prototype(
                TagHandlerUtils.valueSource(valueAttribute), 
                new SimpleContainer(elements));
    }

}
