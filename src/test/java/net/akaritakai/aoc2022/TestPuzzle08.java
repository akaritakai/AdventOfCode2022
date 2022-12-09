package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle08 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle08("""
                30373
                25512
                65332
                33549
                35390
                """);
        assertEquals(puzzle.solvePart1(), "21");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle08(getStoredInput(8));
        assertEquals(puzzle.solvePart1(), "1779");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle08("""
                30373
                25512
                65332
                33549
                35390
                """);
        assertEquals(puzzle.solvePart2(), "8");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle08(getStoredInput(8));
        assertEquals(puzzle.solvePart2(), "172224");
    }
}