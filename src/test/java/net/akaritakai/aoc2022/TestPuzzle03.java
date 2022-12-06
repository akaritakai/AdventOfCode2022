package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle03 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle03("""
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """);
        assertEquals(puzzle.solvePart1(), "157");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle03(getStoredInput(3));
        assertEquals(puzzle.solvePart1(), "7875");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle03("""
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """);
        assertEquals(puzzle.solvePart2(), "70");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle03(getStoredInput(3));
        assertEquals(puzzle.solvePart2(), "2479");
    }
}