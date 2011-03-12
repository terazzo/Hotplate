package sample.hotplate.core;

public interface Context<R, T extends Template<R, T>> {
	T get(Symbol name);
}
