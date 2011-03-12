package sample.hotplate.core.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.ContextImpl;

public class SimpleCompilerTest {
	@Test
	public void testCompile() {
		SimpleTranslator translator = new SimpleTranslator();
		SimpleContainer template = translator.toTemplate("Hello, {name}");
		assertNotNull(template);
		ContextImpl<Object, SimpleTemplate> context = new ContextImpl<Object, SimpleTemplate>();
//		context.put("name", new ValueBase<Object, SimpleTemplate>("hoge"));
		context.put(Symbol.of("name"), new SimpleLiteral(1));
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
		context1.put(Symbol.of("���A"), translator.toTemplate("����ɂ��́A{���q�l��}�l�B"));
		context1.put(Symbol.of("���X���"), translator.toTemplate("{���X��}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"));
		ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>();
		context2.put(Symbol.of("���q�l��"), new SimpleLiteral("���g���g"));
		context2.put(Symbol.of("���X��"), new SimpleLiteral("1��21��"));
		SimpleTemplate applied = template.apply(context1);
		SimpleTemplate applied2 = applied.apply(context2);

		String result = applied2.getString();
		System.out.println("result = " + result);
	}
	
	public static Context<Object, SimpleTemplate> unit = new Context<Object, SimpleTemplate>() {
		@Override
		public SimpleTemplate get(Symbol name) {
			return new SimpleProcessor(name);
		}
	};
	public static SimpleTemplate unit(Symbol varname) {
		return unit.get(varname);
	}
	@Test
	public void testMonad1() {
		Symbol symbol = Symbol.of("aaa");
		ContextImpl<Object, SimpleTemplate> context = new ContextImpl<Object, SimpleTemplate>();
		context.put(symbol, new SimpleLiteral("hoge"));
		
		assertEquals(
	        unit(symbol).apply(context),
	        context.get(symbol));
	}
	@Test
    public void tertRule2() throws Throwable {
		Symbol symbol = Symbol.of("aaa");
		ContextImpl<Object, SimpleTemplate> context = new ContextImpl<Object, SimpleTemplate>();
		context.put(symbol, new SimpleLiteral("hoge"));
        assertEquals(
                unit(symbol).apply(unit),
                unit(symbol));
     }
    @Test
    public void tertRule3() throws Throwable {
		SimpleTranslator translator = new SimpleTranslator();

		Symbol symbol = Symbol.of("���A");

		final ContextImpl<Object, SimpleTemplate> context1 = new ContextImpl<Object, SimpleTemplate>();
		context1.put(Symbol.of("���A"), translator.toTemplate("����ɂ��́A{���q�l��}�l�B"));

		final ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>();
		context2.put(Symbol.of("���q�l��"), new SimpleLiteral("���g���g"));

	    Context<Object, SimpleTemplate> preapplied =
	        new Context<Object, SimpleTemplate>() {
				public SimpleTemplate get(Symbol name) {
					return context1.get(name).apply(context2);
				}
	        };
		assertEquals(
                unit(symbol).apply(context1).apply(context2),
                unit(symbol).apply(preapplied));
    }

}
