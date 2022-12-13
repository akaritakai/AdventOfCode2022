package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle13 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle13("""
                [1,1,3,1,1]
                [1,1,5,1,1]
                                
                [[1],[2,3,4]]
                [[1],4]
                                
                [9]
                [[8,7,6]]
                                
                [[4,4],4,4]
                [[4,4],4,4,4]
                                
                [7,7,7,7]
                [7,7,7]
                                
                []
                [3]
                                
                [[[]]]
                [[]]
                                
                [1,[2,[3,[4,[5,6,7]]]],8,9]
                [1,[2,[3,[4,[5,6,0]]]],8,9]
                """);
        assertEquals(puzzle.solvePart1(), "13");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle13(getStoredInput(13));
        assertEquals(puzzle.solvePart1(), "6272");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle13("""
                [1,1,3,1,1]
                [1,1,5,1,1]
                                
                [[1],[2,3,4]]
                [[1],4]
                                
                [9]
                [[8,7,6]]
                                
                [[4,4],4,4]
                [[4,4],4,4,4]
                                
                [7,7,7,7]
                [7,7,7]
                                
                []
                [3]
                                
                [[[]]]
                [[]]
                                
                [1,[2,[3,[4,[5,6,7]]]],8,9]
                [1,[2,[3,[4,[5,6,0]]]],8,9]
                """);
        assertEquals(puzzle.solvePart2(), "140");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle13(getStoredInput(13));
        assertEquals(puzzle.solvePart2(), "22288");
    }
}