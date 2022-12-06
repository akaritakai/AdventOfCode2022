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
    public int getDay() {
        return 2;
    }

    @Override
    public String solvePart1() {
        var score = 0;
        for (var lines : getPuzzleInput().split("\n")) {
            var opponent = lines.charAt(0) - 'A';
            var me = lines.charAt(2) - 'X';
            if (me == opponent) score += me + 4; // Draw
            else if ((me + 2) % 3 == opponent) score += me + 7; // Win
            else score += me + 1; // Loss
        }
        return String.valueOf(score);
    }

    @Override
    public String solvePart2() {
        var score = 0;
        for (var lines : getPuzzleInput().split("\n")) {
            var opponent = lines.charAt(0) - 'A';
            var outcome = lines.charAt(2) - 'X';
            switch (outcome) {
                case 0 -> score += (opponent + 2) % 3 + 1; // Lose
                case 1 -> score += opponent + 4; // Draw
                case 2 -> score += (opponent + 1) % 3 + 7; // Win
            }
        }
        return String.valueOf(score);
    }
}