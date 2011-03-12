package sample.hotplate.core.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import sample.hotplate.core.impl.ContextImpl;
import sample.hotplate.core.impl.ValueBase;

public class SimpleCompilerTest {
	@Test
	public void testCompile() {
		SimpleTranslator translator = new SimpleTranslator();
		SimpleContainer template = translator.toTemplate("Hello, {name}");
		assertNotNull(template);
		ContextImpl<Object, SimpleTemplate> context = new ContextImpl<Object, SimpleTemplate>();
//		context.put("name", new ValueBase<Object, SimpleTemplate>("hoge"));
		context.put("name", new ValueBase<Object, SimpleTemplate>(1));
		SimpleContainer applied = template.apply(context);
		assertNotNull(applied);
		String result = applied.getString();
		System.out.println("result = " + result);
		
	}
	@Test
	public void testTonkichi() {
		SimpleTranslator translator = new SimpleTranslator();

		SimpleTemplate template = translator.toTemplate("{���A}{���X���}");
		ContextImpl<Object, SimpleTemplate> context1 = new ContextImpl<Object, SimpleTemplate>();
		context1.put("���A", translator.toTemplate("����ɂ��́A{���q�l��}�l�B"));
		context1.put("���X���", translator.toTemplate("{���X��}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"));
		ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>();
		context2.put("���q�l��", new ValueBase<Object, SimpleTemplate>("���g���g"));
		context2.put("���X��", new ValueBase<Object, SimpleTemplate>("1��21��"));
		SimpleTemplate applied = template.apply(context1);
		SimpleTemplate applied2 = applied.apply(context2);

		String result = applied2.getString();
		System.out.println("result = " + result);
	}
	
	public static SimpleTemplate unit(String varname) {
		return new SimpleProcessor(varname);
	}
	public void testMonad() {
		
	}

}
