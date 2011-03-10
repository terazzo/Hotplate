package sample.hotplate.core.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import sample.hotplate.core.impl.ContextImpl;
import sample.hotplate.core.impl.ValueBase;

public class SimpleCompilerTest {
	@Test
	public void testCompile() {
		SimpleTranslator translator = new SimpleTranslator();
		SimpleContainer template = translator.toTemplate("Hello, $name$");
		assertNotNull(template);
		ContextImpl<Object, SimpleTemplate> context = new ContextImpl<Object, SimpleTemplate>();
//		context.put("name", new ValueBase<Object, SimpleTemplate>("hoge"));
		context.put("name", new ValueBase<Object, SimpleTemplate>(1));
		SimpleContainer applied = template.apply(context);
		assertNotNull(applied);
		String result = applied.getString();
		System.out.println("result = " + result);
		
	}

}
