package sample.hotplate.core;

public interface TemplatePair<V, T extends Template<V, T>> {
    T template();
    Context<V, T> context();
}
