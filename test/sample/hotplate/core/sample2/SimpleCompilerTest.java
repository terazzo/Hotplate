package sample.hotplate.core.sample2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sample.hotplate.core.Symbol;
import sample.hotplate.core.impl.ContextImpl;

public class SimpleCompilerTest {
    @Test
    public void testTonkichi0() {
        SimpleTranslator translator = new SimpleTranslator();

        SimpleTemplate template = translator.toTemplate("" +
                "{insert value=aisatsu}" +
                    "{define name=yourname value=customerName /}" +
                "{/insert}"
                    );
        ContextImpl<Object, SimpleTemplate> context1 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("aisatsu"), translator.toTemplate("こんにちは、{insert value=yourname/}様。"));
        ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("customerName"), translator.toTemplate("板東トン吉"));
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
        String result = applied2.getString();
        System.out.println("result = " + result);
        assertEquals("こんにちは、板東トン吉様。", result);
    }
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
        .put(Symbol.of("aisatsu"), translator.toTemplate("こんにちは、{insert value=yourname/}様。"))
        .put(Symbol.of("raitenonrei"), translator.toTemplate("{insert value=raitenbi/}にはご来店いただき、\nまことにありがとうございます。"));
        ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("customerName"), translator.toTemplate("板東トン吉"))
        .put(Symbol.of("day"), translator.toTemplate("1月21日"));
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
		String result = applied2.getString();
		System.out.println("result = " + result);
		assertEquals("こんにちは、板東トン吉様。1月21日にはご来店いただき、\nまことにありがとうございます。", result);
	}
	@Test
    public void testByTemplateArgument() {
        SimpleTranslator translator = new SimpleTranslator();

        SimpleTemplate template = translator.toTemplate("" +
                "{define name=aHeader}" +
                    "これは{insert value=title/}です" +
                "{/define}" +
                "{insert value=raitenonrei}" +
                    "{define name=raitenbi value=day /}" +
                    "{define name=header value=aHeader /}" +
                "{/insert}"
                    );
        ContextImpl<Object, SimpleTemplate> context1 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("raitenonrei"), translator.toTemplate(
                "{insert value=header}{define name=title value=\"ヘッダ\"/}{/insert}\n{insert value=raitenbi/}にはご来店いただき、\nまことにありがとうございます。"));
        SimpleTemplate applied = template.apply(context1).template();
        System.out.println("-------");
        System.out.println("applied = " + applied);
        System.out.println("-------");

        ContextImpl<Object, SimpleTemplate> context2 = new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("customerName"), translator.toTemplate("板東トン吉"))
        .put(Symbol.of("day"), translator.toTemplate("1月21日"));
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied2 = " + applied2);
        String result = applied2.getString();
        System.out.println("result = " + result);
        assertEquals("これはヘッダです\n1月21日にはご来店いただき、\nまことにありがとうございます。", result);
    }

   @Test
   public void testLayout() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate layout = translator.toTemplate("" +
               "<html>\n" +
               "<head><title>{insert value=title/}</title></head>\n" +
               "<body>\n" +
               "<h1>{insert value=title/}</h1>\n" +
               "{insert value=contentBody/}" + 
               "<hr>\n" +
               "Copyright(c) {insert value=copyrightOwner/} {insert value=copyrightYear/} All Rights Reserved." + 
               "</body>\n" +
               "</html>");
        ContextImpl<Object, SimpleTemplate> globalSettings =
        new ContextImpl<Object, SimpleTemplate>()
        .put(Symbol.of("copyrightOwner"), translator.toTemplate("terazzo"))
        .put(Symbol.of("copyrightYear"), translator.toTemplate("2011"));
        layout = layout.apply(globalSettings).template();

       ContextImpl<Object, SimpleTemplate> templates =
           new ContextImpl<Object, SimpleTemplate>().
           put(Symbol.of("layout"), layout);

       SimpleTemplate page = translator.toTemplate("" +
               "{insert value=layout}\n" +
               "{define name=title value=\"アンケートフォーム\"/}\n" + 
               "{define name=contentBody}\n" + 
               "ご意見・ご要望がある方はどうぞ。<br>\n" + 
               "<form action=\"#\">\n" + 
               "お名前:<input type=\"text\" name=\"name\"/><br>\n" + 
               "<input type=\"submit\" value=\"送信\"/><br>\n" + 
               "</form>\n" + 
               "{/define}\n" + 
               "{/insert}");
       String output = 
           translator.fromTemplate(page.apply(templates).template());
       System.out.println(output);
       String expected = "<html>\n" +
        "<head><title>アンケートフォーム</title></head>\n" +
        "<body>\n" +
        "<h1>アンケートフォーム</h1>\n" +
        "\n" +
        "ご意見・ご要望がある方はどうぞ。<br>\n" +
        "<form action=\"#\">\n" +
        "お名前:<input type=\"text\" name=\"name\"/><br>\n" +
        "<input type=\"submit\" value=\"送信\"/><br>\n" +
        "</form>\n" +
        "<hr>\n" +
        "Copyright(c) terazzo 2011 All Rights Reserved.</body>\n" +
        "</html>";
       assertEquals(expected, output);
   }
}
