package sample.hotplate.sample.parser;


import static sample.hotplate.sample.parser.ParserFactory.LITERAL_NAME;
import static sample.hotplate.sample.parser.ParserFactory.endBrace;
import static sample.hotplate.sample.parser.ParserFactory.startBrace;

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
    public static Parser<List<Token>> newTokenizer(Terminals tags) {
        Parser<List<Token>> tagTokenizer = tagTokenizer(tags);
        Parser<List<Token>> literalTokenizer = literalTokenizer().token().many();

        
        Parser<List<Token>> tokenizer = 
            Parsers.or(tagTokenizer, literalTokenizer).many().map(listFlatter);

        return tokenizer;
    }
    /**
     * @param tags タグ名を含むTerminals
     * @return タグ部分のトークナイザを戻す。
     */
    private static Parser<List<Token>> tagTokenizer(Terminals tags) {
        Parser<Object> tagNames = tags.tokenizer();
        Parser<Fragment> identifier = Terminals.Identifier.TOKENIZER;
        Parser<String> expression = Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER;
        Parser<Object> operators = ParserFactory.operators.tokenizer();
        Parser<?> ignorable = Scanners.WHITESPACES;

        Parser<Object> tagContent =
            Parsers.or(tagNames, identifier, expression, operators);

        Parser<List<Token>> tagTokenizer =
            Parsers.sequence(
                startBrace.tokenizer().token(),
                repeatWithIgnorables(tagContent.token(), ignorable),
                endBrace.tokenizer().token(),
                flatter);

        return tagTokenizer;
    }
    /** @return 地のテキスト部分のトークナイザを生成して戻す */
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
    /** List<List<Token>>)をList<Token>にMapする  */
    private static final Map<List<List<Token>>, List<Token>> listFlatter =
        new Map<List<List<Token>>, List<Token>>() {
        public List<Token> map(List<List<Token>> items) {
            List<Token> tokens = new ArrayList<Token>();
            for (List<Token> item : items) {
                tokens.addAll(item);
            }
            return tokens;
        }
    };
    /** (Token, List<Token>, Token)をList<Token>にMapする  */
    private static final Map3<Token, List<Token>, Token, List<Token>> flatter =
        new Map3<Token, List<Token>, Token, List<Token>>() {
        public List<Token> map(Token start, List<Token> tags, Token end) {
            List<Token> tokens = new ArrayList<Token>(tags.size() + 2);

            tokens.add(start);
            tokens.addAll(tags);
            tokens.add(end);

            return tokens;
        }
    };
    /**
     * 前後・トークン間に空白を含んだ繰り返し用のパーサを生成する。
     * @param <T> パーサの出力の型
     * @param parser 繰り返す対象となるパーサ
     * @param ignorables 空白などの無視できる部分のパーサ
     * @return 空白などを含む繰り返し用のパーサ
     */
    private static <T> Parser<List<T>> repeatWithIgnorables(Parser<T> parser, Parser<?> ignorables) {
        return ignorables.optional().next(parser.sepEndBy(ignorables.skipMany()));
    }
}
