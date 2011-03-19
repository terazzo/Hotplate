package sample.hotplate.sample.parser;

import java.util.List;

public class Attribute {
    private final String name;
    private final Value value;
    Attribute(String name, Value value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public Value getValue() {
        return value;
    }
    public static Attribute findAttribute(String name, List<Attribute> attributes) {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(name)) {
                return attribute;
            }
        }
        return null;
    }
}
