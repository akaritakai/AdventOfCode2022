package net.akaritakai.aoc2022;

import com.google.common.primitives.Longs;

import java.util.Arrays;
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
    public String solvePart1() {
        var monkeys = parseInput();
        for (var i = 0; i < 20; i++) {
            for (var monkey : monkeys) {
                monkey.doRound(monkeys, null);
            }
        }
        Arrays.sort(monkeys, (a, b) -> Long.compare(b.inspections, a.inspections));
        return String.valueOf(monkeys[0].inspections * monkeys[1].inspections);
    }

    @Override
    public String solvePart2() {
        var monkeys = parseInput();
        // Calculate the LCM
        var lcm = 1L;
        var matcher = Pattern.compile("divisible by (\\d+)").matcher(getPuzzleInput());
        while (matcher.find()) {
            lcm = lcm(lcm, Long.parseLong(matcher.group(1)));
        }
        for (var i = 0; i < 10_000; i++) {
            for (var monkey : monkeys) {
                monkey.doRound(monkeys, lcm);
            }
        }
        Arrays.sort(monkeys, (a, b) -> Long.compare(b.inspections, a.inspections));
        return String.valueOf(monkeys[0].inspections * monkeys[1].inspections);
    }

    private Monkey[] parseInput() {
        var blocks = getPuzzleInput().split("\n\n");
        var monkeys = new Monkey[blocks.length];
        for (var i = 0; i < blocks.length; i++) {
            monkeys[i] = new Monkey(blocks[i]);
        }
        return monkeys;
    }

    private static final class Monkey {
        private static final Pattern PATTERN = Pattern.compile("""
                Monkey \\d+:
                \\s+Starting items: (.+)
                \\s+Operation: new = old ([+*]) (\\S+)
                \\s+Test: divisible by (\\S+)
                \\s+If true: throw to monkey (\\S+)
                \\s+If false: throw to monkey (\\S+)""");

        private final ItemList items = new ItemList();
        private final char operation;
        private final Long operand;
        private final long divisor;
        private final int trueMonkey;
        private final int falseMonkey;
        private long inspections = 0;

        private Monkey(String block) {
            var matcher = PATTERN.matcher(block);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid monkey block: " + block);
            }
            for (var item : matcher.group(1).split(", ")) {
                items.addLast(Long.parseLong(item));
            }
            this.operation = matcher.group(2).charAt(0);
            this.operand = Longs.tryParse(matcher.group(3));
            this.divisor = Long.parseLong(matcher.group(4));
            this.trueMonkey = Integer.parseInt(matcher.group(5));
            this.falseMonkey = Integer.parseInt(matcher.group(6));
        }

        private void doRound(Monkey[] monkeys, Long lcm) {
            while (!items.isEmpty()) {
                var item = items.removeFirst();
                var operand = item;
                if (this.operand != null) {
                    operand = this.operand;
                }
                switch (operation) {
                    case '+' -> item += operand;
                    case '*' -> item *= operand;
                }
                if (lcm == null) {
                    item /= 3;
                } else {
                    item %= lcm;
                }
                if (item % divisor == 0) {
                    monkeys[trueMonkey].items.addLast(item);
                } else {
                    monkeys[falseMonkey].items.addLast(item);
                }
                inspections++;
            }
        }
    }

    /**
     * An item list that can hold up to 100 items.
     */
    private static final class ItemList {
        private static final int CAPACITY = 100;
        private final long[] items = new long[CAPACITY];
        private int size = 0;
        private int head = 0;
        private int tail = 0;

        private boolean isEmpty() {
            return size == 0;
        }

        private void addLast(long item) {
            if (size == CAPACITY) {
                throw new IllegalStateException("Item list is full");
            }
            items[tail % CAPACITY] = item;
            tail++;
            size++;
        }

        private long removeFirst() {
            if (size == 0) {
                throw new IllegalStateException("Item list is empty");
            }
            var item = items[head % CAPACITY];
            head++;
            size--;
            return item;
        }
    }
}