package sample.hotplate.core;

public interface Template<V, T extends Template<V, T>> {
    TemplatePair<V, T> apply(Context<V, T> context);
    boolean isReducible();
}
