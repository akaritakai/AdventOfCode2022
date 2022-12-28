package net.akaritakai.aoc2022;

import com.google.common.annotations.VisibleForTesting;

/**
 * In Day 25, we're summing up base 5 numbers where some digits can be negative.
 */
public class Puzzle25 extends AbstractPuzzle {

    public Puzzle25(String puzzleInput) {
        super(puzzleInput);
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

    @VisibleForTesting
    static String toSnafu(long n) {
        int numDigits = 1;
        while ((Math.pow(5, numDigits) - 1) / 2 < Math.abs(n)) {
            numDigits++;
        }
        var digits = new char[] { '=', '-', '0', '1', '2' };
        var sb = new StringBuilder();
        for (; numDigits > 0; numDigits--) {
            var base = Math.pow(5, numDigits - 1);
            for (int i = 1; i <= 5; i++) {
                if (5 * base + 2 * n + 1 <= 2 * base * i) {
                    sb.append(digits[i - 1]);
                    n += (3 - i) * base;
                    break;
                }
            }
        }
        return sb.toString();
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
                case '-' -> n += -1;
                case '=' -> n += -2;
            }
        }
        return n;
    }
}
