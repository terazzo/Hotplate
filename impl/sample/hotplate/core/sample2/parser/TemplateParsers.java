package sample.hotplate.core.sample2.parser;

import static sample.hotplate.core.sample2.parser.ParserFactory.LITERAL_NAME;
import static sample.hotplate.core.sample2.parser.ParserFactory.endBrace;
import static sample.hotplate.core.sample2.parser.ParserFactory.operators;
import static sample.hotplate.core.sample2.parser.ParserFactory.startBrace;

import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map2;
import org.codehaus.jparsec.functors.Map3;

import sample.hotplate.core.sample2.SimpleLiteral;
import sample.hotplate.core.sample2.SimpleTemplate;



public final class TemplateParsers {
    private TemplateParsers() {
    }
    static Parser<SimpleTemplate> makeLiteralParser() {
        return Terminals.fragment(LITERAL_NAME).map(
            new Map<String, SimpleTemplate>() {
                public SimpleTemplate map(String source) {
                     return new SimpleLiteral(source);
                }
            }
        );
    }
    static Parser<SimpleTemplate> makeSingleTagParser(
            Terminals tags, 
            String tagName, final TagHandler<Object, SimpleTemplate> handler) {
        return
            startBrace.token("{").next(
                Parsers.sequence(
                    tags.token(tagName), attributeParser().many(), 
                    new Map2<Token, List<Attribute>, SimpleTemplate>() {
                        public SimpleTemplate map(Token tagToken, List<Attribute> attributes) {
                            String tagName = tagToken.toString();
                            return handler.handleSingleTag(tagName, attributes);
                        }
                    }
                )
            ).followedBy(operators.token("/").next(endBrace.token("}")));
    }
    public static Parser<SimpleTemplate> makeContainerTagParser(
            Terminals tags, Parser<List<SimpleTemplate>> contentParser,
            final String tagName,
            final TagHandler<Object, SimpleTemplate> handler) {

        return Parsers.sequence(
            startTagParser(tags, tagName),
            contentParser,
            endTagParser(tags, tagName),

            new Map3<List<Attribute>, List<SimpleTemplate>, Void, SimpleTemplate>() {
                public SimpleTemplate map(List<Attribute> attributes, final List<SimpleTemplate> contents, Void d) {
                    return handler.handleContainerTag(tagName, attributes, contents);
                }
            });
    }
    private static Parser<List<Attribute>> startTagParser(Terminals tags, String tagName) {
        return
            startBrace.token("{").next(
                Parsers.sequence(
                    tags.token(tagName), attributeParser().many(), 
                    new Map2<Token, List<Attribute>, List<Attribute>>() {
                        public List<Attribute> map(Token tagToken, List<Attribute> attrs) {
                            return attrs;
                        }
                    }
                )
            ).followedBy(endBrace.token("}"));
    }
    private static Parser<Void> endTagParser(Terminals tags, String tagName) {
        return 
            startBrace.token("{").next(operators.token("/")).next(
                tags.token(tagName).map(new Map<Token, Void>() {
                    public Void map(Token token) {
                        return null;
                    }
                })
            ).followedBy(endBrace.token("}"));
    }
    private static Parser<Attribute> attributeParser() {
        Parser<Attribute> withExpression = 
            Parsers.sequence(
                Terminals.Identifier.PARSER,
                operators.token("="),
                Terminals.StringLiteral.PARSER,

                new Map3<String, Token, String, Attribute>() {
                    public Attribute map(String name, Token op, String expression) {
                        return new Attribute(name, new Expression(expression));
                    }
                });

        Parser<Attribute> withSymbol = 
            Parsers.sequence(
                Terminals.Identifier.PARSER,
                operators.token("="), 
                Terminals.Identifier.PARSER,

                new Map3<String, Token, String, Attribute>() {
                    public Attribute map(String name, Token op, String label) {
                        return new Attribute(name, new SymbolValue(label));
                    }
                });

        return Parsers.or(withExpression, withSymbol);
    }

}
