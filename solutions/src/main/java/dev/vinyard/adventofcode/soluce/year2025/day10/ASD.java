package dev.vinyard.adventofcode.soluce.year2025.day10;

import com.microsoft.z3.*;
import lombok.Getter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Machine> machines;

        public Root(List<Machine> machines) {
            this.machines = machines;
        }

        public long solution1() {
            return machines.stream().mapToLong(Machine::solve1).sum();
        }

        public Object solution2() {
            return machines.stream().mapToLong(Machine::solve3).peek(System.out::println).sum();
        }
    }

    public static class Machine {
        private final List<ButtonWiringSchematic> buttonWiringSchematics;
        private final JoltageRequirements joltageRequirements;
        private final IndicatorLightDiagram indicateurLightDiagram;

        public Machine(IndicatorLightDiagram indicateurLightDiagram, List<ButtonWiringSchematic> buttonWiringSchematics, JoltageRequirements joltageRequirements) {
            this.indicateurLightDiagram = indicateurLightDiagram;
            this.buttonWiringSchematics = buttonWiringSchematics;
            this.joltageRequirements = joltageRequirements;
        }

        private Graph<Integer, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Integer, DefaultWeightedEdge>undirected()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        public long solve1() {
            Graph<Integer, DefaultWeightedEdge> graph = createEmptyGraph();

            indicateurLightDiagram.getAllSubDiagrams().forEach(graph::addVertex);

            graph.vertexSet().forEach(startVertex -> {
                buttonWiringSchematics.stream().map(ButtonWiringSchematic::getWiringState).forEach(schematic -> {
                    graph.addEdge(startVertex, startVertex ^ schematic);
                });
            });

            ShortestPathAlgorithm<Integer, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

            return (long) dijkstraAlg.getPathWeight(0, indicateurLightDiagram.getState());
        }

        public long solve2() {
            int numButtons = buttonWiringSchematics.size();
            int numCounters = joltageRequirements.joltages.size();

            // Objectif : minimiser la somme des pressions de boutons
            double[] objectiveCoefficients = new double[numButtons];
            Arrays.fill(objectiveCoefficients, 1.0);

            LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(objectiveCoefficients, 0);

            List<LinearConstraint> constraints = new ArrayList<>();

            // Pour chaque compteur, ajouter une contrainte d'égalité
            for (int counter = 0; counter < numCounters; counter++) {
                double[] coefficients = new double[numButtons];

                for (int button = 0; button < numButtons; button++) {
                    ButtonWiringSchematic schematic = buttonWiringSchematics.get(button);
                    // Vérifier si le bouton affecte ce compteur
                    int finalCounter = counter;
                    coefficients[button] = schematic.getButtons().stream()
                            .anyMatch(b -> b.id == finalCounter) ? 1.0 : 0.0;
                }

                // La somme des pressions doit égaler exactement le joltage requis
                constraints.add(new LinearConstraint(coefficients, Relationship.EQ, joltageRequirements.joltages.get(counter)));
            }

            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(
                    objectiveFunction,
                    new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE,
                    new NonNegativeConstraint(true)
            );

            if (solution.getValue() * 100 % 100 != 0)
                System.out.println("Value : " + solution.getValue());
            return (long) Math.ceil(solution.getValue());
        }

        public long solve3() {
            try (Context ctx = new Context()) {
                Optimize opt = ctx.mkOptimize();

                int numCounters = joltageRequirements.joltages.size();

                IntExpr[] buttonPresses = IntStream.range(0, buttonWiringSchematics.size())
                        .mapToObj(i -> ctx.mkIntConst("button_" + i))
                        .peek(expr -> opt.Add(ctx.mkGe(expr, ctx.mkInt(0))))
                        .toArray(IntExpr[]::new);

                for (int counter = 0; counter < numCounters; counter++) {
                    List<ArithExpr> terms = new ArrayList<>();

                    int finalCounter = counter;
                    IntStream.range(0, buttonWiringSchematics.size()).forEach(button -> {
                        ButtonWiringSchematic schematic = buttonWiringSchematics.get(button);
                        if (schematic.getButtons().stream().anyMatch(b -> b.id == finalCounter)) {
                            terms.add(buttonPresses[button]);
                        }
                    });

                    if (!terms.isEmpty()) {
                        ArithExpr sum = ctx.mkAdd(terms.toArray(new ArithExpr[0]));
                        opt.Add(ctx.mkEq(sum, ctx.mkInt(joltageRequirements.joltages.get(counter))));
                    }
                }

                ArithExpr totalPresses = ctx.mkAdd(buttonPresses);
                opt.MkMinimize(totalPresses);

                if (opt.Check() == Status.SATISFIABLE) {
                    Model model = opt.getModel();
                    long result = Long.parseLong(model.eval(totalPresses, false).toString());
                    return result;
                }

                throw new IllegalStateException("No solution found");
            }
        }
    }

    public static class Button {

        private final int id;

        public Button(int id) {
            this.id = id;
        }

        public int getState() {
            return 1 << id;
        }
    }

    public static class ButtonWiringSchematic {

        @Getter
        private final List<Button> buttons;

        @Getter
        private final int wiringState;

        public ButtonWiringSchematic(List<Button> buttons) {
            this.buttons = buttons;
            this.wiringState = buttons.stream().map(Button::getState).reduce(0, (a, b) -> a | b);
        }
    }

    public static class JoltageRequirements {

        private final List<Integer> joltages;

        public JoltageRequirements(List<Integer> joltages) {
            this.joltages = joltages;
        }
    }

    public static class IndicatorLightDiagram {
        private final List<LightState> lightStates;

        public IndicatorLightDiagram(List<LightState> lightStates) {
            this.lightStates = lightStates;
        }

        public Integer getState() {
            return Stream.iterate(0, i -> i + 1).limit(lightStates.size())
                    .filter(i -> Objects.equals(lightStates.get(i), LightState.ON))
                    .reduce(0, (a, b) -> a | (1 << b));
        }

        public List<Integer> getAllSubDiagrams() {
            return Stream.iterate(0, i -> i + 1).limit(1L << lightStates.size()).toList();
        }
    }

    public enum LightState {
        OFF,
        ON
    }

}
