package net.akaritakai.aoc2022;

import java.util.*;

/**
 * In Day 23, we're simulating a cellular automaton that halts.
 */
public class Puzzle23 extends AbstractPuzzle {

    public Puzzle23(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 23;
    }

    @Override
    public String solvePart1() {
        var elves = parseInput();
        for (var i = 0; i < 10; i++) {
            doRound(elves, i);
        }
        return String.valueOf(countEmpty(elves));
    }

    @Override
    public String solvePart2() {
        var elves = parseInput();
        var i = 0;
        while (doRound(elves, i)) {
            i++;
        }
        return String.valueOf(i + 1);
    }

    private boolean doRound(Set<Point> elves, int round) {
        Map<Point, List<Point>> proposals = new HashMap<>();
        for (var elf : elves) {
            var hasNeighbors = false;
            var neighbors = new boolean[8];
            var i = 0;
            for (var dx = -1; dx <= 1; dx++) {
                for (var dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    boolean hasNeighbor = elves.contains(new Point(elf.x + dx, elf.y + dy));
                    hasNeighbors |= hasNeighbor;
                    neighbors[i++] = hasNeighbor;
                }
            }
            if (!hasNeighbors) {
                continue;
            }
            for (i = round; i < round + 4; i++) {
                if (i % 4 == 0) {
                    if (!neighbors[0] && !neighbors[3] && !neighbors[5]) {
                        proposals.computeIfAbsent(new Point(elf.x, elf.y - 1), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                } else if (i % 4 == 1) {
                    if (!neighbors[2] && !neighbors[4] && !neighbors[7]) {
                        proposals.computeIfAbsent(new Point(elf.x, elf.y + 1), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                } else if (i % 4 == 2) {
                    if (!neighbors[0] && !neighbors[1] && !neighbors[2]) {
                        proposals.computeIfAbsent(new Point(elf.x - 1, elf.y), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                } else if (i % 4 == 3) {
                    if (!neighbors[5] && !neighbors[6] && !neighbors[7]) {
                        proposals.computeIfAbsent(new Point(elf.x + 1, elf.y), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                }
            }
        }
        boolean moved = false;
        for (var entry : proposals.entrySet()) {
            if (entry.getValue().size() == 1) {
                elves.remove(entry.getValue().get(0));
                elves.add(entry.getKey());
                moved = true;
            }
        }
        return moved;
    }

    private long countEmpty(Set<Point> elves) {
        var minX = elves.stream().mapToLong(point -> point.x).min().orElseThrow();
        var maxX = elves.stream().mapToLong(point -> point.x).max().orElseThrow();
        var minY = elves.stream().mapToLong(point -> point.y).min().orElseThrow();
        var maxY = elves.stream().mapToLong(point -> point.y).max().orElseThrow();
        return (maxX - minX + 1) * (maxY - minY + 1) - elves.size();
    }

    private Set<Point> parseInput() {
        var points = new HashSet<Point>();
        var lines = getPuzzleInput().trim().split("\n");
        for (var y = 0; y < lines.length; y++) {
            for (var x = 0; x < lines[y].length(); x++) {
                if (lines[y].charAt(x) == '#') {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    private record Point(int x, int y) {
    }
}