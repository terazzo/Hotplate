package sample.hotplate.sample;

import java.util.List;

import org.codehaus.jparsec.Parser;

import sample.hotplate.core.Translator;
import sample.hotplate.sample.parser.ParserFactory;
import sample.hotplate.sample.processor.SimpleDefineProcessor;
import sample.hotplate.sample.processor.SimpleForeachProcessor;
import sample.hotplate.sample.processor.SimpleIfProcessor;
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
            @Override
            public void process(SimpleValue simpleValue) {
                stringBuilder.append(simpleValue.value().toString());
                
            }

            public void _process(SimpleTemplate template) {
                if (template.isReducible()) {
                    throw new IllegalArgumentException("Not " + template);
                }
            }
            public void process(
                    SimpleExpression expression) {
                _process(expression);
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
                _process(nop);
                
            }
            @Override
            public void process(SimpleReference reference) {
                _process(reference);
            }
            @Override
            public void process(SimpleProcessorPrototype processorPrototype) {
                _process(processorPrototype);
                
            }
            @Override
            public void process(SimpleWrapper wrapperBase) {
                _process(wrapperBase);
            }
            @Override
            public void process(SimpleIfProcessor simpleIfProcessor) {
                _process(simpleIfProcessor);
                
            }
            @Override
            public void process(SimpleForeachProcessor simpleForachProcessor) {
                _process(simpleForachProcessor);
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