package net.akaritakai.aoc2022;

import java.util.regex.Pattern;

/**
 * In Day 4, we are iterating over pairs of ranges and determining how they overlap (fully or partially).
 */
public class Puzzle04 extends AbstractPuzzle {
    private static final Pattern LINE_PATTERN = Pattern.compile("^(\\d+)-(\\d+),(\\d+)-(\\d+)$");
    private final Range[][] rangePairs;

    public Puzzle04(String puzzleInput) {
        super(puzzleInput);

        // Parse out the ranges
        var lines = puzzleInput.split("\n");
        rangePairs = new Range[lines.length][2];
        for (var i = 0; i < lines.length; i++) {
            var matcher = LINE_PATTERN.matcher(lines[i]);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid input: " + lines[i]);
            }
            rangePairs[i][0] = new Range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            rangePairs[i][1] = new Range(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        }
    }

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public String solvePart1() {
        var count = 0;
        for (var rangePair : rangePairs) {
            if (Range.fullyContains(rangePair[0], rangePair[1])) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var count = 0;
        for (var rangePair : rangePairs) {
            if (Range.intersects(rangePair[0], rangePair[1])) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    private record Range(int low, int high) {
        private static boolean fullyContains(Range first, Range second) {
            return (first.low <= second.low && first.high >= second.high)
                    || (second.low <= first.low && second.high >= first.high);
        }

        private static boolean intersects(Range first, Range second) {
            return (first.low <= second.low && first.high >= second.low)
                    || (second.low <= first.low && second.high >= first.low);
        }
    }
}