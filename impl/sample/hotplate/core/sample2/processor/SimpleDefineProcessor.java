package sample.hotplate.core.sample2.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.processor.DefineProcessorBase;
import sample.hotplate.core.sample2.SimpleNop;
import sample.hotplate.core.sample2.SimpleTemplate;

public class SimpleDefineProcessor extends DefineProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {
    public SimpleDefineProcessor(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol, SimpleTemplate value) {
        super(lexicalScope, symbol, value);
    }

    @Override
    protected SimpleTemplate newInstance(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol,
            SimpleTemplate value) {
        return new SimpleDefineProcessor(lexicalScope, symbol, value);
    }

    @Override
    public String getString() {
        throw new IllegalStateException("Unused definition:" + symbol);
    }
    public String toString() {
        return String.format("{define name=\"%s\" value=%s/}", symbol, source);
    }

    @Override
    protected SimpleTemplate getNop() {
        return new SimpleNop();
    }
    public static class Prototype extends SimpleProcessorPrototype {
        private Symbol symbol;
        private SimpleTemplate value;
        public Prototype(Symbol symbol, SimpleTemplate value) {
            super();
            this.symbol = symbol;
            this.value = value;
        }
        protected SimpleTemplate instantiate(
                Context<Object, SimpleTemplate> lexicalContext) {
             return new SimpleDefineProcessor(lexicalContext, symbol, value);
        }
        public String toString() {
            return String.format("{*define name=%s value=%s/}", symbol, value);
        }
    }

}
