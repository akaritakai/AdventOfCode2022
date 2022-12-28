package net.akaritakai.aoc2022;

import com.google.common.annotations.VisibleForTesting;

/**
 * In Day 25, we're summing up base 5 numbers where some digits can be negative.
 */
public class Puzzle25 extends AbstractPuzzle {

    public Puzzle25(String puzzleInput) {
        super(puzzleInput);
    }

    @VisibleForTesting
    static String toSnafu(long n) {
        var digits = new char[]{'=', '-', '0', '1', '2'};
        var sb = new StringBuilder();
        do {
            sb.append(digits[(int) ((n + 2) % 5)]);
            n = (n + 2) / 5;
        } while (n != 0);
        return sb.reverse().toString();
    }

    @VisibleForTesting
    static long fromSnafu(String snafu) {
        long n = 0;
        for (var c : snafu.toCharArray()) {
            n *= 5;
            switch (c) {
                case '2' -> n += 2;
                case '1' -> n += 1;
                case '0' -> n += 0;
                case '-' -> n -= 1;
                case '=' -> n -= 2;
            }
        }
        return n;
    }

    @Override
    public int getDay() {
        return 25;
    }

    @Override
    public String solvePart1() {
        long sum = getPuzzleInput().lines().mapToLong(Puzzle25::fromSnafu).sum();
        return toSnafu(sum);
    }

    @Override
    public String solvePart2() {
        return "Day 25 has no part 2";
    }
}
