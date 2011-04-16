package sample.hotplate.sample.prototype;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;

public interface SimpleTemplatePrototype {
    public SimpleTemplate instantiate(Context<Object, SimpleTemplate> lexicalContext);
}
