package sample.hotplate.core;

import sample.hotplate.core.impl.ContainerBase;
import sample.hotplate.core.impl.ExpressionBase;
import sample.hotplate.core.impl.LiteralBase;
import sample.hotplate.core.impl.NopBase;
import sample.hotplate.core.impl.ProcessorPrototype;
import sample.hotplate.core.impl.ReferenceBase;
import sample.hotplate.core.impl.WrapperBase;
import sample.hotplate.core.impl.processor.DefineProcessorBase;
import sample.hotplate.core.impl.processor.InsertProcessorBase;

public interface TemplateWalker<V, T extends Template<V,T>> {

    void process(ContainerBase<V, T> container);
    void process(DefineProcessorBase<V, T> defineProcessor);
    void process(InsertProcessorBase<V, T> insertProcessor);
    void process(LiteralBase<V, T> literal);
    void process(NopBase<V, T> nop);
    void process(ReferenceBase<V, T> reference);
    void process(ProcessorPrototype<V, T> processorPrototype);
    void process(WrapperBase<V, T> wrapperBase);
    void process(ExpressionBase<V, T> expressionBase);

}
