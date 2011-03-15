package sample.hotplate.core.sample2;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.TokenMap;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map3;
import org.codehaus.jparsec.functors.Map4;
import org.codehaus.jparsec.functors.Map5;
import org.codehaus.jparsec.pattern.Patterns;
import org.codehaus.jparsec.util.Objects;
import org.codehaus.jparsec.util.Strings;
import org.junit.Test;

import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.core.Translator;
import sample.hotplate.core.sample2.processors.SimpleDefineProcessor;
import sample.hotplate.core.sample2.processors.SimpleInsertProcessor;
import sample.hotplate.core.sample2.processors.SimpleProcessorPrototype;


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

   	Parser<List<SimpleTemplate>> templateParser = createParser();
    private List<SimpleTemplate> parse(String rawObject) {
        return templateParser.parse(rawObject);
    }
  	
   	
    private static final String[] processorNames = new String[] {"insert", "define"};
    private static final Terminals processors = Terminals.caseSensitive(new String[0], processorNames);
    private static final Terminals braces = Terminals.operators("{","}");
    private static final Terminals operators = Terminals.operators("/", "=");

    @Test
    private Parser<List<SimpleTemplate>> createParser() {
try {
        Parser<SimpleTemplate> literalParser =
            Parsers.token(fromFragment("TEXT")).map(new Map<String, SimpleTemplate>() {
                public SimpleTemplate map(String source) {
                     return new SimpleLiteral(source);
                }
            });

        Parser<?> identifierParser = Terminals.Identifier.PARSER;
            
        Parser.Reference<List<SimpleTemplate>> contentRef = Parser.newReference();
        Parser<SimpleTemplate> defineSingleTagParser = makeSingleTagParser("define", 
            new Map<List<Attribute>, SimpleTemplate>() {
                public SimpleTemplate map(List<Attribute> attributes) {
                    Attribute nameAttribute = findAttribute("name", attributes);
                    Attribute valueAttribute = findAttribute("value", attributes);
                    final SimpleTemplate value;
                    if (valueAttribute.getValue().isSymbol()) {
                        final Symbol symbol = valueAttribute.getValue().getSymbol();
                        value = new SimpleProcessorPrototype() {
                            protected SimpleTemplate create(
                                    Context<Object, SimpleTemplate> lexicalContext) {
                                 return new SimpleInsertProcessor(lexicalContext,
                                         symbol, Collections.<SimpleTemplate>emptyList());
                            }
                            public String toString() {
                                return String.format("{*insert value=%s/}", symbol);
                            }
                        };
                    } else {
                        final String expression = valueAttribute.getValue().getExpression();
                        value = new SimpleLiteral(expression);
                    }
                    final Symbol symbol = nameAttribute.getValue().getSymbol();
                    return new SimpleProcessorPrototype() {
                        protected SimpleTemplate create(
                                Context<Object, SimpleTemplate> lexicalContext) {
                             return new SimpleDefineProcessor(lexicalContext, symbol, value);
                        }
                        public String toString() {
                            return String.format("{*define name=%s value=%s/}", symbol, value);
                        }
                    };
                }
            });
        Parser<List<Attribute>> insertStartTagParser = makeStartTagParser("insert", 
                new Map<List<Attribute>, List<Attribute> >() {
                    public List<Attribute> map(List<Attribute> attrs) {
                        return attrs;
                    }
                });
        Parser<Void> insertEndTagParser = makeEndTagParser("insert");
        Parser<SimpleTemplate> insertTag = Parsers.sequence(
                insertStartTagParser,
                contentRef.lazy(),
                insertEndTagParser,
                new Map3<List<Attribute>, List<SimpleTemplate>, Void, SimpleTemplate>() {
                    @Override
                    public SimpleTemplate map(List<Attribute> attributes, final List<SimpleTemplate> contents, Void d) {
                        System.out.println("contents = " + contents);;
                        Attribute valueAttribute = findAttribute("value", attributes);
                        final Symbol symbol = valueAttribute.getValue().getSymbol();
                        return new SimpleProcessorPrototype() {
                            protected SimpleTemplate create(
                                    Context<Object, SimpleTemplate> lexicalContext) {
                                 return new SimpleInsertProcessor(lexicalContext,
                                         symbol, contents);
                            }
                            public String toString() {
                                return String.format("{*insert value=%s}%s{/*insert}", symbol, contents);
                            }
                        };
                    }
                });
        
        Parser<List<SimpleTemplate>> parser = Parsers.or(defineSingleTagParser, insertTag,  literalParser).many();
        contentRef.set(parser);
        Parser<List<SimpleTemplate>> templateParser = parser.from(prepareTokenizer());
        return templateParser;
////      Parser<List<String>> templateParser = parser.from(tokenizer.lexer(delim));
////      Parser<List<String>> templateParser = parser.from(delim.optional().next(tokenizer.token().sepEndBy(delim));
////      Parser<List<String>> templateParser = parser.from(tokenizer.token().many());
//        
////        String source = "aa\\{\"a{insert hoge=\"fuga\"/}bbb{insert name=aaa/}";
//        String source2 = "{insert value=value}\n{define name=\"value\" value==\"fuga\"/}\n{/insert}";
//        String source = "{insert value=value}\nasd{define name=\"value\" value=\"fuga\"/}\n{/insert}";
//        Object parsed = templateParser.parse(source);
//        System.out.println("parsed:" + parsed);
} catch (Exception e) {
    e.printStackTrace();
    return null;
}
    }
    private <T> Parser<T> makeSingleTagParser(
            String tagName, final Map<List<Attribute>, T> map) {
        return Parsers.sequence(
            braces.token("{"), 
                processors.token(tagName), 
                attributeParser().many(), 
            operators.token("/"), braces.token("}"),

            new Map5<Token, Object, List<Attribute>,Token, Token, T>() {
                public T map(Token a, Object b, List<Attribute> attrs, Token d, Token e) {
                    return map.map(attrs);
                }
            });
    }
    private <T> Parser<T> makeStartTagParser(
            String tagName, final Map<List<Attribute>, T> map) {
        return Parsers.sequence(
            braces.token("{"), 
                processors.token(tagName), 
                attributeParser().many(), 
            braces.token("}"),

            new Map4<Token, Object, List<Attribute>, Token, T>() {
                public T map(Token a, Object b, List<Attribute> attrs, Token e) {
                    return map.map(attrs);
                }
            });
    }
    private Parser<Void> makeEndTagParser(String tagName) {
        return Parsers.sequence(
            braces.token("{"), operators.token("/"),
                processors.token(tagName), 
            braces.token("}")).map(new Map<Token, Void>() {
                @Override
                public Void map(Token token) {
                    return null;
                }
            });
    }

    private Parser<Attribute> attributeParser() {
        Parser<Attribute> attributeWithExpression = 
            Parsers.sequence(Terminals.Identifier.PARSER,
                    operators.token("="), 
                    Terminals.StringLiteral.PARSER,
                new Map3<String, Token, String, Attribute>() {
                    public Attribute map(String name, Token op, String expression) {
                        return new Attribute(name, new Expression(expression));
                    }
                });
        Parser<Attribute> attributeWithSymbol = 
            Parsers.sequence(Terminals.Identifier.PARSER,
                    operators.token("="), 
                    Terminals.Identifier.PARSER,
                new Map3<String, Token, String, Attribute>() {
                    public Attribute map(String name, Token op, String label) {
                        return new Attribute(name, new SymbolValue(label));
                    }
                });
        Parser<Attribute> attribute = Parsers.or(attributeWithExpression,attributeWithSymbol);
        return attribute;
    }

    private <T> Parser<List<T>> repeatWithIgnorables(Parser<T> parser, Parser<?> ignorables) {
        return ignorables.optional().next(parser.sepEndBy(ignorables.skipMany()));
    }
    private Parser<List<Token>> prepareTokenizer() {
        Parser<Fragment> textTokenizer = 
            Scanners.pattern(Patterns.regex("((\\\\\\{)|(\\\\\\})|[^{}])*") ,"TEXT")
            .source().map(
                new Map<String,Fragment>() {
                    public Fragment map(String text) {
                        return new Fragment(text, "TEXT");
                    }
                    @Override public String toString() {
                        return "TEXT";
                    }
                });;
        Parser<?> ignorable = Scanners.WHITESPACES;
        Parser<Fragment> identifier = Terminals.Identifier.TOKENIZER;
        Parser<String> quoted = Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER;
        Parser<?> tagContent =
            Parsers.or(processors.tokenizer(), identifier, quoted, operators.tokenizer());
        Parser<List<Token>> tagTokenizer =
            Parsers.sequence(
                braces.tokenizer().token(),
                repeatWithIgnorables(tagContent.token(), ignorable),
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
            Parsers.or(tagTokenizer, textTokenizer.token().many())
            .many().map(new Map<List<List<Token>>, List<Token>>() {
                public List<Token> map(List<List<Token>> items) {
                    List<Token> results = new ArrayList<Token>();
                    for (List<Token> item : items)
                    results.addAll(item);
                    return results;
                }
            });
        return tokenizerToken;
    }
    
    private static interface Value {
        boolean isSymbol();
        String getExpression();
        Symbol getSymbol();
    }
    private static class Expression implements Value {
        private final String expression;
        Expression(String expression) {
            this.expression = expression;
        }
        public boolean isSymbol() {
            return false;
        }
        public String getExpression() {
            return expression;
        }
        public Symbol getSymbol() {
            throw new IllegalStateException("Expression has not Symbol");
        }
    }
    private static class SymbolValue implements Value {
        private final Symbol symbol;
        SymbolValue(String label) {
            this.symbol = Symbol.of(label);
        }
        @Override
        public boolean isSymbol() {
            return true;
        }
        public String getExpression() {
            throw new IllegalStateException("SymbolValue has not Expression");
        }
        @Override
        public Symbol getSymbol() {
            return symbol;
        }
    }
    private static class Attribute {
        private final String name;
        private final Value value;
        Attribute(String name, Value value) {
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public Value getValue() {
            return value;
        }
    }


    private static Attribute findAttribute(String name, List<Attribute> attributes) {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(name)) {
                return attribute;
            }
        }
        return null;
    }
    private static <T> List<T> selectProcessor(Class<T> clazz, List<SimpleTemplate> contents) {
        List<T> results = new ArrayList<T>();
        for (SimpleTemplate item : contents) {
            if (clazz.isInstance(item)) {
                results.add(clazz.cast(item));
            }
        }
        return results;
    }


}