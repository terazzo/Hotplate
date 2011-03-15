package sample.hotplate.core.sample;

import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.pattern.Patterns;

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

	Parser<List<SimpleTemplate>> templateParser = createParser();
    private Parser<List<SimpleTemplate>> createParser() {

        Parser<String> braceStart = Scanners.isChar('{').source();
        Parser<String> braceEnd = Scanners.isChar('}').source();
        Parser<String> textParser = 
            Scanners.pattern(Patterns.regex("((\\\\\\{)|(\\\\\\})|[^{}])*") ,"TEXT").source();

        
        Parser<SimpleTemplate> placeHolder =  textParser.between(braceStart, braceEnd).map(new Map<String, SimpleTemplate>() {
             public SimpleTemplate map(String text) {
                 return new SimpleProcessor(Symbol.of(text));
            }
        });
        Parser<SimpleTemplate> literal = textParser.map(new Map<String, SimpleTemplate>() {
            public SimpleTemplate map(String text) {
                return new SimpleLiteral(text);
           }
        });
        return Parsers.or(placeHolder, literal).many();
    }
    private List<SimpleTemplate> parse(String rawObject) {
        return templateParser.parse(rawObject);
    }
}