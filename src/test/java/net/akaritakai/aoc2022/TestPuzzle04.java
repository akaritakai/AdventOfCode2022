package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle04 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle04("""
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8
                """);
        assertEquals(puzzle.solvePart1(), "2");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle04(getStoredInput(4));
        assertEquals(puzzle.solvePart1(), "540");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle04("""
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8
                """);
        assertEquals(puzzle.solvePart2(), "4");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle04(getStoredInput(4));
        assertEquals(puzzle.solvePart2(), "872");
    }
}