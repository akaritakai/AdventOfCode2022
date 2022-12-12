package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle12 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle12("""
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi
                """);
        assertEquals(puzzle.solvePart1(), "31");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle12(getStoredInput(12));
        assertEquals(puzzle.solvePart1(), "408");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle12("""
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi
                """);
        assertEquals(puzzle.solvePart2(), "29");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle12(getStoredInput(12));
        assertEquals(puzzle.solvePart2(), "399");
    }
}