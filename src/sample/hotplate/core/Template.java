package sample.hotplate.core;

public interface Template<V, T extends Template<V, T>> {
	// apply is 'bind'
	T apply(Context<V, T> context);
	boolean isReducible();
}
