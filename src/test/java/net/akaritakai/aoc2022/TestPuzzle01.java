package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle01 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle01("""
                1000
                2000
                3000
                
                4000
                
                5000
                6000
                
                7000
                8000
                9000
                
                10000
                """);
        assertEquals(puzzle.solvePart1(), "24000");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle01(getStoredInput(1));
        assertEquals(puzzle.solvePart1(), "75622");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle01("""
                1000
                2000
                3000
                
                4000
                
                5000
                6000
                
                7000
                8000
                9000
                
                10000
                """);
        assertEquals(puzzle.solvePart2(), "45000");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle01(getStoredInput(1));
        assertEquals(puzzle.solvePart2(), "213159");
    }
}