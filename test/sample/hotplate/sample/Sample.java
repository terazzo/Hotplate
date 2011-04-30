package sample.hotplate.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.util.ContextBuilder;

public class Sample {
    private SimpleTranslator translator = new SimpleTranslator();
    private SimpleTemplate loadTemplate(String resourceName) throws IOException {
        String contentString = IOUtils.toString(getClass().getResourceAsStream(resourceName));
        return translator.toTemplate(contentString);
    }
    @Test
    public void testLayout() throws IOException {
        SimpleTemplate layout = loadTemplate("templates/layout.html");
        Context<Object, SimpleTemplate> globalSettings =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("copyrightOwner"), translator.toTemplate("terazzo"))
            .put(Symbol.of("copyrightYear"), translator.toTemplate("2011"))
            .context();
        layout = layout.apply(globalSettings).template();
        Context<Object, SimpleTemplate> context =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("layout"), layout)
            .put(Symbol.of("actionUrl"), new SimpleValue("http://example.com/post.cgi"))
            .context();

        SimpleTemplate page = loadTemplate("templates/page.html");
        String output = translator.fromTemplate(page.apply(context).template());
        System.out.println(output);
    }
    public static enum BloodType {
        A (1, "A�^"),
        B (2, "B�^"),
        O (3, "O�^"),
        AB (4, "AB�^");
        
        /** ID�l */
        private int value;
        /** ���x���� */
        private String label;

        BloodType(int value, String label) {
            this.value = value;
            this.label = label;
        }
        public int getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }
    }
    public static class Branch {
        protected int id;
        protected String name;
        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getRegisteredDate() {
            return registeredDate;
        }
        protected String registeredDate;
    }
    List<Branch> branches = new ArrayList<Branch>(){{
        add(new Branch(){{id=1;name="�{��";registeredDate="2001-1-1";}});
        add(new Branch(){{id=2;name="��ʎx�X";registeredDate="2004-3-10";}});
        add(new Branch(){{id=3;name="�_�ސ�x�X";registeredDate="2005-4-2";}});
        add(new Branch(){{id=4;name="��t�x�X";registeredDate="2005-7-15";}});
        add(new Branch(){{id=5;name="���x�X";registeredDate="2007-1-5";}});
        add(new Branch(){{id=6;name="���k�x�X";registeredDate="2010-5-8";}});
        add(new Branch(){{id=7;name="�É��x�X";registeredDate="2010-9-1";}});
        add(new Branch(){{id=8;name="����x�X";registeredDate="2010-11-15";}});
        add(new Branch(){{id=9;name="�R���x�X";registeredDate="2011-2-25";}});
        add(new Branch(){{id=10;name="���É��x�X";registeredDate="2011-4-2";}});
        add(new Branch(){{id=11;name="�V���x�X";registeredDate="2011-4-2";}});
    }};
    @Test
    public void testList() throws IOException {
        SimpleTemplate listPage = loadTemplate("templates/list.html");
        Context<Object, SimpleTemplate> context =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("branches"), new SimpleValue(branches))
            .context();
        String output = translator.fromTemplate(listPage.apply(context).template());
        System.out.println(output);
        
    }
    @Test
    public void testPager() throws IOException {
        SimpleTemplate pager = loadTemplate("templates/pager.html");
        SimpleTemplate listWithPager = loadTemplate("templates/listWithPager.html");
        Context<Object, SimpleTemplate> context =
            new ContextBuilder<Object, SimpleTemplate>()
            .put(Symbol.of("pager"), pager)
            .put(Symbol.of("branches"), new SimpleValue(branches))
            .put(Symbol.of("page"), new SimpleValue(0))
            .context();
        String output = translator.fromTemplate(listWithPager.apply(context).template());
        System.out.println(output);
    }
}
