package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle24 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle24("""
                #.######
                #>>.<^<#
                #.<..<<#
                #>v.><>#
                #<^v^^>#
                ######.#
                """);
        assertEquals(puzzle.solvePart1(), "18");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle24(getStoredInput(24));
        assertEquals(puzzle.solvePart1(), "326");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle24("""
                #.######
                #>>.<^<#
                #.<..<<#
                #>v.><>#
                #<^v^^>#
                ######.#
                """);
        assertEquals(puzzle.solvePart2(), "54");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle24(getStoredInput(24));
        assertEquals(puzzle.solvePart2(), "976");
    }
}