package sample.hotplate.core;

public interface Associable<V, T extends Template<V, T>> {
	boolean isTemplate();
	Value<V, T> asValue();
	Template<V, T> asTemplate();
}
