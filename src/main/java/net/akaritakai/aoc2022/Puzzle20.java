package net.akaritakai.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * In Day 20, we're moving elements around in a circular list. This requires only some index bookkeeping and modular
 * arithmetic.
 */
public class Puzzle20 extends AbstractPuzzle {

    public Puzzle20(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var nodes = new Nodes(1);
        nodes.mix();
        return String.valueOf(nodes.decrypt());
    }

    @Override
    public String solvePart2() {
        var nodes = new Nodes(811589153);
        for (var i = 0; i < 10; i++) {
            nodes.mix();
        }
        return String.valueOf(nodes.decrypt());
    }

    private record Node(int index, long value) {
    }

    private final class Nodes {
        private final List<Long> values;
        private final List<Node> nodes = new ArrayList<>();

        private Nodes(long key) {
            values = getPuzzleInput().lines().map(line -> key * Long.parseLong(line)).toList();
            IntStream.range(0, values.size()).forEach(i -> nodes.add(new Node(i, values.get(i))));
        }

        private void mix() {
            for (var i = 0; i < values.size(); i++) {
                var node = new Node(i, values.get(i));
                long index = nodes.indexOf(node);
                nodes.remove((int) index);
                index = (index + node.value) % (values.size() - 1);
                if (index < 0) {
                    index += values.size() - 1;
                }
                nodes.add((int) index, node);
            }
        }

        private long decrypt() {
            var zero = new Node(values.indexOf(0L), 0);
            var index = nodes.indexOf(zero);
            return LongStream.of(1000, 2000, 3000)
                    .map(i -> nodes.get((int) ((index + i) % values.size())).value)
                    .sum();
        }
    }
}