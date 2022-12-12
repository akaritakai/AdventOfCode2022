package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle09 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle09("""
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2
                """);
        assertEquals(puzzle.solvePart1(), "13");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle09(getStoredInput(9));
        assertEquals(puzzle.solvePart1(), "6090");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle09("""
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2
                """);
        assertEquals(puzzle.solvePart2(), "1");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle09("""
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20
                """);
        assertEquals(puzzle.solvePart2(), "36");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle09(getStoredInput(9));
        assertEquals(puzzle.solvePart2(), "2566");
    }
}