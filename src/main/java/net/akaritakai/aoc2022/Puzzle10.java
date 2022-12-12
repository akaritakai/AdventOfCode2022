package net.akaritakai.aoc2022;

import net.akaritakai.aoc2022.ocr.LetterOcr;

import java.util.List;

/**
 * In Day 10, we're emulating a processor that drives a CRT display and then reading the display via OCR.
 */
public class Puzzle10 extends AbstractPuzzle {

    public Puzzle10(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 10;
    }

    @Override
    public String solvePart1() {
        var cpu = new CPU();
        for (var line : getPuzzleInput().split("\n")) {
            cpu.execute(line);
        }
        var sum = 0;
        for (var cycle : List.of(20, 60, 100, 140, 180, 220)) {
            sum += cpu.values[cycle - 1] * cycle;
        }
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var cpu = new CPU();
        for (var line : getPuzzleInput().split("\n")) {
            cpu.execute(line);
        }
        var image = draw(cpu.values);
        return LetterOcr.parse(image);
    }

    private static boolean[][] draw(int[] values) {
        boolean[][] display = new boolean[6][40];
        for (var cycle = 0; cycle < 240; cycle++) {
            var col = cycle % 40;
            var row = cycle / 40;
            if (Math.abs(col - values[cycle]) <= 1) {
                display[row][col] = true;
            }
        }
        return display;
    }

    private static final class CPU {
        private int cycle = 1;
        private int x = 1;
        private final int[] values = new int[240];

        private void execute(String instruction) {
            if (cycle > 240) {
                return;
            }
            if (instruction.equals("noop")) {
                values[cycle++ - 1] = x;
            } else if (instruction.startsWith("addx")) {
                values[cycle++ - 1] = x;
                values[cycle++ - 1] = x;
                x += Integer.parseInt(instruction.substring(5));
            }
        }
    }

}