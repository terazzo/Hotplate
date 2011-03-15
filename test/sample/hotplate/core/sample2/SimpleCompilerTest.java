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
        .put(Symbol.of("aisatsu"), translator.toTemplate("����ɂ��́A{insert value=yourname}{/insert}�l�B"))
        .put(Symbol.of("raitenonrei"), translator.toTemplate("{insert value=raitenbi}{/insert}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"));
        ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("customerName"), translator.toTemplate("���g���g"))
        .put(Symbol.of("day"), translator.toTemplate("1��21��"));
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
		String result = applied2.getString();
		System.out.println("result = " + result);
	}

}
