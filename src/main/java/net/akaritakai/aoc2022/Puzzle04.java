package net.akaritakai.aoc2022;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * In Day 4, we are iterating over pairs of ranges and determining how they overlap (fully or partially).
 */
public class Puzzle04 extends AbstractPuzzle {

    private static final Pattern LINE_PATTERN = Pattern.compile("^(\\d+)-(\\d+),(\\d+)-(\\d+)$");

    public Puzzle04(String puzzleInput) {
        super(puzzleInput);
    }

    private static boolean fullyContains(int[] ranges) {
        return (ranges[0] <= ranges[2] && ranges[1] >= ranges[3]) || (ranges[2] <= ranges[0] && ranges[3] >= ranges[1]);
    }

    private static boolean intersects(int[] ranges) {
        return ranges[1] >= ranges[2] && ranges[3] >= ranges[0];
    }

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public String solvePart1() {
        var count = Arrays.stream(parseInput()).filter(Puzzle04::fullyContains).count();
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var count = Arrays.stream(parseInput()).filter(Puzzle04::intersects).count();
        return String.valueOf(count);
    }

    private int[][] parseInput() {
        return getPuzzleInput().lines()
                .map(line -> {
                    var ranges = new int[4];
                    var matcher = LINE_PATTERN.matcher(line);
                    if (matcher.find()) {
                        ranges[0] = Integer.parseInt(matcher.group(1));
                        ranges[1] = Integer.parseInt(matcher.group(2));
                        ranges[2] = Integer.parseInt(matcher.group(3));
                        ranges[3] = Integer.parseInt(matcher.group(4));
                    }
                    return ranges;
                })
                .toArray(int[][]::new);
    }
}