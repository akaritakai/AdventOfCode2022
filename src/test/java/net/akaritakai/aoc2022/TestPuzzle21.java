
package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle21 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle21("""
                root: pppw + sjmn
                dbpl: 5
                cczh: sllz + lgvd
                zczc: 2
                ptdq: humn - dvpt
                dvpt: 3
                lfqf: 4
                humn: 5
                ljgn: 2
                sjmn: drzm * dbpl
                sllz: 4
                pppw: cczh / lfqf
                lgvd: ljgn * ptdq
                drzm: hmdt - zczc
                hmdt: 32
                """);
        assertEquals(puzzle.solvePart1(), "152");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle21(getStoredInput(21));
        assertEquals(puzzle.solvePart1(), "142707821472432");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle21("""
                root: pppw + sjmn
                dbpl: 5
                cczh: sllz + lgvd
                zczc: 2
                ptdq: humn - dvpt
                dvpt: 3
                lfqf: 4
                humn: 5
                ljgn: 2
                sjmn: drzm * dbpl
                sllz: 4
                pppw: cczh / lfqf
                lgvd: ljgn * ptdq
                drzm: hmdt - zczc
                hmdt: 32
                """);
        assertEquals(puzzle.solvePart2(), "301");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle21(getStoredInput(21));
        assertEquals(puzzle.solvePart2(), "3587647562851");
   }
}