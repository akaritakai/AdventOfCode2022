package net.akaritakai.aoc2022;

public class Main {
    private static final PuzzleInputFetcher PUZZLE_INPUT_FETCHER = new PuzzleInputFetcher();
    private static final AbstractPuzzle[] PUZZLES = new AbstractPuzzle[]{
            new Puzzle01(PUZZLE_INPUT_FETCHER.getPuzzleInput(1)),
            new Puzzle02(PUZZLE_INPUT_FETCHER.getPuzzleInput(2)),
            new Puzzle03(PUZZLE_INPUT_FETCHER.getPuzzleInput(3)),
            new Puzzle04(PUZZLE_INPUT_FETCHER.getPuzzleInput(4)),
            new Puzzle05(PUZZLE_INPUT_FETCHER.getPuzzleInput(5)),
            new Puzzle06(PUZZLE_INPUT_FETCHER.getPuzzleInput(6)),
            new Puzzle07(PUZZLE_INPUT_FETCHER.getPuzzleInput(7)),
            new Puzzle08(PUZZLE_INPUT_FETCHER.getPuzzleInput(8)),
            new Puzzle09(PUZZLE_INPUT_FETCHER.getPuzzleInput(9)),
            new Puzzle10(PUZZLE_INPUT_FETCHER.getPuzzleInput(10)),
            new Puzzle11(PUZZLE_INPUT_FETCHER.getPuzzleInput(11)),
            new Puzzle12(PUZZLE_INPUT_FETCHER.getPuzzleInput(12)),
            new Puzzle13(PUZZLE_INPUT_FETCHER.getPuzzleInput(13)),
            new Puzzle14(PUZZLE_INPUT_FETCHER.getPuzzleInput(14)),
            new Puzzle15(PUZZLE_INPUT_FETCHER.getPuzzleInput(15)),
            new Puzzle16(PUZZLE_INPUT_FETCHER.getPuzzleInput(16)),
            new Puzzle17(PUZZLE_INPUT_FETCHER.getPuzzleInput(17)),
            new Puzzle18(PUZZLE_INPUT_FETCHER.getPuzzleInput(18)),
            new Puzzle19(PUZZLE_INPUT_FETCHER.getPuzzleInput(19)),
            new Puzzle20(PUZZLE_INPUT_FETCHER.getPuzzleInput(20)),
            new Puzzle21(PUZZLE_INPUT_FETCHER.getPuzzleInput(21)),
            new Puzzle22(PUZZLE_INPUT_FETCHER.getPuzzleInput(22)),
            new Puzzle23(PUZZLE_INPUT_FETCHER.getPuzzleInput(23)),
            new Puzzle24(PUZZLE_INPUT_FETCHER.getPuzzleInput(24)),
            new Puzzle25(PUZZLE_INPUT_FETCHER.getPuzzleInput(25))
    };

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            var day = String.format("%02d", i + 1);
            System.out.println("Day " + day + " Part 1: " + PUZZLES[i].solvePart1());
            System.out.println("Day " + day + " Part 2: " + PUZZLES[i].solvePart2());
        }
    }
}