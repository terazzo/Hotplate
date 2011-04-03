package sample.hotplate.sample;

import java.util.List;

import org.codehaus.jparsec.Parser;

import sample.hotplate.core.Translator;
import sample.hotplate.sample.parser.ParserFactory;


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

   	Parser<List<SimpleTemplate>> templateParser =
   	    ParserFactory.instance.newParser();

    private List<SimpleTemplate> parse(String rawObject) {
        return templateParser.parse(rawObject);
    }

}