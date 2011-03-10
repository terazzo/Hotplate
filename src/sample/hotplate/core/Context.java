package sample.hotplate.core;

public interface Context<R, T extends Template<R, T>> {
	void put(String name, Associable<R, T> value);
	Associable<R, T> get(String name);
}
