package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle05 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle05("""
                    [D]
                [N] [C]
                [Z] [M] [P]
                 1   2   3
                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """);
        assertEquals(puzzle.solvePart1(), "CMZ");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle05(getStoredInput(5));
        assertEquals(puzzle.solvePart1(), "FJSRQCFTN");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle05("""
                    [D]
                [N] [C]
                [Z] [M] [P]
                 1   2   3
                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """);
        assertEquals(puzzle.solvePart2(), "MCD");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle05(getStoredInput(5));
        assertEquals(puzzle.solvePart2(), "CJVLJQPHS");
    }
}