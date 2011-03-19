package sample.hotplate.core.sample2.parser;

import sample.hotplate.core.Symbol;

public class Expression implements Value {
    private final String expression;
    Expression(String expression) {
        this.expression = expression;
    }
    public boolean isSymbol() {
        return false;
    }
    public String getExpression() {
        return expression;
    }
    public Symbol getSymbol() {
        throw new IllegalStateException("Expression has not Symbol");
    }
}
