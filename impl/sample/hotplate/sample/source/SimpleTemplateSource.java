package sample.hotplate.sample.source;

import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;

public interface SimpleTemplateSource {
    SimpleTemplate getTemplate(Context<Object, SimpleTemplate> context);
}
