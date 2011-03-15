package sample.hotplate.core.sample;

import sample.hotplate.core.Context;
import sample.hotplate.core.impl.TemplatePairImpl;
import sample.hotplate.core.sample.SimpleTemplate;
import sample.hotplate.core.sample.SimpleTemplatePair;

public class SimpleTemplatePair extends TemplatePairImpl<Object, SimpleTemplate>{
    public SimpleTemplatePair(SimpleTemplate template, Context<Object, SimpleTemplate> context) {
        super(template, context);
    }
    public static SimpleTemplatePair of(SimpleTemplate template, Context<Object, SimpleTemplate> context) {
        return new SimpleTemplatePair(template, context);
    }
}