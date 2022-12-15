package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle14 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle14("""
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9
                """);
        assertEquals(puzzle.solvePart1(), "24");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle14(getStoredInput(14));
        assertEquals(puzzle.solvePart1(), "795");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle14("""
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9
                """);
        assertEquals(puzzle.solvePart2(), "93");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle14(getStoredInput(14));
        assertEquals(puzzle.solvePart2(), "30214");
    }
}