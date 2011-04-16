package sample.hotplate.sample.parser;

import java.util.List;

import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

public interface TagHandler {
    String[] tagNames();
    boolean requireContainerTag();
    boolean requireSingleTag();
    SimpleTemplatePrototype handleSingleTag(String tagName, List<Attribute> attributes);
    SimpleTemplatePrototype handleContainerTag(String tagName, List<Attribute> attributes, List<SimpleTemplatePrototype> elements);
}
