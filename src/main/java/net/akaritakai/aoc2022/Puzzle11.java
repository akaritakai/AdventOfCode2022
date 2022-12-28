package net.akaritakai.aoc2022;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * In Day 11, we are provided with a set of arithmetic operations to do. The operations are chosen depending on the
 * current modulo value. Using modular arithmetic allows us to avoid integer overflows.
 */
public class Puzzle11 extends AbstractPuzzle {

    public Puzzle11(String puzzleInput) {
        super(puzzleInput);
    }

    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    private static long gcd(long a, long b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }

    @Override
    public int getDay() {
        return 11;
    }

    @Override
    public String solvePart1() {
        var monkeys = parseInput(null);
        for (var i = 0; i < 20; i++) {
            for (var monkey : monkeys) {
                monkey.doRound();
            }
        }
        Arrays.sort(monkeys, (a, b) -> Long.compare(b.inspections, a.inspections));
        return String.valueOf(monkeys[0].inspections * monkeys[1].inspections);
    }

    @Override
    public String solvePart2() {
        var monkeys = parseInput(lcm());
        for (var i = 0; i < 10_000; i++) {
            for (var monkey : monkeys) {
                monkey.doRound();
            }
        }
        Arrays.sort(monkeys, (a, b) -> Long.compare(b.inspections, a.inspections));
        return String.valueOf(monkeys[0].inspections * monkeys[1].inspections);
    }

    private Monkey[] parseInput(Long lcm) {
        var blocks = getPuzzleInput().split("\n\n");
        var monkeys = new Monkey[blocks.length];
        for (var i = 0; i < blocks.length; i++) {
            monkeys[i] = new Monkey(monkeys, blocks[i], lcm);
        }
        return monkeys;
    }

    private long lcm() {
        var lcm = 1L;
        var matcher = Pattern.compile("divisible by (\\d+)").matcher(getPuzzleInput());
        while (matcher.find()) {
            lcm = lcm(lcm, Long.parseLong(matcher.group(1)));
        }
        return lcm;
    }

    private static final class Monkey {
        private static final Pattern PATTERN = Pattern.compile("""
                Monkey \\d+:
                \\s+Starting items: (.+)
                \\s+Operation: new = old ([+*]) (\\S+)
                \\s+Test: divisible by (\\S+)
                \\s+If true: throw to monkey (\\S+)
                \\s+If false: throw to monkey (\\S+)""");

        private final LinkedList<Long> items = new LinkedList<>();
        private final Consumer<Long> function;
        private long inspections = 0;

        private Monkey(Monkey[] monkeys, String block, Long lcm) {
            var matcher = PATTERN.matcher(block);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid monkey block: " + block);
            }
            for (var item : matcher.group(1).split(", ")) {
                items.add(Long.parseLong(item));
            }
            var operation = matcher.group(2).charAt(0);

            UnaryOperator<Long> operand = switch (matcher.group(3)) {
                case "old" -> i -> i;
                default -> i -> Long.parseLong(matcher.group(3));
            };
            var divisor = Long.parseLong(matcher.group(4));
            var trueMonkey = Integer.parseInt(matcher.group(5));
            var falseMonkey = Integer.parseInt(matcher.group(6));
            function = i -> {
                switch (operation) {
                    case '+' -> i += operand.apply(i);
                    case '*' -> i *= operand.apply(i);
                }
                if (lcm != null) {
                    i %= lcm;
                } else {
                    i /= 3;
                }
                if (i % divisor == 0) {
                    monkeys[trueMonkey].items.addLast(i);
                } else {
                    monkeys[falseMonkey].items.addLast(i);
                }
            };
        }

        private void doRound() {
            while (!items.isEmpty()) {
                function.accept(items.removeFirst());
                inspections++;
            }
        }
    }
}