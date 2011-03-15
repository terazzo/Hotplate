package sample.hotplate.core.sample2;

import org.junit.Test;

import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.ContextImpl;

public class SimpleCompilerTest {
	@Test
	public void testTonkichi() {
		SimpleTranslator translator = new SimpleTranslator();

		SimpleTemplate template = translator.toTemplate("" +
                "{insert value=aisatsu}" +
                    "{define name=yourname value=customerName /}" +
                "{/insert}" +
                "{insert value=raitenonrei}" +
                    "{define name=raitenbi value=day /}" +
                "{/insert}"
                    );
        ContextImpl<Object, SimpleTemplate> context1 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("aisatsu"), translator.toTemplate("こんにちは、{insert value=yourname}{/insert}様。"))
        .put(Symbol.of("raitenonrei"), translator.toTemplate("{insert value=raitenbi}{/insert}にはご来店いただき、\nまことにありがとうございます。"));
        ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("customerName"), translator.toTemplate("板東トン吉"))
        .put(Symbol.of("day"), translator.toTemplate("1月21日"));
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
		String result = applied2.getString();
		System.out.println("result = " + result);
	}

}
