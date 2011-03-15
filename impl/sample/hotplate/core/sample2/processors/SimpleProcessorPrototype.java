package sample.hotplate.core.sample2.processors;

import sample.hotplate.core.processor.ProcessorPrototype;
import sample.hotplate.core.sample2.SimpleTemplate;

public abstract class SimpleProcessorPrototype extends ProcessorPrototype<Object, SimpleTemplate> implements SimpleTemplate {
    @Override
    public String getString() {
        throw new IllegalStateException("Unused prototype.");
    }

}
