package dev.vinyard.adventofcode.soluce.year2023.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASD {

    public static class Root {

        private List<Call> calls;
        private Map<String, ASD.Expression<Long>> functions;

        public Root(List<Call> calls, Map<String, ASD.Expression<Long>> functions) {
            this.calls = calls;
            this.functions = functions;
        }

        public long evaluateAll() {
            functions.put("A", (h) -> h.values().stream().mapToLong(l -> l).sum());
            functions.put("R", (h) -> 0L);

            return calls.stream().mapToLong(c -> c.evaluate(new HashMap<>())).sum();
        }

    }

    @FunctionalInterface
    public interface Expression<T> {
        T evaluate(Map<String, Integer> variables);
    }

    public static class Statement implements Expression<Long> {
        private String variable;
        private Map<String, Expression<Long>> statements;

        public Statement(String variable, Map<String, Expression<Long>> statements) {
            this.variable = variable;
            this.statements = statements;
        }

        public Long evaluate(Map<String, Integer> variables) {
            return this.statements.get(variable).evaluate(variables);
        }
    }

    public static class Constant implements Expression<Long> {
        private Long value;

        public Constant(Long value) {
            this.value = value;
        }

        @Override
        public Long evaluate(Map<String, Integer> variables) {
            return value;
        }
    }

    public static class Call implements Expression<Long> {
        private List<Expression<Void>> args;
        private Map<String, Expression<Long>> functions;
        private String functionName;

        public Call(String functionName, List<Expression<Void>> args, Map<String, Expression<Long>> functions) {
            this.args = args;
            this.functions = functions;
            this.functionName = functionName;
        }

        @Override
        public Long evaluate(Map<String, Integer> variables) {
            for (Expression<Void> arg : args) {
                arg.evaluate(variables);
            }

            return functions.get(functionName).evaluate(variables);
        }
    }

    public static class Assignment implements Expression<Void> {
        private String variable;
        private Expression<Long> expression;

        public Assignment(String variable, Expression<Long> expression) {
            this.variable = variable;
            this.expression = expression;
        }

        @Override
        public Void evaluate(Map<String, Integer> variables) {
            Long value = expression.evaluate(variables);
            variables.put(variable, value.intValue());
            return null;
        }
    }

    public static class IfStatement implements Expression<Long> {
        private Expression<Boolean> condition;
        private Expression<Long> thenExpression;
        private Expression<Long> elseExpression;

        public IfStatement(Expression<Boolean> condition, Expression<Long> thenExpression, Expression<Long> elseExpression) {
            this.condition = condition;
            this.thenExpression = thenExpression;
            this.elseExpression = elseExpression;
        }


        @Override
        public Long evaluate(Map<String, Integer> variables) {
            if (condition.evaluate(variables)) {
                return thenExpression.evaluate(variables);
            } else {
                return elseExpression.evaluate(variables);
            }
        }
    }

    public static class GreaterThan implements Expression<Boolean> {

        private final String variable;
        private final int value;

        public GreaterThan(String variable, int value) {
            this.variable = variable;
            this.value = value;
        }

        public Boolean evaluate(Map<String, Integer> variables) {
            return variables.get(variable) > value;
        }
    }

    public static class LessThan implements Expression<Boolean> {
        private final String variable;
        private final int value;

        public LessThan(String variable, int value) {
            this.variable = variable;
            this.value = value;
        }

        public Boolean evaluate(Map<String, Integer> variables) {
            return variables.get(variable) < value;
        }
    }

}
