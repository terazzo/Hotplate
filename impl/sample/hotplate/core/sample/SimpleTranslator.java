package sample.hotplate.core.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import sample.hotplate.core.Symbol;
import sample.hotplate.core.Translator;


public class SimpleTranslator implements Translator<String, Object, SimpleTemplate> {
	@Override
	public SimpleContainer toTemplate(String rawObject) {
		List<SimpleTemplate> elements = parse(rawObject);
		SimpleContainer simpleTemplate = new SimpleContainer(elements);
		return simpleTemplate;
	}
	@Override
	public String fromTemplate(SimpleTemplate template) {
		return template.getString();
	}
	enum Mode {
		TEXT,
		IN_BRACE
	}
	private List<SimpleTemplate> parse(String rawObject) {
		List<SimpleTemplate> elements = new ArrayList<SimpleTemplate>();
		StringTokenizer stringTokenizer = new StringTokenizer(rawObject, "{}", true);
		Mode mode = Mode.TEXT;
		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			switch (mode) {
			case TEXT:
				if (token.equals("{")) {
					mode = Mode.IN_BRACE;
				} else if (token.equals("}")) {
					throw new IllegalArgumentException("Illegal format string. Unexpected '}' occured." + rawObject);
				} else {
					elements.add(new SimpleLiteral(token));
				}
				break;
			case IN_BRACE:
				if (token.equals("{")) {
					throw new IllegalArgumentException("Illegal format string. Unexpected '{' occured." + rawObject);
				} else if (token.equals("}")) {
					mode = Mode.TEXT;
				} else {
					elements.add(new SimpleProcessor(Symbol.of(token)));				
				}
				break;
			}
		}
		return elements;
	}
}