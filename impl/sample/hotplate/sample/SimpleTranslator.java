package sample.hotplate.sample;

import java.util.List;

import org.codehaus.jparsec.Parser;

import sample.hotplate.core.Template;
import sample.hotplate.core.TemplateWalker;
import sample.hotplate.core.Translator;
import sample.hotplate.core.impl.ContainerBase;
import sample.hotplate.core.impl.ExpressionBase;
import sample.hotplate.core.impl.LiteralBase;
import sample.hotplate.core.impl.NopBase;
import sample.hotplate.core.impl.ProcessorPrototype;
import sample.hotplate.core.impl.ReferenceBase;
import sample.hotplate.core.impl.WrapperBase;
import sample.hotplate.core.impl.processor.DefineProcessorBase;
import sample.hotplate.core.impl.processor.InsertProcessorBase;
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
	    final StringBuilder stringBuilder = new StringBuilder();
	    template.traverse(new TemplateWalker<Object, SimpleTemplate>() {
            public void process(LiteralBase<Object, SimpleTemplate> literal) {
                stringBuilder.append(literal.value().toString());
            }
            public void process(
                    ExpressionBase<Object, SimpleTemplate> expression) {
                stringBuilder.append(expression.value().toString());
            }

            public void _process(Template<Object, SimpleTemplate> template) {
                if (template.isReducible()) {
                    throw new IllegalArgumentException("Not " + template);
                }
            }

            public void process(WrapperBase<Object, SimpleTemplate> wrapper) {
                _process(wrapper);
            }
            public void process(
                    ProcessorPrototype<Object, SimpleTemplate> processorPrototype) {
                _process(processorPrototype);
            }
            public void process(ReferenceBase<Object, SimpleTemplate> reference) {
                _process(reference);
            }
            public void process(NopBase<Object, SimpleTemplate> nop) {
                _process(nop);
            }
            public void process(
                    InsertProcessorBase<Object, SimpleTemplate> insertProcessor) {
                _process(insertProcessor);
            }
            public void process(
                    DefineProcessorBase<Object, SimpleTemplate> defineProcessor) {
                _process(defineProcessor);
            }
            public void process(ContainerBase<Object, SimpleTemplate> container) {
                _process(container);
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