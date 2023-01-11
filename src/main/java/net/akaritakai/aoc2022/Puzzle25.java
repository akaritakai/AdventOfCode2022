package net.akaritakai.aoc2022;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.Chars;

/**
 * In Day 25, we're summing up base 5 numbers where some digits can be negative.
 */
public class Puzzle25 extends AbstractPuzzle {
    private static final char[] DIGITS = {'=', '-', '0', '1', '2'};

    public Puzzle25(String puzzleInput) {
        super(puzzleInput);
    }

    @VisibleForTesting
    static String toSnafu(long n) {
        var sb = new StringBuilder();
        do {
            sb.append(DIGITS[(int) ((n + 2) % 5)]);
            n = (n + 2) / 5;
        } while (n != 0);
        return sb.reverse().toString();
    }

    @VisibleForTesting
    static long fromSnafu(String snafu) {
        return snafu.chars()
                .mapToLong(c -> Chars.indexOf(DIGITS, (char) c))
                .reduce(0, (a, b) -> 5 * a + b - 2);
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
