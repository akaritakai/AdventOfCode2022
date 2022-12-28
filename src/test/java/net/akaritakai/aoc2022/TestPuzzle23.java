package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle23 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle23("""
                ....#..
                ..###.#
                #...#.#
                .#...##
                #.###..
                ##.#.##
                .#..#..
                """);
        assertEquals(puzzle.solvePart1(), "110");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle23(getStoredInput(23));
        assertEquals(puzzle.solvePart1(), "4082");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle23("""
                ....#..
                ..###.#
                #...#.#
                .#...##
                #.###..
                ##.#.##
                .#..#..
                """);
        assertEquals(puzzle.solvePart2(), "20");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle23(getStoredInput(23));
        assertEquals(puzzle.solvePart2(), "1065");
    }
}