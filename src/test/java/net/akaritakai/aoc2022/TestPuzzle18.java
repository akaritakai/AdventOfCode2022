
package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle18 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle18("1,1,1\n2,1,1");
        assertEquals(puzzle.solvePart1(), "10");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle18("""
                2,2,2
                1,2,2
                3,2,2
                2,1,2
                2,3,2
                2,2,1
                2,2,3
                2,2,4
                2,2,6
                1,2,5
                3,2,5
                2,1,5
                2,3,5
                """);
        assertEquals(puzzle.solvePart1(), "64");
    }


    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle18(getStoredInput(18));
        assertEquals(puzzle.solvePart1(), "4628");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle18("""
                2,2,2
                1,2,2
                3,2,2
                2,1,2
                2,3,2
                2,2,1
                2,2,3
                2,2,4
                2,2,6
                1,2,5
                3,2,5
                2,1,5
                2,3,5
                """);
        assertEquals(puzzle.solvePart2(), "58");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle18(getStoredInput(18));
        assertEquals(puzzle.solvePart2(), "2582");
   }
}