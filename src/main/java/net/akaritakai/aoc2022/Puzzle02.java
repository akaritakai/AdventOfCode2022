package net.akaritakai.aoc2022;

/**
 * In Day 2, we are parsing a list of Rock Paper Scissor games and adding up the score according to the tournament
 * rules.
 */
public class Puzzle02 extends AbstractPuzzle {
    public Puzzle02(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var score = getPuzzleInput().lines()
                .mapToInt(line -> {
                    var opponent = line.charAt(0) - 'A';
                    var me = line.charAt(2) - 'X';
                    if (me == opponent) {
                        return me + 4; // Draw
                    } else if ((me + 1) % 3 == opponent) {
                        return me + 1; // Loss
                    } else {
                        return me + 7; // Win
                    }
                })
                .sum();
        return String.valueOf(score);
    }

    @Override
    public String solvePart2() {
        var score = getPuzzleInput().lines()
                .mapToInt(line -> {
                    var opponent = line.charAt(0) - 'A';
                    var outcome = line.charAt(2) - 'X';
                    return switch (outcome) {
                        case 0 -> (opponent + 2) % 3 + 1; // Loss
                        case 1 -> opponent + 4; // Draw
                        case 2 -> (opponent + 1) % 3 + 7; // Win
                        default -> throw new IllegalStateException("Invalid outcome: " + outcome);
                    };
                })
                .sum();
        return String.valueOf(score);
    }
}
