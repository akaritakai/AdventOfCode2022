package net.akaritakai.aoc2022;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * In Day 14, we are performing a physics simulation of falling sand.
 */
public class Puzzle14 extends AbstractPuzzle {

    public Puzzle14(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 14;
    }

    @Override
    public String solvePart1() {
        var cave = new Cave();
        return String.valueOf(cave.fill(false));
    }

    @Override
    public String solvePart2() {
        var cave = new Cave();
        return String.valueOf(cave.fill(true));
    }

    private final class Cave {
        private final Set<Point> rock = new HashSet<>();
        private final Set<Point> sand = new HashSet<>();
        private final int floor;

        private Cave() {
            var pattern = Pattern.compile("(\\d+),(\\d+)");
            for (var line : getPuzzleInput().split("\n")) {
                var matcher = pattern.matcher(line);
                Point point = null;
                while (matcher.find()) {
                    var x = Integer.parseInt(matcher.group(1));
                    var y = Integer.parseInt(matcher.group(2));
                    if (point == null) {
                        point = new Point(x, y);
                        rock.add(point);
                    } else {
                        var next = new Point(x, y);
                        if (point.x == next.x) {
                            var min = Math.min(point.y, next.y);
                            var max = Math.max(point.y, next.y);
                            for (var i = min; i <= max; i++) {
                                rock.add(new Point(point.x, i));
                            }
                        } else {
                            var min = Math.min(point.x, next.x);
                            var max = Math.max(point.x, next.x);
                            for (var i = min; i <= max; i++) {
                                rock.add(new Point(i, point.y));
                            }
                        }
                        point = next;
                    }
                }
            }
            floor = rock.stream().mapToInt(p -> p.y).max().orElseThrow() + 1;
        }

        private int fill(boolean useFloor) {
            var filled = false;
            while (!filled) {
                var point = new Point(500, 0);
                while (true) {
                    if (point.y == floor) {
                        filled = !useFloor;
                        if (useFloor) {
                            sand.add(point);
                        }
                        break;
                    }
                    var down = new Point(point.x, point.y + 1);
                    if (!rock.contains(down) && !sand.contains(down)) {
                        point = down;
                        continue;
                    }
                    var downLeft = new Point(point.x - 1, point.y + 1);
                    if (!rock.contains(downLeft) && !sand.contains(downLeft)) {
                        point = downLeft;
                        continue;
                    }
                    var downRight = new Point(point.x + 1, point.y + 1);
                    if (!rock.contains(downRight) && !sand.contains(downRight)) {
                        point = downRight;
                        continue;
                    }
                    if (point.x == 500 && point.y == 0) {
                        filled = true;
                    }
                    sand.add(point);
                    break;
                }
            }
            return sand.size();
        }
    }

    private record Point(int x, int y) {
    }
}