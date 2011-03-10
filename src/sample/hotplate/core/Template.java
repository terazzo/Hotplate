package sample.hotplate.core;

public interface Template<V, T extends Template<V, T>> extends Associable<V, T> {
	T apply(Context<V, T> context);
	boolean isReducible();
}
