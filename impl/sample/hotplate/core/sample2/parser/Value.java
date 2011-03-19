package sample.hotplate.core.sample2.parser;

import sample.hotplate.core.Symbol;

public interface Value {
    boolean isSymbol();
    String getExpression();
    Symbol getSymbol();
}
