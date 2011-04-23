package sample.hotplate.sample.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parser.Reference;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;

import sample.hotplate.sample.parser.handler.DefineTagHandler;
import sample.hotplate.sample.parser.handler.InsertTagHandler;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;


public final class ParserFactory {
    public static final Terminals startBrace = Terminals.operators("{");
    public static final Terminals endBrace = Terminals.operators("}");
    public static final Terminals operators = Terminals.operators("/", "=");
    public static final String LITERAL_NAME = "TEXT";

    public static final ParserFactory instance = new ParserFactory();
    
    /** タグ名をキー、TagHandlerを値とするMap */
    private Map<String, TagHandler> handlers = new LinkedHashMap<String, TagHandler>();
    
    public ParserFactory() {
        registerHandlers();
    }

    private void registerHandlers() {
        registerHandler(new DefineTagHandler());
        registerHandler(new InsertTagHandler());
    }
    public synchronized void registerHandler(TagHandler handler) {
        for (String tagName : handler.tagNames()) {
            handlers.put(tagName, handler);            
        }
    }

    public synchronized Parser<SimpleTemplatePrototype> newParser() {
        // Tag用のTerminalsを作成
        Terminals tags = makeTags(this.handlers);
        // Tokenizer(字句解析部)を作成
        Parser<List<Token>> tokenizer = Tokenizers.newTokenizer(tags);
        // Parser(構文解析部)を作成
        Parser<SimpleTemplatePrototype> parser = makeParser(handlers, tags);
        // 両方を合わせてパーサを作成
        return parser.from(tokenizer);
    }

    private Terminals makeTags(Map<String, TagHandler> handlers) {
        String[] processorNames = handlers.keySet().toArray(new String[0]);
        return Terminals.caseSensitive(new String[0], processorNames);
    }
    
    private Parser<SimpleTemplatePrototype> makeParser(Map<String, TagHandler> handlers, Terminals tags) {
        Reference<List<SimpleTemplatePrototype>> contentParserRef = Parser.newReference();

        List<Parser<SimpleTemplatePrototype>> parsers = makeTagParses(handlers, tags, contentParserRef);
        parsers.add(TemplateParsers.makeLiteralParser());

        Parser<List<SimpleTemplatePrototype>> listParser = Parsers.or(parsers).many();
        contentParserRef.set(listParser);

        return TemplateParsers.flatten(listParser);
    }

    private List<Parser<SimpleTemplatePrototype>> makeTagParses(
            Map<String, TagHandler> handlers,
            Terminals tags, Reference<List<SimpleTemplatePrototype>> listParserRef) {
        List<Parser<SimpleTemplatePrototype>> tagParsers = new ArrayList<Parser<SimpleTemplatePrototype>>();

        for (String tagName: handlers.keySet()) {
            TagHandler handler = handlers.get(tagName);
            if (handler.requireContainerTag(tagName)) {
                tagParsers.add(
                    TemplateParsers.makeContainerTagParser(tags, listParserRef.lazy(), tagName, handler));
            }
            if (handler.requireSingleTag(tagName)) {
                tagParsers.add(
                    TemplateParsers.makeSingleTagParser(tags, tagName, handler));
            }
        }
        return tagParsers;
    }
}
