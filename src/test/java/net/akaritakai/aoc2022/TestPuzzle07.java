package net.akaritakai.aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle07 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle07("""
                $ cd /
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                $ ls
                dir e
                29116 f
                2557 g
                62596 h.lst
                $ cd e
                $ ls
                584 i
                $ cd ..
                $ cd ..
                $ cd d
                $ ls
                4060174 j
                8033020 d.log
                5626152 d.ext
                7214296 k
                """);
        assertEquals(puzzle.solvePart1(), "95437");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle07(getStoredInput(7));
        assertEquals(puzzle.solvePart1(), "1307902");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle07("""
                $ cd /
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                $ ls
                dir e
                29116 f
                2557 g
                62596 h.lst
                $ cd e
                $ ls
                584 i
                $ cd ..
                $ cd ..
                $ cd d
                $ ls
                4060174 j
                8033020 d.log
                5626152 d.ext
                7214296 k
                """);
        assertEquals(puzzle.solvePart2(), "24933642");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle07(getStoredInput(7));
        assertEquals(puzzle.solvePart2(), "7068748");
    }
}