package sample.hotplate.sample.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parser.Reference;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.junit.Test;

import sample.hotplate.sample.parser.handler.DefineTagHandler;
import sample.hotplate.sample.parser.handler.ForeachTagHandler;
import sample.hotplate.sample.parser.handler.IfTagHandler;
import sample.hotplate.sample.parser.handler.InsertTagHandler;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;


public final class ParserFactory {
    public static final Terminals startBrace = Terminals.operators("{");
    public static final Terminals endBrace = Terminals.operators("}");
    public static final Terminals operators = Terminals.operators("/", "=");
    public static final String LITERAL_NAME = "TEXT";
    public static final Parser<SimpleTemplatePrototype>
        literalParser = TemplateParsers.makeLiteralParser();

    public static final ParserFactory instance = new ParserFactory();
    
    private java.util.Map<String, TagHandler>
        handlers = new LinkedHashMap<String, TagHandler>();
    
    
    public ParserFactory() {
        registerHandlers();
    }

    private void registerHandlers() {
        registerHandler(new DefineTagHandler());
        registerHandler(new InsertTagHandler());
        registerHandler(new IfTagHandler());
        registerHandler(new ForeachTagHandler());
    }
    public synchronized void registerHandler(TagHandler handler) {
        for (String tagName : handler.tagNames()) {
            handlers.put(tagName, handler);            
        }
    }

    @Test
    public Parser<List<SimpleTemplatePrototype>> newParser() {
        String[] processorNames = handlers.keySet().toArray(new String[0]);
        Terminals tags = Terminals.caseSensitive(new String[0], processorNames);
            
        Reference<List<SimpleTemplatePrototype>> parserRef = Parser.newReference();
        
        List<Parser<SimpleTemplatePrototype>> parsers = new ArrayList<Parser<SimpleTemplatePrototype>>();
        parsers.addAll(makeTagParses(tags, parserRef));
        parsers.add(literalParser);
        
        Parser<List<SimpleTemplatePrototype>> parser = Parsers.or(parsers).many();
        parserRef.set(parser);

        return parser.from(Tokenizers.newTokenizer(tags));
    }

    private List<Parser<SimpleTemplatePrototype>> makeTagParses(
            Terminals tags, Reference<List<SimpleTemplatePrototype>> parserRef) {

        List<Parser<SimpleTemplatePrototype>> tagParsers = new ArrayList<Parser<SimpleTemplatePrototype>>();
        for (String tagName: handlers.keySet()) {
            TagHandler handler = handlers.get(tagName);
            if (handler.requireContainerTag()) {
                tagParsers.add(
                    TemplateParsers.makeContainerTagParser(tags, parserRef.lazy(), tagName, handler));
            }
            if (handler.requireSingleTag()) {
                tagParsers.add(
                    TemplateParsers.makeSingleTagParser(tags, tagName, handler));
            }
        }
        return tagParsers;
    }
    
}
