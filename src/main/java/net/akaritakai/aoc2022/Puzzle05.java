package net.akaritakai.aoc2022;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * In Day 5, we are manipulating a number of stacks.
 */
public class Puzzle05 extends AbstractPuzzle {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");
    private final String[] instructions;

    public Puzzle05(String puzzleInput) {
        super(puzzleInput);
        instructions = puzzleInput.split("\n\n")[1].split("\n");
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String solvePart1() {
        var stacks = parseStacks();
        for (var instruction : instructions) {
            var matcher = INSTRUCTION_PATTERN.matcher(instruction);
            if (matcher.find()) {
                var count = Integer.parseInt(matcher.group(1));
                var from = Integer.parseInt(matcher.group(2)) - 1;
                var to = Integer.parseInt(matcher.group(3)) - 1;
                for (var i = 0; i < count; i++) {
                    stacks[to].push(stacks[from].pop());
                }
            }
        }
        return stackTops(stacks);
    }

    @Override
    public String solvePart2() {
        var stacks = parseStacks();
        for (var instruction : instructions) {
            var matcher = INSTRUCTION_PATTERN.matcher(instruction);
            if (matcher.find()) {
                var count = Integer.parseInt(matcher.group(1));
                var from = Integer.parseInt(matcher.group(2)) - 1;
                var to = Integer.parseInt(matcher.group(3)) - 1;
                var buffer = new Character[count];
                for (var i = 0; i < count; i++) {
                    buffer[count - i - 1] = stacks[from].pop();
                }
                for (var i = 0; i < count; i++) {
                    stacks[to].push(buffer[i]);
                }
            }
        }
        return stackTops(stacks);
    }

    private static String stackTops(Stack<Character>[] stacks) {
        StringBuilder sb = new StringBuilder();
        for (var stack : stacks) {
            if (stack != null && !stack.isEmpty()) {
                sb.append(stack.peek());
            }
        }
        return sb.toString();
    }

    private Stack<Character>[] parseStacks() {
        @SuppressWarnings("unchecked") Stack<Character>[] stacks = new Stack[9];
        var lines = getPuzzleInput().split("\n\n")[0].split("\n");
        for (var i = lines.length - 2; i >= 0; i--) {
            var line = lines[i];
            for (var j = 1; j < line.length(); j += 4) {
                char c = line.charAt(j);
                if (c != ' ') {
                    if (stacks[j / 4] == null) {
                        stacks[j / 4] = new Stack<>();
                    }
                    stacks[j / 4].push(c);
                }
            }
        }
        return stacks;
    }
}