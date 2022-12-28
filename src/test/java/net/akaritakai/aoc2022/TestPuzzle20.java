package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle20 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle20("1\n2\n-3\n3\n-2\n0\n4");
        assertEquals(puzzle.solvePart1(), "3");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle20(getStoredInput(20));
        assertEquals(puzzle.solvePart1(), "7004");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle20("1\n2\n-3\n3\n-2\n0\n4");
        assertEquals(puzzle.solvePart2(), "1623178306");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle20(getStoredInput(20));
        assertEquals(puzzle.solvePart2(), "17200008919529");
    }
}