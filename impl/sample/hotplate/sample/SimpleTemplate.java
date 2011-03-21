package sample.hotplate.sample;

import sample.hotplate.core.Template;

public interface SimpleTemplate extends Template<Object, SimpleTemplate> {
    void traverse(SimpleTemplateWalker walker);
}
