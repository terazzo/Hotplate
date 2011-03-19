package sample.hotplate.core.sample2.parser;

import java.util.List;

import sample.hotplate.core.Template;

public interface TagHandler<V, T extends Template<V, T>> {
    String[] tagNames();
    boolean requireContainerTag();
    boolean requireSingleTag();
    T handleSingleTag(String tagName, List<Attribute> attributes);
    T handleContainerTag(String tagName, List<Attribute> attributes, List<T> elements);
}
