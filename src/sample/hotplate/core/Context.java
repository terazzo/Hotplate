package sample.hotplate.core;

public interface Context<V, T extends Template<V, T>> {
    T get(Symbol name);
}
