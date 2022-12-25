
package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle25 extends BasePuzzleTest {
    @Test
    public void testSnafuToDecimal() {
        assertEquals(1, Puzzle25.fromSnafu("1"));
        assertEquals(2, Puzzle25.fromSnafu("2"));
        assertEquals(3, Puzzle25.fromSnafu("1="));
        assertEquals(4, Puzzle25.fromSnafu("1-"));
        assertEquals(5, Puzzle25.fromSnafu("10"));
        assertEquals(6, Puzzle25.fromSnafu("11"));
        assertEquals(7, Puzzle25.fromSnafu("12"));
        assertEquals(8, Puzzle25.fromSnafu("2="));
        assertEquals(9, Puzzle25.fromSnafu("2-"));
        assertEquals(10, Puzzle25.fromSnafu("20"));
        assertEquals(15, Puzzle25.fromSnafu("1=0"));
        assertEquals(20, Puzzle25.fromSnafu("1-0"));
        assertEquals(2022, Puzzle25.fromSnafu("1=11-2"));
        assertEquals(12345, Puzzle25.fromSnafu("1-0---0"));
        assertEquals(314159265, Puzzle25.fromSnafu("1121-1110-1=0"));
    }

    @Test
    public void testDecimalToSnafu() {
        assertEquals("1", Puzzle25.toSnafu(1));
        assertEquals("2", Puzzle25.toSnafu(2));
        assertEquals("1=", Puzzle25.toSnafu(3));
        assertEquals("1-", Puzzle25.toSnafu(4));
        assertEquals("10", Puzzle25.toSnafu(5));
        assertEquals("11", Puzzle25.toSnafu(6));
        assertEquals("12", Puzzle25.toSnafu(7));
        assertEquals("2=", Puzzle25.toSnafu(8));
        assertEquals("2-", Puzzle25.toSnafu(9));
        assertEquals("20", Puzzle25.toSnafu(10));
        assertEquals("1=0", Puzzle25.toSnafu(15));
        assertEquals("1-0", Puzzle25.toSnafu(20));
        assertEquals("1=11-2", Puzzle25.toSnafu(2022));
        assertEquals("1-0---0", Puzzle25.toSnafu(12345));
        assertEquals("1121-1110-1=0", Puzzle25.toSnafu(314159265));
    }

    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle25("""
                1=-0-2
                12111
                2=0=
                21
                2=01
                111
                20012
                112
                1=-1=
                1-12
                12
                1=
                122
                """);
        assertEquals(puzzle.solvePart1(), "2=-1=0");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle25(getStoredInput(25));
        assertEquals(puzzle.solvePart1(), "121=2=1==0=10=2-20=2");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle25(getStoredInput(25));
        assertEquals(puzzle.solvePart2(), "Day 25 has no part 2");
   }
}