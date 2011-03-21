package sample.hotplate.sample;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.util.ContextBuilder;

public class SimpleCompilerTest {
    @Test
    public void testTonkichi0() {
        SimpleTranslator translator = new SimpleTranslator();

        SimpleTemplate template = translator.toTemplate("" +
                "{insert value=aisatsu}" +
                    "{define name=yourname value=customerName /}" +
                "{/insert}"
                    );
        Context<Object, SimpleTemplate> context1 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("aisatsu"), translator.toTemplate("����ɂ��́A{insert value=yourname/}�l�B"))
            .context();
        Context<Object, SimpleTemplate> context2 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("customerName"), translator.toTemplate("���g���g"))
            .context();
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
        String result = translator.fromTemplate(applied2);
        System.out.println("result = " + result);
        assertEquals("����ɂ��́A���g���g�l�B", result);
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
        Context<Object, SimpleTemplate> context1 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("aisatsu"), translator.toTemplate("����ɂ��́A{insert value=yourname/}�l�B"))
            .put(Symbol.of("raitenonrei"), translator.toTemplate("{insert value=raitenbi/}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"))
            .context();
        Context<Object, SimpleTemplate> context2 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("customerName"), translator.toTemplate("���g���g"))
            .put(Symbol.of("day"), translator.toTemplate("1��21��"))
            .context();
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
		String result = translator.fromTemplate(applied2);
		System.out.println("result = " + result);
		assertEquals("����ɂ��́A���g���g�l�B1��21���ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B", result);
	}
    @Test
    public void testTonkichi3() {
        SimpleTranslator translator = new SimpleTranslator();

        SimpleTemplate template = translator.toTemplate("" +
                "{insert value=aisatsu}" +
                    "{define name=yourname value=customerName /}" +
                "{/insert}" +
                "{insert value=raitenonrei}" +
                    "{define name=raitenbi value=day /}" +
                "{/insert}"
                    );
        Context<Object, SimpleTemplate> context1 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("aisatsu"), translator.toTemplate("����ɂ��́A{insert value=yourname/}�l�B"))
            .put(Symbol.of("raitenonrei"), translator.toTemplate("{insert value=raitenbi/}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"))
            .context();
        Context<Object, SimpleTemplate> context2 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("customerName"), translator.toTemplate("���g���g"))
            .context();
        Context<Object, SimpleTemplate> context3 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("day"), translator.toTemplate("1��21��"))
            .context();
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();
        SimpleTemplate applied3 = applied2.apply(context3).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
        String result = translator.fromTemplate(applied3);
        System.out.println("result = " + result);
        assertEquals("����ɂ��́A���g���g�l�B1��21���ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B", result);
    }
	@Test
    public void testByTemplateArgument() {
        SimpleTranslator translator = new SimpleTranslator();

        SimpleTemplate template = translator.toTemplate("" +
                "{define name=aHeader}" +
                    "�����{insert value=title/}�ł�" +
                "{/define}" +
                "{insert value=raitenonrei}" +
                    "{define name=raitenbi value=day /}" +
                    "{define name=header value=aHeader /}" +
                "{/insert}"
                    );
        Context<Object, SimpleTemplate> context1 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("raitenonrei"), translator.toTemplate(
                "{insert value=header}{define name=title value=\"'�w�b�_'\"/}{/insert}\n{insert value=raitenbi/}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"))
            .context();
        SimpleTemplate applied = template.apply(context1).template();
        System.out.println("-------");
        System.out.println("applied = " + applied);
        System.out.println("-------");

        Context<Object, SimpleTemplate> context2 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("customerName"), translator.toTemplate("���g���g"))
            .put(Symbol.of("day"), translator.toTemplate("1��21��"))
            .context();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied2 = " + applied2);
        String result = translator.fromTemplate(applied2);
        System.out.println("result = " + result);
        assertEquals("����̓w�b�_�ł�\n1��21���ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B", result);
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
        Context<Object, SimpleTemplate> globalSettings =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("copyrightOwner"), translator.toTemplate("terazzo"))
            .put(Symbol.of("copyrightYear"), translator.toTemplate("2011"))
            .context();
        layout = layout.apply(globalSettings).template();

        Context<Object, SimpleTemplate> templates =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("layout"), layout)
            .context();

       SimpleTemplate page = translator.toTemplate("" +
               "{insert value=layout}\n" +
               "{define name=title value=\"'�A���P�[�g�t�H�[��'\"/}\n" + 
               "{define name=contentBody}\n" + 
               "���ӌ��E���v�]��������͂ǂ����B<br>\n" + 
               "<form action=\"#\">\n" + 
               "�����O:<input type=\"text\" name=\"name\"/><br>\n" + 
               "<input type=\"submit\" value=\"���M\"/><br>\n" + 
               "</form>\n" + 
               "{/define}\n" + 
               "{/insert}");
       String output = 
           translator.fromTemplate(page.apply(templates).template());
       System.out.println(output);
       String expected = "<html>\n" +
        "<head><title>�A���P�[�g�t�H�[��</title></head>\n" +
        "<body>\n" +
        "<h1>�A���P�[�g�t�H�[��</h1>\n" +
        "\n" +
        "���ӌ��E���v�]��������͂ǂ����B<br>\n" +
        "<form action=\"#\">\n" +
        "�����O:<input type=\"text\" name=\"name\"/><br>\n" +
        "<input type=\"submit\" value=\"���M\"/><br>\n" +
        "</form>\n" +
        "<hr>\n" +
        "Copyright(c) terazzo 2011 All Rights Reserved.</body>\n" +
        "</html>";
       assertEquals(expected, output);
   }
   @Test
   public void testSope() {
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
        Context<Object, SimpleTemplate> globalSettings =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("copyrightOwner"), translator.toTemplate("terazzo"))
            .put(Symbol.of("copyrightYear"), translator.toTemplate("2011"))
            .context();
        layout = layout.apply(globalSettings).template();

        Context<Object, SimpleTemplate> context =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("layout"), layout)
            .put(Symbol.of("message"), new SimpleLiteral("���ӌ��E���v�]��������͂ǂ����B"))
            .context();

       SimpleTemplate page = translator.toTemplate("" +
               "{insert value=layout}\n" +
               "{define name=title value=\"'�A���P�[�g�t�H�[��'\"/}\n" + 
               "{define name=contentBody}\n" + 
               "{insert value=message/}<br>\n" + 
               "<form action=\"#\">\n" + 
               "�����O:<input type=\"text\" name=\"name\"/><br>\n" + 
               "<input type=\"submit\" value=\"���M\"/><br>\n" + 
               "</form>\n" + 
               "{/define}\n" + 
               "{/insert}");
       String output = 
           translator.fromTemplate(page.apply(context).template());
       System.out.println(output);
       String expected = "<html>\n" +
        "<head><title>�A���P�[�g�t�H�[��</title></head>\n" +
        "<body>\n" +
        "<h1>�A���P�[�g�t�H�[��</h1>\n" +
        "\n" +
        "���ӌ��E���v�]��������͂ǂ����B<br>\n" +
        "<form action=\"#\">\n" +
        "�����O:<input type=\"text\" name=\"name\"/><br>\n" +
        "<input type=\"submit\" value=\"���M\"/><br>\n" +
        "</form>\n" +
        "<hr>\n" +
        "Copyright(c) terazzo 2011 All Rights Reserved.</body>\n" +
        "</html>";
       assertEquals(expected, output);
   }
   public static class Customer {
       public String firstName;
       public String lastName;
       public boolean isMember;
   }
   @Test
   public void testIf() {
       Customer customer = new Customer();
       customer.firstName = "�g���g";
       customer.isMember = true;
       
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("customer"), new SimpleValue(customer))
           .context();

       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate template = translator.toTemplate("" +
           "{if condition=\"customer.isMember\"}" +
           "�悤�����A���{insert value=\"customer.firstName\"/}" + 
           "{/if}" +
       "");
       String output = 
           translator.fromTemplate(template.apply(context).template());
       System.out.println(output);
       String expected = "�悤�����A����g���g";
       assertEquals(expected, output);
   }
   @Test
   public void testForeach() {
       List<Customer> customers = new ArrayList<Customer>();
       customers.add(new Customer(){{firstName="hoge";}});
       customers.add(new Customer(){{firstName="fuga";}});
       customers.add(new Customer(){{firstName="hige";}});
       
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("customers"), new SimpleValue(customers))
           .context();

       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate template = translator.toTemplate("" +
           "{foreach items=\"customers\" var=customer}" +
           "{insert value=\"customer.firstName\"/}�A" + 
           "{/foreach}" +
       "");
       String output = 
           translator.fromTemplate(template.apply(context).template());
       System.out.println(output);
       String expected = "hoge�Afuga�Ahige�A";
       assertEquals(expected, output);
   }
   @Test
   public void testFib() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate template = translator.toTemplate("" +
           "{define name=fib}" +
           "{if condition=\"i==0\"}{/if}" +
           "{if condition=\"i==1\"}*{/if}" +
           "{if condition=\"i>=2\"}" +
               "{insert value=fib}" +
                   "{define name=i value=\"i-1\"/}" +
                   "{define name=fib value=fib/}" +
               "{/insert}" +
                   "{insert value=fib}" +
                   "{define name=i value=\"i-2\"/}" +
                   "{define name=fib value=fib/}" +
               "{/insert}" +
           "{/if}" +
           "{/define}" +
           "{insert value=fib}" + 
               "{define name=i value=count/}" +
               "{define name=fib value=fib/}" +
           "{/insert}" +
       "");
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("count"), new SimpleValue(5))
           .context();

       String output = 
           translator.fromTemplate(template.apply(context).template());
       System.out.println(output);
       String expected = "*****";
       assertEquals(expected, output);
   }
}
