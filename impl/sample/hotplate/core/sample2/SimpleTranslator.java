package sample.hotplate.core.sample2;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.TokenMap;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map2;
import org.codehaus.jparsec.functors.Map3;
import org.codehaus.jparsec.functors.Map4;
import org.codehaus.jparsec.pattern.Patterns;
import org.codehaus.jparsec.util.Objects;
import org.codehaus.jparsec.util.Strings;
import org.junit.Test;

import sample.hotplate.core.Translator;


public class SimpleTranslator implements Translator<String, Object, SimpleTemplate> {
	@Override
	public SimpleContainer toTemplate(String rawObject) {
		List<SimpleTemplate> elements = parse(rawObject);
		SimpleContainer simpleTemplate = new SimpleContainer(elements);
		return simpleTemplate;
	}
	@Override
	public String fromTemplate(SimpleTemplate template) {
		return template.getString();
	}
	abstract static  class IsFragment implements TokenMap<String> {
	    
	    public String map(final Token token) {
	      final Object val = token.value();
	      if (val instanceof Fragment) {
	        Fragment c = (Fragment) val;
	        if (!isExpectedTag(c.tag())) return null;
	        return c.text();
	      }
	      else return null;
	    }
	    
	    /** Is {@code type} expected? */
	    abstract boolean isExpectedTag(Object type);
	  }
	static TokenMap<String> fromFragment(final Object... tags) {
	      return new IsFragment() {
	        @Override boolean isExpectedTag(Object type) {
	          return Objects.in(type, tags);
	        }
	        @Override public String toString() {
	          if (tags.length == 0) return "";
	          if (tags.length == 1) return String.valueOf(tags[0]);
	          return "[" + Strings.join(", ", tags) + "]";
	        }
	      };
	    }
	   	@Test
	public void testParser() {
//	   	    Scanners.DOUBLE_QUOTE_STRING.atomic()
	    Terminals braces = Terminals.operators("{","}");
        Parser<Fragment> literalTokenizer = 
            Scanners.pattern(Patterns.regex("((\\\\\\{)|(\\\\\\})|[^{}])*") ,"TEXT").source()
        .map(new Map<String,Fragment>() {
            public Fragment map(String text) {
                return new Fragment(text, "TEXT");
              }
              @Override public String toString() {
                return "TEXT";
              }
            });;
        Parser<String> literalParser = Parsers.token(fromFragment("TEXT"));;
	    Parser<?> tokenizer = Parsers.or(braces.tokenizer(), literalTokenizer);

	    Parser<String> placeHolder =  literalParser.between(braces.token("{"), braces.token("}"));
	    Parser<?> templates = Parsers.or(literalParser, placeHolder).many();
	    
	    Parser templateParser = templates.from(tokenizer.token().many());
	    
	    Object result = templateParser.parse("\"a\\{aa\"{ hog\\}e }\"zzz\"");
	    System.out.println("result = " + result);
	}
   	Parser<List<SimpleTemplate>> templateParser = createParser();
    private List<SimpleTemplate> parse(String rawObject) {
        return templateParser.parse(rawObject);
    }
    private Parser<List<SimpleTemplate>> createParser() {
        return null;
    }
  	
   	
    
    @Test
    public void testParse() {
try {
        Parser<Fragment> literalTokenizer = 
            Scanners.pattern(Patterns.regex("((\\\\\\{)|(\\\\\\})|[^{}])*") ,"TEXT").source()
        .map(new Map<String,Fragment>() {
            public Fragment map(String text) {
                return new Fragment(text, "TEXT");
              }
              @Override public String toString() {
                return "TEXT";
              }
            });;
        Parser<String> literalParser = Parsers.token(fromFragment("TEXT"));;
        Parser<?> ignored = Scanners.WHITESPACES;


        Terminals braces = Terminals.operators("{","}");
        Terminals operators = Terminals.operators("/", "=");
        Parser<Fragment> identifierTokenizer = Terminals.Identifier.TOKENIZER;
        Parser<?> identifierParser = Terminals.Identifier.PARSER;
        Parser<List<Token>> tagTokenizer =
            Parsers.sequence(
                braces.tokenizer().token(),
                ignored.optional().next(
                    Parsers.or(identifierTokenizer, Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER, operators.tokenizer()).token().
                    sepEndBy(ignored.skipMany())),
                braces.tokenizer().token(),
                new Map3<Token, List<Token>, Token, List<Token>>() {
                    public List<Token> map(Token a, List<Token> b, Token d) {
                        List<Token> results = new ArrayList<Token>(b.size() + 2);
                        results.add(a);
                        results.addAll(b);
                        results.add(d);
                        return results;
                    }
                });
            
        Parser<List<Token>> tokenizerToken = 
            Parsers.or(
                    tagTokenizer,
                    literalTokenizer.token().many()).
            many().map(new Map<List<List<Token>>, List<Token>>() {
                public List<Token> map(List<List<Token>> items) {
                    List<Token> results = new ArrayList<Token>();
                    for (List<Token> item : items)
                    results.addAll(item);
                    return results;
                }
            });
            
        Parser<String> attribute = Parsers.sequence(Terminals.Identifier.PARSER, operators.token("="), 
                    Parsers.or(Terminals.StringLiteral.PARSER,Terminals.Identifier.PARSER),
                new Map3<String, Token, String, String>() {
                    public String map(String a, Token b, String d) {
                        System.out.printf("key = %s, value = %s\n", a, d);
                        return a + "=" + d;
                    }
                });
        Parser<String> singleTagParser = Parsers.sequence(
                braces.token("{"), identifierParser, attribute.many(), braces.token("}"),
                new Map4<Token, Object, List<String>,Token, String>() {
                    @Override
                    public String map(Token a, Object b, List<String> attrs, Token d) {
                        System.out.println("attrs = " + attrs);
                        return b.toString();
                    }
                });
        Parser<List<String>> parser = Parsers.or(singleTagParser, literalParser).many();
        Parser<List<String>> templateParser = parser.from(tokenizerToken);
//      Parser<List<String>> templateParser = parser.from(tokenizer.lexer(delim));
//      Parser<List<String>> templateParser = parser.from(delim.optional().next(tokenizer.token().sepEndBy(delim));
//      Parser<List<String>> templateParser = parser.from(tokenizer.token().many());
        
        String source = "aa\\{\"a{insert hoge=\"fuga\"}bbb{insert name=aaa}";
        Object parsed = templateParser.parse(source);
        System.out.println("parsed:" + parsed);
} catch (Exception e) {
    e.printStackTrace();
    fail(e.getMessage());
}
    }

}