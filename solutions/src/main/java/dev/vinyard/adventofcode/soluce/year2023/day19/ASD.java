package dev.vinyard.adventofcode.soluce.year2023.day19;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASD {

    public static class Root {

        private final List<Rating> ratings;

        public Root(List<Rating> ratings, Map<String, ASD.Expression<Long>> registry) {
            this.ratings = ratings;

            registry.put("A", new Expression<>() {
                @Override
                public Long evaluate(Ranges ranges) {
                    return ranges.sum();
                }

                @Override
                public Long evaluate(Variables variables) {
                    return variables.sum();
                }
            });

            registry.put("R", new Expression<>() {

                @Override
                public Long evaluate(Ranges ranges) {
                    return 0L;
                }

                @Override
                public Long evaluate(Variables variables) {
                    return 0L;
                }
            });
        }

        public long evaluateAll() {
            return ratings.stream().mapToLong(c -> c.evaluate(new Variables())).sum();
        }

        public long getAllCombinations() {
            return ratings.getFirst().evaluate(new Ranges());
        }
    }

    @NoArgsConstructor
    public static class Ranges {
        private final Map<String, Range> ranges = new HashMap<>();

        public Ranges(Map<String, Range> ranges) {
            this.ranges.putAll(ranges);
        }

        public Range get(String name) {
            return ranges.get(name);
        }

        public Ranges from(String name, Range range) {
            Ranges r = new Ranges(ranges);
            r.set(name, range);
            return r;
        }

        public void set(String name, Range range) {
            ranges.put(name, range);
        }

        public long sum() {
            return ranges.values().stream().mapToLong(Range::getLength).reduce(1, (l1, l2) -> l1 * l2);
        }
    }

    public record Range(long start, long end) {
        public static Range of(long start, long end) {
            return new Range(start, end);
        }

        public long getLength() {
            return Math.max(0, end - start + 1);
        }
    }

    public interface Expression<T> {
        T evaluate(Ranges ranges);
        T evaluate(Variables variables);
    }

    public static class Variables {
        private final Map<String, Long> variables = new HashMap<>();

        public Long get(String name) {
            return variables.get(name);
        }

        public void set(String name, Long value) {
            variables.put(name, value);
        }

        public long sum() {
            return variables.values().stream().mapToLong(v -> v).sum();
        }
    }

    public static class Statement implements Expression<Long> {
        private final String variable;
        private final Map<String, Expression<Long>> statements;

        public Statement(String variable, Map<String, Expression<Long>> statements) {
            this.variable = variable;
            this.statements = statements;
        }

        @Override
        public Long evaluate(Ranges ranges) {
            return this.statements.get(variable).evaluate(ranges);
        }

        @Override
        public Long evaluate(Variables variables) {
            return this.statements.get(variable).evaluate(variables);
        }
    }

    public static class Constant implements Expression<Long> {
        private final Long value;

        public Constant(Long value) {
            this.value = value;
        }

        @Override
        public Long evaluate(Ranges ranges) {
            return value;
        }

        @Override
        public Long evaluate(Variables variables) {
            return value;
        }
    }

    public static class Rating implements Expression<Long> {
        private final List<Expression<Void>> args;
        private final Map<String, Expression<Long>> functions;
        private final String functionName;

        public Rating(String functionName, List<Expression<Void>> args, Map<String, Expression<Long>> functions) {
            this.args = args;
            this.functions = functions;
            this.functionName = functionName;
        }

        @Override
        public Long evaluate(Ranges ranges) {
            for (Expression<Void> arg : args) {
                arg.evaluate(ranges);
            }

            return functions.get(functionName).evaluate(ranges);
        }

        @Override
        public Long evaluate(Variables variables) {
            for (Expression<Void> arg : args) {
                arg.evaluate(variables);
            }

            return functions.get(functionName).evaluate(variables);
        }
    }

    public static class Assignment implements Expression<Void> {
        private final String variable;
        private final Expression<Long> expression;

        public Assignment(String variable, Expression<Long> expression) {
            this.variable = variable;
            this.expression = expression;
        }

        @Override
        public Void evaluate(Ranges ranges) {
            ranges.set(variable, new Range(1, 4000));
            return null;
        }

        @Override
        public Void evaluate(Variables variables) {
            variables.set(variable, expression.evaluate(variables));
            return null;
        }
    }

    public static class Rule implements Expression<Long> {
        private final Condition condition;
        private final Expression<Long> thenExpression;
        private final Expression<Long> elseExpression;

        public Rule(Condition condition, Expression<Long> thenExpression, Expression<Long> elseExpression) {
            this.condition = condition;
            this.thenExpression = thenExpression;
            this.elseExpression = elseExpression;
        }

        @Override
        public Long evaluate(Ranges ranges) {
            return thenExpression.evaluate(condition.getThenStmts(ranges)) + elseExpression.evaluate(condition.getElseStmts(ranges));
        }

        @Override
        public Long evaluate(Variables variables) {
            if (condition.evaluate(variables)) {
                return thenExpression.evaluate(variables);
            } else {
                return elseExpression.evaluate(variables);
            }
        }
    }

    public abstract static class Condition implements Expression<Boolean> {

        public abstract Ranges getThenStmts(Ranges variables);

        public abstract Ranges getElseStmts(Ranges variables);
    }

    public static class GreaterThan extends Condition {

        private final String variable;
        private final int value;

        public GreaterThan(String variable, int value) {
            this.variable = variable;
            this.value = value;
        }

        @Override
        public Boolean evaluate(Ranges ranges) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public Boolean evaluate(Variables variables) {
            return variables.get(variable) > value;
        }

        @Override
        public Ranges getThenStmts(Ranges ranges) {
            return ranges.from(variable, new Range(value + 1, ranges.get(variable).end));
        }

        @Override
        public Ranges getElseStmts(Ranges ranges) {
            return ranges.from(variable, new Range(ranges.get(variable).start, value));
        }
    }

    public static class LessThan extends Condition {
        private final String variable;
        private final int value;

        public LessThan(String variable, int value) {
            this.variable = variable;
            this.value = value;
        }

        @Override
        public Boolean evaluate(Ranges ranges) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public Boolean evaluate(Variables variables) {
            return variables.get(variable) < value;
        }

        @Override
        public Ranges getThenStmts(Ranges variables) {
            return variables.from(variable, new Range(variables.get(variable).start, value - 1));
        }

        @Override
        public Ranges getElseStmts(Ranges variables) {
            return variables.from(variable, new Range(value, variables.get(variable).end));
        }
    }
}
