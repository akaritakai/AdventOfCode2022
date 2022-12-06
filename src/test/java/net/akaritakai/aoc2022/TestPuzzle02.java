package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle02 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle02("""
                A Y
                B X
                C Z
                """);
        assertEquals(puzzle.solvePart1(), "15");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle02(getStoredInput(2));
        assertEquals(puzzle.solvePart1(), "15572");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle02("""
                A Y
                B X
                C Z
                """);
        assertEquals(puzzle.solvePart2(), "12");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle02(getStoredInput(2));
        assertEquals(puzzle.solvePart2(), "16098");
    }
}