package sample.hotplate.sample;

import static org.junit.Assert.assertEquals;

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
                    "{define name=okyakusamamei value=customerName /}" +
                "{/insert}" +
                "{insert value=raitenonrei}" +
                    "{define name=raitenbi value=visitDay /}" +
                "{/insert}"
                    );
        Context<Object, SimpleTemplate> context1 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("aisatsu"), translator.toTemplate("����ɂ��́A{insert value=okyakusamamei/}�l�B"))
            .put(Symbol.of("raitenonrei"),translator.toTemplate("{insert value=raitenbi/}�ɂ͂����X���������A\n" +
            		"�܂��Ƃɂ��肪�Ƃ��������܂��B"))
            .context();
        Context<Object, SimpleTemplate> context2 =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("customerName"), translator.toTemplate("���g���g"))
            .put(Symbol.of("visitDay"), translator.toTemplate("1��21��"))
            .context();
        SimpleTemplate applied = template.apply(context1).template();
        SimpleTemplate applied2 = applied.apply(context2).template();

        System.out.println("applied = " + applied);
        System.out.println("applied2 = " + applied2);
        String result = translator.fromTemplate(applied2);
        System.out.println("result = " + result);
        assertEquals("����ɂ��́A���g���g�l�B1��21���ɂ͂����X���������A\n" +
        		"�܂��Ƃɂ��肪�Ƃ��������܂��B", result);
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
                "{insert value=header}{define name=title value=\"�w�b�_\"/}{/insert}\n{insert value=raitenbi/}�ɂ͂����X���������A\n�܂��Ƃɂ��肪�Ƃ��������܂��B"))
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
               "{define name=title value=\"�A���P�[�g�t�H�[��\"/}\n" + 
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
               "{define name=title value=\"�A���P�[�g�t�H�[��\"/}\n" + 
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
      
   @Test
   public void testLayout2() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate yamadaStyle = translator.toTemplate(
               "{insert value=message}\n" +
               "{define name=companyName value=\"�R�c����\" /}\n" +
               "{/insert}\n" +
               "---\n" +
               "Copyright(c) �R�c���� All Rights Reserved.\n"
               );
       SimpleTemplate main = translator.toTemplate(
               "{insert value=layout}\n" +
               "{define name=message}\n" +
               "����ɂ��́A{insert value=companyName /}�̃z�[���y�[�W�ɂ悤�����I" +
               "{/define}\n" +
               "{/insert}\n"
               );
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("layout"), yamadaStyle)
           .context();
       String output = 
           translator.fromTemplate(main.apply(context).template());
       System.out.println("output = " + output);
   }
   
   @Test
   public void testScope() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate helloWorld = translator.toTemplate(
               "Hello, {insert value=yourName /}!"
           );
       SimpleTemplate main = translator.toTemplate(
               "{insert value=helloWold}" +
               "{define name=yourName value=inputName /}" +
               "{/insert}"
           );
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("helloWold"), helloWorld)
           .put(Symbol.of("inputName"), translator.toTemplate("���g���g"))
           .context();
       String output = translator.fromTemplate(main.apply(context).template());
       System.out.println("output = " + output);
   }
   @Test
   public void testScopeError() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate helloWorld = translator.toTemplate(
               "Hello, {insert value=yourName /}!"
           );
       SimpleTemplate main = translator.toTemplate(
               "{insert value=helloWold}" +
               "{define name=yourName value=inputName /}" +
               "{/insert}"
           );
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("helloWold"), helloWorld)
           .put(Symbol.of("yourName"), translator.toTemplate("���g���g"))
           .context();
       try {
           String output = translator.fromTemplate(main.apply(context).template());
           System.out.println("output = " + output);
       } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getMessage());
       }
   }
   @Test
   public void testInsertWithTempalte() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate helloWorld = translator.toTemplate(
               "{define name=firstName value=\"�Ԏq\" /}" + 
               "Hello, {insert value=namePart /}!"
           );
       SimpleTemplate main = translator.toTemplate(
               "{insert value=helloWold}" +
               "{define name=namePart}" +
               "{insert value=familyName/} {insert value=firstName/}" +
               "{/define}" + 
               "{/insert}"
           );
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("familyName"), translator.toTemplate("��"))
           .put(Symbol.of("firstName"), translator.toTemplate("�g���g"))
           .put(Symbol.of("helloWold"), helloWorld)
           .context();
       SimpleTemplate applied = main.apply(context).template();
       String output = translator.fromTemplate(applied);
       System.out.println("output = " + output);
   }
   @Test
   public void testNestedScopes() {
       SimpleTranslator translator = new SimpleTranslator();
       SimpleTemplate templateB = translator.toTemplate(
               "{insert value=a1}" +
               "{define name=b1}" +
               "{insert value=a2/}" +
               "{/define}" +
               "{/insert}"
           );
       SimpleTemplate templateA = translator.toTemplate(
               "{insert value=b}" +
               "{define name=a1}" +
               "{insert value=b1}" +
               "{define name=a2}" +
               "{insert value=firstName/}" +
               "{/define}" +
               "{/insert}" +
               "{/define}" +
               "{/insert}"
           );
       Context<Object, SimpleTemplate> context =
           new ContextBuilder<Object, SimpleTemplate>()
           .put(Symbol.of("firstName"), translator.toTemplate("�g���g"))
           .put(Symbol.of("b"), templateB)
           .context();
       SimpleTemplate applied = templateA.apply(context).template();
       String output = translator.fromTemplate(applied);
       System.out.println("output = " + output);
   }
}
