package sample.hotplate.sample.parser;

import sample.hotplate.core.Symbol;

public interface Value {
    boolean isSymbol();
    String getExpression();
    Symbol getSymbol();
}
