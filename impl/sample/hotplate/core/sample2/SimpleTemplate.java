package sample.hotplate.core.sample2;

import java.util.Arrays;
import java.util.List;

import sample.hotplate.core.Template;

public interface SimpleTemplate extends Template<Object, SimpleTemplate> {
	String getString();
	public class Utils {
		public static List<SimpleTemplate> asList(SimpleTemplate template) {
			return Arrays.asList(template);
		}
	}
}
