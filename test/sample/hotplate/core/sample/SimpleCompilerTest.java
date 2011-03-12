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

		SimpleTemplate template = translator.toTemplate("{挨拶}{来店御礼}");
		ContextImpl<Object, SimpleTemplate> context1 = new ContextImpl<Object, SimpleTemplate>();
		context1.put("挨拶", translator.toTemplate("こんにちは、{お客様名}様。"));
		context1.put("来店御礼", translator.toTemplate("{来店日}にはご来店いただき、\nまことにありがとうございます。"));
		ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>();
		context2.put("お客様名", new ValueBase<Object, SimpleTemplate>("板東トン吉"));
		context2.put("来店日", new ValueBase<Object, SimpleTemplate>("1月21日"));
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
