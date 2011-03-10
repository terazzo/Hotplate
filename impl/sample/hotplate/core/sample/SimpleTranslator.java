package sample.hotplate.core.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

	private List<SimpleTemplate> parse(String rawObject) {
		List<SimpleTemplate> elements = new ArrayList<SimpleTemplate>();
		StringTokenizer stringTokenizer = new StringTokenizer(rawObject, "$");
		boolean inExpression = false;
		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			elements.add(inExpression ? new SimpleProcessor(token) : new LiteralTemplate(token));
			inExpression = !inExpression;
		}
		return elements;
	}
}
