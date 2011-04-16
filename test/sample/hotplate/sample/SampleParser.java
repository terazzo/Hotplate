package sample.hotplate.sample;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map2;
import org.codehaus.jparsec.functors.Map3;
import org.codehaus.jparsec.pattern.Patterns;
import org.junit.Test;

import sample.hotplate.sample.parser.Attribute;
import sample.hotplate.sample.parser.Expression;
import sample.hotplate.sample.parser.SymbolValue;
import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

public class SampleParser {
    @Test
    public void testParser() {
        String[] tags = new String[] {"insert", "define"};
        String[] opts = new String[] {"{", "}", "=", "/"};


//        Parser<?> tagTokenizer =  Parsers.or(terminals.tokenizer(), identifier, expression);
        Terminals tagNames = Terminals.caseSensitive(new String[0], tags);
        Parser<List<Token>> tagTokenizer = newTokenizer(tagNames);
    
        Parser<String> attributeParser =
            Parsers.or(
                Parsers.sequence(
                    Terminals.Identifier.PARSER,
                    operators.token("="),
                    Terminals.Identifier.PARSER,
                    new Map3<String, Token, String, String>() {
                        public String map(String name, Token op, String expression) {
                            return null;
                        }
                    }
                ),
                Parsers.sequence(
                    Terminals.Identifier.PARSER,
                    operators.token("="),
                    Terminals.StringLiteral.PARSER,
                    new Map3<String, Token, String, String>() {
                        public String map(String name, Token op, String expression) {
                            return null;
                        }
                    }
                )
            );
        Parser<String> singleTagParser = 
            startBrace.token("{").next(
                Parsers.sequence(
                    tagNames.token(tags),
                    attributeParser.many(),
                    new Map2<Token, List<String>, String>() {
                        @Override
                        public String map(Token a, List<String> b) {
                            return null;
                        }
                    }
                )
            ).followedBy(operators.token("/").next(endBrace.token("}")));
        Parser<String> parser = singleTagParser.from(tagTokenizer);
        
        String parsed = parser.parse("{define name=yourname value=\"テラゾー\"/}");
        System.out.println("parserd = " + parsed);
    }
    public static final Terminals startBrace = Terminals.operators("{");
    public static final Terminals endBrace = Terminals.operators("}");
    public static final Terminals operators = Terminals.operators("/", "=");

    /**
     * @param tags タグ名を含むTerminals
     * @return タグ部分のトークナイザを戻す。
     */
    private static Parser<List<Token>> tagTokenizer(Terminals tags) {
        Parser<Object> tagNames = tags.tokenizer();
        Parser<Object> operatorTokenizer = operators.tokenizer();

        Parser<Fragment> identifier = Terminals.Identifier.TOKENIZER;
        Parser<String> expression = Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER;
        Parser<?> ignorable = Scanners.WHITESPACES;

        Parser<Object> tagContent =
            Parsers.or(tagNames, identifier, expression, operatorTokenizer);

        Parser<List<Token>> tagTokenizer =
            Parsers.sequence(
                startBrace.tokenizer().token(),
                repeatWithIgnorables(tagContent.token(), ignorable),
                endBrace.tokenizer().token(),

                new Map3<Token, List<Token>, Token, List<Token>>() {
                    public List<Token> map(Token start, List<Token> tags, Token end) {
                        List<Token> tokens = new ArrayList<Token>(tags.size() + 2);

                        tokens.add(start);
                        tokens.addAll(tags);
                        tokens.add(end);

                        return tokens;
                    }
                });

        return tagTokenizer;
    }
    // 前後・トークン間に空白を含んだ繰り返し用のパーサを生成する。
    private static <T> Parser<List<T>> repeatWithIgnorables(Parser<T> parser, Parser<?> ignorables) {
        return ignorables.optional().next(parser.sepEndBy(ignorables.skipMany()));
    }
    public static final String LITERAL_NAME = "TEXT";
    private static Parser<Fragment> literalTokenizer() {
        Parser<Void> scanner = Scanners.pattern(Patterns.regex("((\\\\\\{)|(\\\\\\})|[^{}])*"), LITERAL_NAME);
        Parser<String> sourceParser = scanner.source();
        Parser<Fragment> tokenizer = sourceParser.map(
            new Map<String, Fragment>() {
                public Fragment map(String text) {
                    String unescaped = text.replaceAll("\\\\\\{", "{").replaceAll("\\\\\\}", "}");
                    return new Fragment(unescaped, LITERAL_NAME);
                }
                @Override public String toString() {
                    return LITERAL_NAME;
                }
            });
        return tokenizer;
    }
    private static final Map<List<List<Token>>, List<Token>> flattenMap =
        new Map<List<List<Token>>, List<Token>>() {
        public List<Token> map(List<List<Token>> items) {
            List<Token> tokens = new ArrayList<Token>();
            for (List<Token> item : items) {
                tokens.addAll(item);
            }
            return tokens;
        }
    };
    public static Parser<List<Token>> newTokenizer(Terminals tags) {
        Parser<List<Token>> tagTokenizer = tagTokenizer(tags);
        Parser<List<Token>> literalTokenizer = literalTokenizer().token().many();

        
        Parser<List<Token>> tokenizer = 
            Parsers.or(tagTokenizer, literalTokenizer).many().map(flattenMap);

        return tokenizer;
    }

}
