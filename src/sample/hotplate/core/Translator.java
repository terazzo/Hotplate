package sample.hotplate.core;

public interface Translator<R, V, T extends Template<V, T>> {
	T toTemplate(R rawObject);
	R fromTemplate(T template);
}
