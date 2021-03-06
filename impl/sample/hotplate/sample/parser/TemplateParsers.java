package sample.hotplate.sample.parser;

import static sample.hotplate.sample.parser.ParserFactory.LITERAL_NAME;
import static sample.hotplate.sample.parser.ParserFactory.endBrace;
import static sample.hotplate.sample.parser.ParserFactory.operators;
import static sample.hotplate.sample.parser.ParserFactory.startBrace;

import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map2;
import org.codehaus.jparsec.functors.Map3;

import sample.hotplate.sample.prototype.SimpleContainerPrototype;
import sample.hotplate.sample.prototype.SimpleLiteralPrototype;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;



public final class TemplateParsers {
    private TemplateParsers() {
    }
    public static Parser<SimpleTemplatePrototype> makeLiteralParser() {
        return Terminals.fragment(LITERAL_NAME).map(
            new Map<String, SimpleTemplatePrototype>() {
                public SimpleTemplatePrototype map(String source) {
                     return new SimpleLiteralPrototype(source);
                }
            }
        );
    }
    public static Parser<SimpleTemplatePrototype> makeSingleTagParser(
            Terminals tags, String tagName, final TagHandler handler) {
        return
            startBrace.token("{").next(
                Parsers.sequence(
                    tags.token(tagName), makeAttributeParser().many(), 
                    new Map2<Token, List<Attribute>, SimpleTemplatePrototype>() {
                        public SimpleTemplatePrototype map(Token tagToken, List<Attribute> attributes) {
                            String tagName = tagToken.toString();
                            return handler.handleSingleTag(tagName, attributes);
                        }
                    }
                )
            ).followedBy(operators.token("/").next(endBrace.token("}")));
    }
    public static Parser<SimpleTemplatePrototype> makeContainerTagParser(
            Terminals tags, Parser<List<SimpleTemplatePrototype>> contentParser,
            final String tagName, final TagHandler handler) {

        return Parsers.sequence(
            startTagParser(tags, tagName),
            contentParser,
            endTagParser(tags, tagName),

            new Map3<List<Attribute>, List<SimpleTemplatePrototype>, Void, SimpleTemplatePrototype>() {
                public SimpleTemplatePrototype map(List<Attribute> attributes, final List<SimpleTemplatePrototype> contents, Void d) {
                    return handler.handleContainerTag(tagName, attributes, contents);
                }
            });
    }
    private static Parser<List<Attribute>> startTagParser(Terminals tags, String tagName) {
        return
            startBrace.token("{").next(
                Parsers.sequence(
                    tags.token(tagName), makeAttributeParser().many(), 
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
    private static Parser<Attribute> makeAttributeParser() {
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
    /**
     * @return listParserを元にSimpleTemplatePrototypeを生成するParserを戻す
     * @param listParser List<SimpleTemplatePrototype>を生成するParser
     */
    public static Parser<SimpleTemplatePrototype> flatten(Parser<List<SimpleTemplatePrototype>> listParser) {
        return listParser.map(new Map<List<SimpleTemplatePrototype>, SimpleTemplatePrototype>() {
            public SimpleTemplatePrototype map(List<SimpleTemplatePrototype> elements) {
                return new SimpleContainerPrototype(elements);
            }
        });
    }

}
