package net.akaritakai.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * In Day 5, we are manipulating a number of stacks and querying their final state.
 */
public class Puzzle05 extends AbstractPuzzle {

    public Puzzle05(String puzzleInput) {
        super(puzzleInput);
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

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String solvePart1() {
        var input = new Input();
        var stacks = input.stacks;
        for (var instruction : input.instructions) {
            var count = instruction.count;
            var from = instruction.from;
            var to = instruction.to;
            for (var i = 0; i < count; i++) {
                var item = stacks[from].pop();
                stacks[to].push(item);
            }
        }
        return stackTops(stacks);
    }

    @Override
    public String solvePart2() {
        var input = new Input();
        var stacks = input.stacks;
        for (var instruction : input.instructions) {
            var count = instruction.count;
            var from = instruction.from;
            var to = instruction.to;
            var buffer = new char[count];
            for (var i = 0; i < count; i++) {
                buffer[count - i - 1] = stacks[from].pop();
            }
            for (var i = 0; i < count; i++) {
                stacks[to].push(buffer[i]);
            }
        }
        return stackTops(stacks);
    }

    private record Instruction(int count, int from, int to) {
    }

    /**
     * Parses the input, supporting up to 9 stacks.
     */
    private final class Input {
        private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

        @SuppressWarnings("unchecked")
        private final Stack<Character>[] stacks = new Stack[9];
        private final List<Instruction> instructions = new ArrayList<>();

        private Input() {
            var scanner = new Scanner(getPuzzleInput());
            parseStack(scanner);
            parseInstructions(scanner);
        }

        private void parseStack(Scanner scanner) {
            Stack<String> buffer = new Stack<>();
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine();
                if (line.startsWith(" 1")) {
                    break; // End of stack data
                }
                buffer.push(line);

            }
            while (!buffer.isEmpty()) {
                var line = buffer.pop();
                for (var i = 1; i < line.length(); i += 4) {
                    char c = line.charAt(i);
                    if (c != ' ') {
                        if (stacks[i / 4] == null) {
                            stacks[i / 4] = new Stack<>();
                        }
                        stacks[i / 4].push(c);
                    }
                }
            }
        }

        private void parseInstructions(Scanner scanner) {
            while (scanner.hasNextLine()) {
                var matcher = INSTRUCTION_PATTERN.matcher(scanner.nextLine());
                if (matcher.find()) {
                    var count = Integer.parseInt(matcher.group(1));
                    var from = Integer.parseInt(matcher.group(2)) - 1;
                    var to = Integer.parseInt(matcher.group(3)) - 1;
                    instructions.add(new Instruction(count, from, to));
                }
            }
        }
    }
}