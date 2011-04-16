package sample.hotplate.sample;

import java.util.List;

import org.codehaus.jparsec.Parser;

import sample.hotplate.core.Translator;
import sample.hotplate.core.util.ContextUtils;
import sample.hotplate.sample.parser.ParserFactory;
import sample.hotplate.sample.prototype.SimpleContainerPrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;


public class SimpleTranslator implements Translator<String, Object, SimpleTemplate> {
	@Override
	public SimpleTemplate toTemplate(String rawObject) {
		List<SimpleTemplatePrototype> elements = parse(rawObject);
		SimpleTemplatePrototype prototype = new SimpleContainerPrototype(elements);
		return prototype.instantiate(ContextUtils.<Object, SimpleTemplate>emptyContext());
	}
	@Override
	public String fromTemplate(SimpleTemplate template) {
	    return template.getString();
	}

   	Parser<List<SimpleTemplatePrototype>> templateParser =
   	    ParserFactory.instance.newParser();

    private List<SimpleTemplatePrototype> parse(String rawObject) {
        return templateParser.parse(rawObject);
    }

}