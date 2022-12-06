package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle06 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle06("bvwbjplbgvbhsrlpgdmjqwftvncz");
        assertEquals(puzzle.solvePart1(), "5");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle06("nppdvjthqldpwncqszvftbrmjlhg");
        assertEquals(puzzle.solvePart1(), "6");
    }

    @Test
    public void testPart1Example3() {
        var puzzle = new Puzzle06("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg");
        assertEquals(puzzle.solvePart1(), "10");
    }

    @Test
    public void testPart1Example4() {
        var puzzle = new Puzzle06("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");
        assertEquals(puzzle.solvePart1(), "11");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle06(getStoredInput(6));
        assertEquals(puzzle.solvePart1(), "1855");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle06("mjqjpqmgbljsphdztnvjfqwrcgsmlb");
        assertEquals(puzzle.solvePart2(), "19");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle06("bvwbjplbgvbhsrlpgdmjqwftvncz");
        assertEquals(puzzle.solvePart2(), "23");
    }

    @Test
    public void testPart2Example3() {
        var puzzle = new Puzzle06("nppdvjthqldpwncqszvftbrmjlhg");
        assertEquals(puzzle.solvePart2(), "23");
    }

    @Test
    public void testPart2Example4() {
        var puzzle = new Puzzle06("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg");
        assertEquals(puzzle.solvePart2(), "29");
    }

    @Test
    public void testPart2Example5() {
        var puzzle = new Puzzle06("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");
        assertEquals(puzzle.solvePart2(), "26");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle06(getStoredInput(6));
        assertEquals(puzzle.solvePart2(), "3256");
    }
}