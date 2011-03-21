package sample.hotplate.sample;

import sample.hotplate.sample.processor.SimpleDefineProcessor;
import sample.hotplate.sample.processor.SimpleForeachProcessor;
import sample.hotplate.sample.processor.SimpleIfProcessor;
import sample.hotplate.sample.processor.SimpleInsertProcessor;
import sample.hotplate.sample.processor.SimpleProcessorPrototype;

public interface SimpleTemplateWalker {
    void process(SimpleContainer container);
    void process(SimpleDefineProcessor defineProcessor);
    void process(SimpleInsertProcessor insertProcessor);
    void process(SimpleLiteral literal);
    void process(SimpleNop nop);
    void process(SimpleReference reference);
    void process(SimpleProcessorPrototype processorPrototype);
    void process(SimpleWrapper wrapperBase);
    void process(SimpleExpression expressionBase);
    void process(SimpleValue simpleValue);
    void process(SimpleIfProcessor simpleIfProcessor);
    void process(SimpleForeachProcessor simpleForachProcessor);

}
