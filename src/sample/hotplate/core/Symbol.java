package sample.hotplate.core;

public final class Symbol {
	private final String label;
	public static Symbol of(String label) {
		if (label == null) {
			throw new IllegalArgumentException("label required.");
		}
		return new Symbol(label);
	}
	private Symbol(String label) {
		this.label = label;
	}
	@Override
	public int hashCode() {
		return label.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Symbol)) {
			return false;
		}
		return label.equals(((Symbol) obj).label);
	}
	@Override
	public String toString() {
		return String.format("<%s: '%s'>", getClass(), label);
	}
	
}
