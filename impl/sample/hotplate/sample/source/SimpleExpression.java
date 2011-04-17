package sample.hotplate.sample.source;

import org.mvel2.MVEL;
import org.mvel2.PropertyAccessException;
import org.mvel2.integration.VariableResolver;
import org.mvel2.integration.impl.BaseVariableResolverFactory;
import org.mvel2.integration.impl.SimpleValueResolver;

import sample.hotplate.core.Associable;
import sample.hotplate.core.Context;
import sample.hotplate.core.Symbol;
import sample.hotplate.sample.SimpleTemplate;
import sample.hotplate.sample.SimpleValue;

public class SimpleExpression implements SimpleSource {

    private String expression;
    public SimpleExpression(String expression) {
        this.expression = expression;
    }
    public boolean isReducible() {
        return true;
    }
    @SuppressWarnings("unchecked")
    @Override
    public Associable<Object, SimpleTemplate> getAssociable(Context<Object, SimpleTemplate> context) {
        try {
            Object value = evaluate(context);
            if (!(value instanceof SimpleTemplate)) {
                value = new SimpleValue(value);
            }
            return (Associable<Object, SimpleTemplate>) value;
        } catch (PropertyAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Object evaluate(Context<Object, SimpleTemplate> context) {
        return MVEL.eval(expression, new VariableResolverFactory(context));
    }

    
    
    public static class VariableResolverFactory extends BaseVariableResolverFactory {
        private static final long serialVersionUID = 1L;
        private Context<Object, SimpleTemplate> context;

        public VariableResolverFactory(Context<Object, SimpleTemplate> context) {
            this.context = context;
        }


        @Override
        public VariableResolver getVariableResolver(String name) {
            Associable<Object, SimpleTemplate> value = context.get(Symbol.of(name));
            if (value != null) {
                return new SimpleValueResolver(value.asValue().value());
            } else {
                return null;
            }
        }
        @Override
        public boolean isTarget(String name) {
            return context.get(Symbol.of(name)) != null;
        }

        @Override
        public boolean isResolveable(String name) {
            return context.get(Symbol.of(name)) != null;
        }

        @Override
        public VariableResolver createVariable(String name, Object value) {
            throw new IllegalStateException();
        }

        @Override
        public VariableResolver createVariable(String name, Object value,
                Class<?> type) {
            throw new IllegalStateException();
        }

        
    }

}
