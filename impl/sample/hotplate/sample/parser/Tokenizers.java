package sample.hotplate.sample.parser;


import static sample.hotplate.sample.parser.ParserFactory.*;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map3;
import org.codehaus.jparsec.pattern.Patterns;

public final class Tokenizers {
    private Tokenizers() {
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
    private static Parser<List<Token>> tagTokenizer(Terminals tags) {
        Parser<Object> tagNames = tags.tokenizer();
        Parser<Fragment> identifier = Terminals.Identifier.TOKENIZER;
        Parser<String> expression = Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER;
        Parser<Object> operators = ParserFactory.operators.tokenizer();

        Parser<Object> tagContent =
            Parsers.or(tagNames, identifier, expression, operators);

        Parser<?> ignorable = Scanners.WHITESPACES;
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
    private static <T> Parser<List<T>> repeatWithIgnorables(Parser<T> parser, Parser<?> ignorables) {
        return ignorables.optional().next(parser.sepEndBy(ignorables.skipMany()));
    }
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
    
}
