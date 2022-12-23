
package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle19 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var blueprint = new Puzzle19.Blueprint(1, new int[][] {
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {3, 14, 0, 0},
                {2, 0, 7, 0}});
        var mining = new Puzzle19.Mining(blueprint, 24);
        assertEquals(mining.simulate(), 9);
    }

    @Test
    public void testPart1Example2() {
        var blueprint = new Puzzle19.Blueprint(1, new int[][] {
                {2, 0, 0, 0},
                {3, 0, 0, 0},
                {3, 8, 0, 0},
                {3, 0, 12, 0}});
        var mining = new Puzzle19.Mining(blueprint, 24);
        assertEquals(mining.simulate(), 12);
    }

    @Test
    public void testPart1Example3() {
        var puzzle = new Puzzle19("""
                Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
                """);
        assertEquals(puzzle.solvePart1(), "33");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle19(getStoredInput(19));
        assertEquals(puzzle.solvePart1(), "1418");
    }

    @Test
    public void testPart2Example1() {
        var blueprint = new Puzzle19.Blueprint(1, new int[][] {
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {3, 14, 0, 0},
                {2, 0, 7, 0}});
        var mining = new Puzzle19.Mining(blueprint, 32);
        assertEquals(mining.simulate(), 56);
    }

    @Test
    public void testPart2Example2() {
        var blueprint = new Puzzle19.Blueprint(1, new int[][] {
                {2, 0, 0, 0},
                {3, 0, 0, 0},
                {3, 8, 0, 0},
                {3, 0, 12, 0}});
        var mining = new Puzzle19.Mining(blueprint, 32);
        assertEquals(mining.simulate(), 62);
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle19(getStoredInput(19));
        assertEquals(puzzle.solvePart2(), "4114");
   }
}