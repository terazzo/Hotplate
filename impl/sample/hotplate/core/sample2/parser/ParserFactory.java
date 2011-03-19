package sample.hotplate.core.sample2.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parser.Reference;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.junit.Test;

import sample.hotplate.core.sample2.SimpleTemplate;
import sample.hotplate.core.sample2.parser.handler.DefineTagHandler;
import sample.hotplate.core.sample2.parser.handler.InsertTagHandler;


public final class ParserFactory {
    public static final Terminals startBrace = Terminals.operators("{");
    public static final Terminals endBrace = Terminals.operators("}");
    public static final Terminals operators = Terminals.operators("/", "=");
    public static final String LITERAL_NAME = "TEXT";
    public static final Parser<SimpleTemplate>
        literalParser = TemplateParsers.makeLiteralParser();

    public static final ParserFactory instance = new ParserFactory();
    
    private java.util.Map<String, TagHandler<Object, SimpleTemplate>>
        handlers = new LinkedHashMap<String, TagHandler<Object,SimpleTemplate>>();
    
    
    public ParserFactory() {
        registerHandlers();
    }

    private void registerHandlers() {
        registerHandler(new DefineTagHandler());
        registerHandler(new InsertTagHandler());
    }
    public synchronized void registerHandler(TagHandler<Object, SimpleTemplate> handler) {
        for (String tagName : handler.tagNames()) {
            handlers.put(tagName, handler);            
        }
    }

    @Test
    public Parser<List<SimpleTemplate>> newParser() {
        String[] processorNames = handlers.keySet().toArray(new String[0]);
        Terminals tags = Terminals.caseSensitive(new String[0], processorNames);
            
        Reference<List<SimpleTemplate>> parserRef = Parser.newReference();
        
        List<Parser<SimpleTemplate>> parsers = new ArrayList<Parser<SimpleTemplate>>();
        parsers.addAll(makeTagParses(tags, parserRef));
        parsers.add(literalParser);
        
        Parser<List<SimpleTemplate>> parser = Parsers.or(parsers).many();
        parserRef.set(parser);

        return parser.from(Tokenizers.newTokenizer(tags));
    }

    private List<Parser<SimpleTemplate>> makeTagParses(
            Terminals tags, Reference<List<SimpleTemplate>> parserRef) {

        List<Parser<SimpleTemplate>> tagParsers = new ArrayList<Parser<SimpleTemplate>>();
        for (String tagName: handlers.keySet()) {
            TagHandler<Object,SimpleTemplate> handler = handlers.get(tagName);
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
