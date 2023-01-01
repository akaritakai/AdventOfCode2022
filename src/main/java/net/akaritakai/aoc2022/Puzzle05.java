package net.akaritakai.aoc2022;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * In Day 5, we are manipulating a number of stacks and querying their final state.
 */
public class Puzzle05 extends AbstractPuzzle {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    public Puzzle05(String puzzleInput) {
        super(puzzleInput);
    }

    private static String stackTops(Stack<Character>[] stacks) {
        return Arrays.stream(stacks)
                .filter(stack -> stack != null && !stack.isEmpty())
                .map(stack -> String.valueOf(stack.peek()))
                .collect(Collectors.joining());
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String solvePart1() {
        var stacks = parseStacks();
        parseInstructions().forEach(instruction -> {
            for (var i = 0; i < instruction.count; i++) {
                var item = stacks[instruction.from].pop();
                stacks[instruction.to].push(item);
            }
        });
        return stackTops(stacks);
    }

    @Override
    public String solvePart2() {
        var stacks = parseStacks();
        parseInstructions().forEach(instruction -> {
            var buffer = new char[instruction.count];
            for (var i = 0; i < instruction.count; i++) {
                buffer[instruction.count - i - 1] = stacks[instruction.from].pop();
            }
            for (var i = 0; i < instruction.count; i++) {
                stacks[instruction.to].push(buffer[i]);
            }
        });
        return stackTops(stacks);
    }

    /**
     * Parses the input, supporting up to 9 stacks.
     */
    private Stack<Character>[] parseStacks() {
        Stack<Character>[] stacks = new Stack[9];
        var blocks = getPuzzleInput().split("\n\n");
        var stackLines = blocks[0].lines().collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(stackLines);
        stackLines.stream().skip(1).forEach(line -> {
            for (var i = 1; i < line.length(); i += 4) {
                char c = line.charAt(i);
                if (c != ' ') {
                    if (stacks[i / 4] == null) {
                        stacks[i / 4] = new Stack<>();
                    }
                    stacks[i / 4].push(c);
                }
            }
        });
        return stacks;
    }

    private List<Instruction> parseInstructions() {
        var blocks = getPuzzleInput().split("\n\n");
        return blocks[1].lines()
                .map(line -> {
                    var matcher = INSTRUCTION_PATTERN.matcher(line);
                    if (!matcher.find()) {
                        throw new IllegalArgumentException("Invalid line: " + line);
                    }
                    var count = Integer.parseInt(matcher.group(1));
                    var from = Integer.parseInt(matcher.group(2)) - 1;
                    var to = Integer.parseInt(matcher.group(3)) - 1;
                    return new Instruction(count, from, to);
                })
                .toList();
    }

    private record Instruction(int count, int from, int to) {
    }
}