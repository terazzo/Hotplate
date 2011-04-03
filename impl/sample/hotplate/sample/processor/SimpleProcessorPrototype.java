package sample.hotplate.sample.processor;

import sample.hotplate.core.impl.ProcessorPrototype;
import sample.hotplate.sample.SimpleTemplate;

public abstract class SimpleProcessorPrototype extends ProcessorPrototype<Object, SimpleTemplate> implements SimpleTemplate {
    @Override
    public String getString() {
        throw new IllegalStateException("Uninstantiate ProcessorPrototype:" + this);
    }
}
