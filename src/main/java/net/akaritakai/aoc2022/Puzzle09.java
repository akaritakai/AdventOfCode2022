package net.akaritakai.aoc2022;

import java.util.HashSet;
import java.util.Set;

/**
 * In Day 9, we are performing a physics simulation of how parts of a rope will move when being pulled by the part of
 * the rope ahead of it.
 */
public class Puzzle09 extends AbstractPuzzle {
    public Puzzle09(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var rope = new Rope(2);
        for (var line : getPuzzleInput().split("\n")) {
            rope.move(line);
        }
        return String.valueOf(rope.seen.size());
    }

    @Override
    public String solvePart2() {
        var rope = new Rope(10);
        for (var line : getPuzzleInput().split("\n")) {
            rope.move(line);
        }
        return String.valueOf(rope.seen.size());
    }

    private static final class Rope {
        private final Point[] knots;
        private final Set<Point> seen = new HashSet<>();

        private Rope(int N) {
            this.knots = new Point[N];
            for (int i = 0; i < N; i++) {
                this.knots[i] = new Point(0, 0);
            }
            seen.add(this.knots[N - 1]);
        }

        private void move(String line) {
            var direction = line.charAt(0);
            var distance = Integer.parseInt(line.substring(2));
            for (int i = 0; i < distance; i++) {
                switch (direction) {
                    case 'U' -> move(0, 1);
                    case 'D' -> move(0, -1);
                    case 'L' -> move(-1, 0);
                    case 'R' -> move(1, 0);
                }
            }
        }

        private void move(int dx, int dy) {
            knots[0] = knots[0].move(dx, dy);
            for (int i = 1; i < knots.length; i++) {
                dx = knots[i - 1].x - knots[i].x;
                dy = knots[i - 1].y - knots[i].y;
                if (knots[i - 1].x == knots[i].x && Math.abs(dy) > 1) {
                    knots[i] = knots[i].move(0, (int) Math.signum(dy));
                } else if (knots[i - 1].y == knots[i].y && Math.abs(dx) > 1) {
                    knots[i] = knots[i].move((int) Math.signum(dx), 0);
                } else if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
                    knots[i] = knots[i].move((int) Math.signum(dx), (int) Math.signum(dy));
                }
            }
            seen.add(knots[knots.length - 1]);
        }
    }

    private record Point(int x, int y) {
        public Point move(int dx, int dy) {
            return new Point(x + dx, y + dy);
        }
    }
}