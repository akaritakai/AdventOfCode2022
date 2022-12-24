package net.akaritakai.aoc2022;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * In Day 23, we're simulating a cellular automaton that halts.
 * TODO: This solution needs a speed-up.
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
        Multimap<Point, Point> proposals = MultimapBuilder.hashKeys().arrayListValues().build();
        for (var elf : elves) {
            var neighbors = Arrays.stream(Direction.values()).map(elf::move).map(elves::contains).toArray(Boolean[]::new);
            if (Arrays.stream(neighbors).noneMatch(b -> b)) {
                continue;
            }
            for (var i = round; i < round + 4; i++) {
                if (i % 4 == 0) {
                    if (!neighbors[Direction.NORTH.ordinal()]
                            && !neighbors[Direction.NORTH_EAST.ordinal()]
                            && !neighbors[Direction.NORTH_WEST.ordinal()]) {
                        proposals.put(elf.move(Direction.NORTH), elf);
                        break;
                    }
                } else if (i % 4 == 1) {
                    if (!neighbors[Direction.SOUTH.ordinal()]
                            && !neighbors[Direction.SOUTH_EAST.ordinal()]
                            && !neighbors[Direction.SOUTH_WEST.ordinal()]) {
                        proposals.put(elf.move(Direction.SOUTH), elf);
                        break;
                    }
                } else if (i % 4 == 2) {
                    if (!neighbors[Direction.WEST.ordinal()]
                            && !neighbors[Direction.NORTH_WEST.ordinal()]
                            && !neighbors[Direction.SOUTH_WEST.ordinal()]) {
                        proposals.put(elf.move(Direction.WEST), elf);
                        break;
                    }
                } else if (i % 4 == 3) {
                    if (!neighbors[Direction.EAST.ordinal()]
                            && !neighbors[Direction.NORTH_EAST.ordinal()]
                            && !neighbors[Direction.SOUTH_EAST.ordinal()]) {
                        proposals.put(elf.move(Direction.EAST), elf);
                        break;
                    }
                }
            }
        }
        AtomicBoolean moved = new AtomicBoolean(false);
        proposals.asMap().forEach((proposal, proposers) -> {
            if (proposers.size() == 1) {
                elves.remove(proposers.iterator().next());
                elves.add(proposal);
                moved.set(true);
            }
        });
        return moved.get();
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

    private enum Direction {
        NORTH(0, -1),
        NORTH_EAST (1, -1),
        EAST (1, 0),
        SOUTH_EAST (1, 1),
        SOUTH (0, 1),
        SOUTH_WEST (-1, 1),
        WEST (-1, 0),
        NORTH_WEST (-1, -1);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private record Point(int x, int y) {
        private Point move(Direction direction) {
            return new Point(x + direction.dx, y + direction.dy);
        }
    }
}