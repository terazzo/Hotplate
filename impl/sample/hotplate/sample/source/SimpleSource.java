package sample.hotplate.sample.source;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.sample.SimpleTemplate;

public interface SimpleSource {
    Associable<Object, SimpleTemplate> getAssociable(Context<Object, SimpleTemplate> context);
}
