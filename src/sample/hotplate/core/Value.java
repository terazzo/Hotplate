package sample.hotplate.core;

public interface Value<V, T extends Template<V, T>> extends Associable<V, T> {
	V value();
}
