package sample.hotplate.sample.processor;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.processor.DefineProcessorBase;
import sample.hotplate.sample.SimpleNop;
import sample.hotplate.sample.SimpleTemplate;

public class SimpleDefineProcessor extends DefineProcessorBase<Object, SimpleTemplate> implements SimpleTemplate {
    public SimpleDefineProcessor(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol, SimpleTemplate value) {
        super(lexicalScope, symbol, value);
    }

    @Override
    protected SimpleTemplate newInstance(Context<Object, SimpleTemplate> lexicalScope, Symbol symbol,
            SimpleTemplate value) {
        return new SimpleDefineProcessor(lexicalScope, symbol, value);
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
