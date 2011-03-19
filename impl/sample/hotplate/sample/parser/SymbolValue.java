package sample.hotplate.sample.parser;

import sample.hotplate.core.Symbol;

public class SymbolValue implements Value {
    private final Symbol symbol;
    SymbolValue(String label) {
        this.symbol = Symbol.of(label);
    }
    @Override
    public boolean isSymbol() {
        return true;
    }
    public String getExpression() {
        throw new IllegalStateException("SymbolValue has not Expression");
    }
    @Override
    public Symbol getSymbol() {
        return symbol;
    }
}
