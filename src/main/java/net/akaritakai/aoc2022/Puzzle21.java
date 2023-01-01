package net.akaritakai.aoc2022;

import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * In Day 21, we want to solve a system of equations to find the value of a single variable in the system.
 * We do this by parsing the necessary expressions into a syntax tree, reducing them, and then providing the final
 * constraints to Z3.
 */
public class Puzzle21 extends AbstractPuzzle {

    private static final Pattern IMMEDIATE_PATTERN = Pattern.compile("(\\S+): (\\d+)");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("(\\S+): (\\S+) (\\S+) (\\S+)");

    public Puzzle21(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var equations = parseInput();
        Node root = buildAst(equations, equations.get("root"));
        try (var context = new Context()) {
            var solver = context.mkSolver();
            var variables = Map.of("root", context.mkIntConst("root"));
            solver.add(context.mkEq(variables.get("root"), root.toZ3(context, variables)));
            solver.check();
            return solver.getModel().eval(variables.get("root"), false).toString();
        }
    }

    @Override
    public String solvePart2() {
        var equations = parseInput();
        equations.remove("humn");
        Operation root = (Operation) equations.remove("root");
        Node lhs = buildAst(equations, root.left);
        Node rhs = buildAst(equations, root.right);
        try (var context = new Context()) {
            var solver = context.mkSolver();
            var variables = Map.of("humn", context.mkIntConst("humn"));
            solver.add(context.mkEq(lhs.toZ3(context, variables), rhs.toZ3(context, variables)));
            solver.check();
            return solver.getModel().eval(variables.get("humn"), false).toString();
        }
    }

    private Node buildAst(Map<String, Node> equations, Node node) {
        if (node instanceof Immediate) {
            return node;
        } else if (node instanceof Variable variable) {
            return Optional.ofNullable(equations.get(variable.name))
                    .map(next -> buildAst(equations, next))
                    .orElse(node);
        } else if (node instanceof Operation operation) {
            Node left = buildAst(equations, operation.left);
            Node right = buildAst(equations, operation.right);
            if (left instanceof Immediate leftImmediate && right instanceof Immediate rightImmediate) {
                return new Immediate(switch (operation.op()) {
                    case "+" -> leftImmediate.value() + rightImmediate.value();
                    case "-" -> leftImmediate.value() - rightImmediate.value();
                    case "*" -> leftImmediate.value() * rightImmediate.value();
                    case "/" -> leftImmediate.value() / rightImmediate.value();
                    default -> throw new IllegalStateException("Unknown operator: " + operation.op());
                });
            }
            return new Operation(operation.op(), left, right);
        }
        throw new IllegalStateException("Unknown node type: " + node.getClass().getName());
    }

    private Map<String, Node> parseInput() {
        var equations = new HashMap<String, Node>();
        for (var line : getPuzzleInput().split("\n")) {
            var matcher = IMMEDIATE_PATTERN.matcher(line);
            if (matcher.find()) {
                equations.put(matcher.group(1), new Immediate(Long.parseLong(matcher.group(2))));
            }
            matcher = OPERATION_PATTERN.matcher(line);
            if (matcher.find()) {
                var left = new Variable(matcher.group(2));
                var right = new Variable(matcher.group(4));
                equations.put(matcher.group(1), new Operation(matcher.group(3), left, right));
            }
        }
        return equations;
    }

    private interface Node {
        IntExpr toZ3(Context context, Map<String, IntExpr> variables);
    }

    private record Immediate(long value) implements Node {
        @Override
        public IntExpr toZ3(Context context, Map<String, IntExpr> variables) {
            return context.mkInt(value);
        }
    }

    private record Variable(String name) implements Node {
        @Override
        public IntExpr toZ3(Context context, Map<String, IntExpr> variables) {
            return variables.get(name);
        }
    }

    private record Operation(String op, Node left, Node right) implements Node {
        @Override
        public IntExpr toZ3(Context context, Map<String, IntExpr> variables) {
            return switch (op) {
                case "+" -> (IntExpr) context.mkAdd(left.toZ3(context, variables), right.toZ3(context, variables));
                case "-" -> (IntExpr) context.mkSub(left.toZ3(context, variables), right.toZ3(context, variables));
                case "*" -> (IntExpr) context.mkMul(left.toZ3(context, variables), right.toZ3(context, variables));
                case "/" -> (IntExpr) context.mkDiv(left.toZ3(context, variables), right.toZ3(context, variables));
                default -> throw new IllegalStateException("Unknown operator: " + op);
            };
        }
    }
}