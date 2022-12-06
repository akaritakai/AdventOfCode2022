package net.akaritakai.aoc2022;

/**
 * In Day 6, we are looking for the first window of size k in a string of size n >= k that contains at least k distinct
 * characters.
 */
public class Puzzle06 extends AbstractPuzzle {
    public Puzzle06(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 6;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(firstUniqueWindow(4));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(firstUniqueWindow(14));
    }

    private int firstUniqueWindow(int windowSize) {
        var input = getPuzzleInput();
        for (var i = 0; i < input.length() - windowSize + 1; i++) {
            var window = input.substring(i, i + windowSize);
            if (window.chars().distinct().count() == windowSize) {
                return i + windowSize;
            }
        }
        throw new IllegalArgumentException("No window of size = " + windowSize + " had unique characters");
    }
}