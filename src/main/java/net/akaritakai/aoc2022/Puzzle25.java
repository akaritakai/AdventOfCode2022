package net.akaritakai.aoc2022;

import com.google.common.annotations.VisibleForTesting;
import com.microsoft.z3.*;

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
        try (var context = new Context()) {
            for (var numDigits = 1; true; numDigits++) {
                var solver = context.mkSolver();

                var digits = new IntExpr[numDigits];
                for (var i = 0; i < numDigits; i++) {
                    digits[i] = context.mkIntConst("digit" + i);
                    solver.add(context.mkAnd(
                            context.mkGe(digits[i], context.mkInt(-2)),
                            context.mkLe(digits[i], context.mkInt(2))));
                }

                ArithExpr<IntSort> total = context.mkInt(0);
                long base = 1;
                for (var i = numDigits - 1; i >= 0; i--) {
                    total = context.mkAdd(total, context.mkMul(digits[i], context.mkInt(base)));
                    base *= 5;
                }
                solver.add(context.mkEq(total, context.mkInt(n)));

                if (solver.check() == Status.SATISFIABLE) {
                    var sb = new StringBuilder();
                    for (int i = 0; i < numDigits; i++) {
                        switch (solver.getModel().eval(digits[i], false).toString()) {
                            case "-2" -> sb.append("=");
                            case "-1" -> sb.append("-");
                            case "0" -> sb.append("0");
                            case "1" -> sb.append("1");
                            case "2" -> sb.append("2");
                        }
                    }
                    return sb.toString();
                }
            }
        }
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
