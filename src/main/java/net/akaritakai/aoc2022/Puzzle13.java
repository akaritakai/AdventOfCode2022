package net.akaritakai.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In Day 13, we're parsing and comparing recursive structures.
 */
public class Puzzle13 extends AbstractPuzzle {

    private static Comparator<Node> NODE_COMPARATOR = null;

    static {
        NODE_COMPARATOR = (first, second) -> {
            if (first instanceof ValueNode left && second instanceof ValueNode right) {
                return Integer.compare(left.element, right.element);
            } else if (first instanceof ArrayNode left && second instanceof ArrayNode right) {
                for (int i = 0; i < Math.min(left.elements.size(), right.elements.size()); i++) {
                    var result = NODE_COMPARATOR.compare(left.elements.get(i), right.elements.get(i));
                    if (result != 0) {
                        return result;
                    }
                }
                return Integer.compare(left.elements.size(), right.elements.size());
            } else if (first instanceof ValueNode left && second instanceof ArrayNode right) {
                return NODE_COMPARATOR.compare(new ArrayNode(List.of(left)), right);
            } else if (first instanceof ArrayNode left && second instanceof ValueNode right) {
                return NODE_COMPARATOR.compare(left, new ArrayNode(List.of(right)));
            }
            throw new IllegalArgumentException("Invalid nodes: " + first + ", " + second);
        };
    }

    public Puzzle13(String puzzleInput) {
        super(puzzleInput);
    }

    private static Node parsePacket(String packet) {
        var stack = new Stack<Node>();
        var matcher = Pattern.compile("\\[|]|\\d+").matcher(packet);
        while (matcher.find()) {
            switch (matcher.group()) {
                case "[" -> {
                    var node = new ArrayNode();
                    if (!stack.isEmpty()) {
                        var parent = stack.peek();
                        if (parent instanceof ArrayNode arr) {
                            arr.elements.add(node);
                        }
                    }
                    stack.push(node);
                }
                case "]" -> {
                    if (stack.size() > 1) {
                        stack.pop();
                    }
                }
                default -> {
                    var value = Integer.parseInt(matcher.group());
                    if (stack.isEmpty()) {
                        stack.push(new ValueNode(value));
                    } else {
                        var parent = stack.peek();
                        if (parent instanceof ArrayNode arr) {
                            arr.elements.add(new ValueNode(value));
                        }
                    }
                }
            }
        }
        return stack.pop();
    }

    @Override
    public String solvePart1() {
        var sum = 0;
        var pairs = parseInput();
        for (var i = 0; i < pairs.size(); i++) {
            var pair = pairs.get(i);
            if (NODE_COMPARATOR.compare(pair.getLeft(), pair.getRight()) <= 0) {
                sum += i + 1;
            }
        }
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var packets = parseInput().stream()
                .flatMap(pair -> Stream.of(pair.getLeft(), pair.getRight()))
                .collect(Collectors.toCollection(ArrayList::new));
        var divider1 = parsePacket("[[2]]");
        var divider2 = parsePacket("[[6]]");
        packets.add(divider1);
        packets.add(divider2);
        packets.sort(NODE_COMPARATOR);
        var key = 1;
        for (var i = 0; i < packets.size(); i++) {
            if (packets.get(i) == divider1 || packets.get(i) == divider2) {
                key *= i + 1;
            }
        }
        return String.valueOf(key);
    }

    private List<Pair<Node, Node>> parseInput() {
        return Arrays.stream(getPuzzleInput().split("\n\n"))
                .map(block -> {
                    var lines = block.split("\n");
                    var packet1 = parsePacket(lines[0]);
                    var packet2 = parsePacket(lines[1]);
                    return Pair.of(packet1, packet2);
                })
                .toList();
    }

    private interface Node {
    }

    private record ArrayNode(List<Node> elements) implements Node {
        private ArrayNode() {
            this(new ArrayList<>());
        }
    }

    private record ValueNode(int element) implements Node {
    }
}