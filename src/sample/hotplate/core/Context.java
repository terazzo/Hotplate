package sample.hotplate.core;

public interface Context<V, T extends Template<V, T>> {
    Associable<V, T> get(Symbol name);
}
