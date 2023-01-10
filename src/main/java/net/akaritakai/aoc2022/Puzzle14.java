package net.akaritakai.aoc2022;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * In Day 14, we are performing a physics simulation of falling sand.
 */
public class Puzzle14 extends AbstractPuzzle {

    public Puzzle14(String puzzleInput) {
        super(puzzleInput);
    }

    private static int fill(boolean[][] cave, boolean useFloor) {
        var sand = 0;
        var floor = cave.length - 1;
        var startCol = cave[0].length / 2;
        var filled = false;
        while (!filled) {
            var row = 0;
            var col = startCol;
            while (true) {
                if (row == floor) {
                    filled = !useFloor;
                    if (useFloor) {
                        cave[row][col] = true;
                        sand++;
                    }
                    break;
                }
                if (!cave[row + 1][col]) { // Down
                    row++;
                    continue;
                }
                if (!cave[row + 1][col - 1]) { // Down left
                    row++;
                    col--;
                    continue;
                }
                if (!cave[row + 1][col + 1]) { // Down right
                    row++;
                    col++;
                    continue;
                }
                if (row == 0 && col == startCol) {
                    filled = true;
                }
                cave[row][col] = true;
                sand++;
                break;
            }
        }
        return sand;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(fill(makeCave(), false));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(fill(makeCave(), true));
    }

    private boolean[][] makeCave() {
        // Find all the rocks in the cave
        var rocks = new HashSet<Point>();
        var pattern = Pattern.compile("(\\d+),(\\d+)");
        for (var line : getPuzzleInput().split("\n")) {
            var matcher = pattern.matcher(line);
            Point point = null;
            while (matcher.find()) {
                var col = Integer.parseInt(matcher.group(1));
                var row = Integer.parseInt(matcher.group(2));
                if (point == null) {
                    point = new Point(row, col);
                    rocks.add(point);
                } else {
                    var next = new Point(row, col);
                    if (point.col == next.col) {
                        var min = Math.min(point.row, next.row);
                        var max = Math.max(point.row, next.row);
                        for (var i = min; i <= max; i++) {
                            rocks.add(new Point(i, point.col));
                        }
                    } else {
                        var min = Math.min(point.col, next.col);
                        var max = Math.max(point.col, next.col);
                        for (var i = min; i <= max; i++) {
                            rocks.add(new Point(point.row, i));
                        }
                    }
                    point = next;
                }
            }
        }
        // Build the cave
        var height = rocks.stream().mapToInt(p -> p.row).max().orElseThrow() + 2;
        var width = 2 * height + 1;
        var cave = new boolean[height][width];
        for (var rock : rocks) {
            cave[rock.row][rock.col - 500 + (width / 2)] = true;
        }
        return cave;
    }

    private record Point(int row, int col) {
    }
}