package sample.hotplate.core.sample2.processor;

import sample.hotplate.core.impl.ProcessorPrototype;
import sample.hotplate.core.sample2.SimpleTemplate;

public abstract class SimpleProcessorPrototype extends ProcessorPrototype<Object, SimpleTemplate> implements SimpleTemplate {
    @Override
    public String getString() {
        throw new IllegalStateException("Unused prototype.");
    }

}
