package sample.hotplate.sample;

import java.util.List;

import org.codehaus.jparsec.Parser;

import sample.hotplate.core.Translator;
import sample.hotplate.sample.parser.ParserFactory;
import sample.hotplate.sample.processor.SimpleDefineProcessor;
import sample.hotplate.sample.processor.SimpleInsertProcessor;
import sample.hotplate.sample.processor.SimpleProcessorPrototype;


public class SimpleTranslator implements Translator<String, Object, SimpleTemplate> {
	@Override
	public SimpleContainer toTemplate(String rawObject) {
		List<SimpleTemplate> elements = parse(rawObject);
		SimpleContainer simpleTemplate = new SimpleContainer(elements);
		return simpleTemplate;
	}
	@Override
	public String fromTemplate(SimpleTemplate template) {
	    final StringBuilder stringBuilder = new StringBuilder();
	    template.traverse(new SimpleTemplateWalker() {
            public void process(SimpleLiteral literal) {
                stringBuilder.append(literal.value().toString());
            }
            public void process(
                    SimpleExpression expression) {
                stringBuilder.append(expression.value().toString());
            }

            public void _process(SimpleTemplate template) {
                if (template.isReducible()) {
                    throw new IllegalArgumentException("Not " + template);
                }
            }
            @Override
            public void process(SimpleContainer container) {
                _process(container);
            }
            @Override
            public void process(SimpleDefineProcessor defineProcessor) {
                _process(defineProcessor);
            }
            @Override
            public void process(SimpleInsertProcessor insertProcessor) {
                _process(insertProcessor);
            }
            @Override
            public void process(SimpleNop nop) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void process(SimpleReference reference) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void process(SimpleProcessorPrototype processorPrototype) {
                _process(processorPrototype);
                
            }
            @Override
            public void process(SimpleWrapper wrapperBase) {
                _process(wrapperBase);
            }
        });
		return stringBuilder.toString();
	}

   	Parser<List<SimpleTemplate>> templateParser =
   	    ParserFactory.instance.newParser();

    private List<SimpleTemplate> parse(String rawObject) {
        return templateParser.parse(rawObject);
    }

}