package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle17 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle17(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>");
        assertEquals(puzzle.solvePart1(), "3068");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle17(getStoredInput(17));
        assertEquals(puzzle.solvePart1(), "3181");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle17(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>");
        assertEquals(puzzle.solvePart2(), "1514285714288");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle17(getStoredInput(17));
        assertEquals(puzzle.solvePart2(), "1570434782634");
   }
}