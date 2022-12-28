package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle22 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle22("""
                        ...#
                        .#..
                        #...
                        ....
                ...#.......#
                ........#...
                ..#....#....
                ..........#.
                        ...#....
                        .....#..
                        .#......
                        ......#.
                                
                10R5L5R10L4R5L5
                """);
        assertEquals(puzzle.solvePart1(), "6032");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle22(getStoredInput(22));
        assertEquals(puzzle.solvePart1(), "30552");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle22(getStoredInput(22));
        assertEquals(puzzle.solvePart2(), "184106");
    }
}