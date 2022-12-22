package net.akaritakai.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * In Day 17, we are simulating a Tetris game and observing how tall the stack gets.
 */
public class Puzzle17 extends AbstractPuzzle {

    public Puzzle17(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 17;
    }

    @Override
    public String solvePart1() {
        var tetris = new Tetris();
        for (var i = 0; i < 2022; i++) {
            tetris.tick();
        }
        return String.valueOf(tetris.grid.size());
    }

    @Override
    public String solvePart2() {
        var tetris = new Tetris();

        // Find the cycle
        List<Integer> dy = new ArrayList<>();
        int cycleLength;
        while ((cycleLength = findCycleLength(dy)) == 0) {
            var y1 = tetris.grid.size();
            tetris.tick();
            var y2 = tetris.grid.size();
            dy.add(y2 - y1);
        }

        long rocks = 1000000000000L;

        // Add the heights of the rocks before the cycle starts
        long count = dy.size() - 2L * cycleLength;
        long height = dy.subList(0, (int) count).stream().mapToInt(Integer::intValue).sum();
        rocks -= count;

        // Add the heights of all the full cycles
        dy = dy.subList((int) count, (int) count + cycleLength);
        count = rocks / dy.size();
        height += count * dy.stream().mapToInt(Integer::intValue).sum();
        rocks -= count * dy.size();

        // Add the heights of the last partial cycle
        height += dy.subList(0, (int) rocks).stream().mapToInt(Integer::intValue).sum();
        return String.valueOf(height);
    }

    private int findCycleLength(List<Integer> sequence) {
        for (int length = getPuzzleInput().trim().length(); length < sequence.size() / 2; length++) {
            boolean matches = true;
            for (int i = 0; i < length; i++) {
                if (!Objects.equals(sequence.get(sequence.size() - 2 * length + i), sequence.get(sequence.size() - length + i))) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return length;
            }
        }
        return 0; // Not found
    }

    private final class Tetris {
        private final Supplier<Integer> nextPush = nextPush();
        private final Supplier<Shape> nextShape = nextShape();
        private final List<Integer> grid = new ArrayList<>();

        private void tick() {
            var shape = nextShape.get();
            var spawn = shape.spawn(grid.size() - 1);
            var block = new Block(shape, spawn);
            boolean dropped;
            do {
                block.push(nextPush.get());
                dropped = block.drop();
            } while (dropped);
            block.addToGrid();
        }

        private boolean notFree(int x, int y) {
            return x < 0 || x > 6 || y < 0 || (y < grid.size() && (grid.get(y) & (0b1000000 >> x)) != 0);
        }

        private final class Block {
            private final Shape shape;
            private int x;
            private int y;

            private Block(Shape shape, int[] spawn) {
                this.shape = shape;
                this.x = spawn[0];
                this.y = spawn[1];
            }

            private int[][] leftEdge() {
                return switch (shape) {
                    case SHAPE_1 -> new int[][] { {x, y} };
                    case SHAPE_2 -> new int[][] { {x, y}, {x - 1, y - 1}, {x, y - 2} };
                    case SHAPE_3 -> new int[][] { {x, y}, {x, y - 1}, {x - 2, y - 2} };
                    case SHAPE_4 -> new int[][] { {x, y}, {x, y - 1}, {x, y - 2}, {x, y - 3} };
                    case SHAPE_5 -> new int[][] { {x, y}, {x, y - 1} };
                };
            }

            private int[][] rightEdge() {
                return switch (shape) {
                    case SHAPE_1 -> new int[][] { {x + 3, y} };
                    case SHAPE_2 -> new int[][] { {x, y}, {x + 1, y - 1}, {x, y - 2} };
                    case SHAPE_3 -> new int[][] { {x, y}, {x, y - 1}, {x, y - 2} };
                    case SHAPE_4 -> new int[][] { {x, y}, {x, y - 1}, {x, y - 2}, {x, y - 3} };
                    case SHAPE_5 -> new int[][] { {x + 1, y}, {x + 1, y - 1} };
                };
            }

            private int[][] bottomEdge() {
                return switch (shape) {
                    case SHAPE_1 -> new int[][] { {x, y}, {x + 1, y}, {x + 2, y}, {x + 3, y} };
                    case SHAPE_2 -> new int[][] { {x - 1, y - 1}, {x, y - 2}, {x + 1, y - 1} };
                    case SHAPE_3 -> new int[][] { {x - 2, y - 2}, {x - 1, y - 2}, {x, y - 2} };
                    case SHAPE_4 -> new int[][] { {x, y - 3} };
                    case SHAPE_5 -> new int[][] { {x, y - 1}, {x + 1, y - 1} };
                };
            }

            private void push(int push) {
                var edge = push < 0 ? leftEdge() : rightEdge();
                for (var e : edge) {
                    if (notFree(e[0] + push, e[1])) {
                        return;
                    }
                }
                x += push;
            }

            private boolean drop() {
                for (var e : bottomEdge()) {
                    if (notFree(e[0], e[1] - 1)) {
                        return false;
                    }
                }
                y--;
                return true;
            }

            private void addToGrid() {
                while (grid.size() < y + 1) {
                    grid.add(0);
                }
                switch (shape) {
                    case SHAPE_1 -> grid.set(y, grid.get(y) | (0b1111000 >> x));
                    case SHAPE_2 -> {
                        grid.set(y, grid.get(y) | (0b0100000 >> (x - 1)));
                        grid.set(y - 1, grid.get(y - 1) | (0b1110000 >> (x - 1)));
                        grid.set(y - 2, grid.get(y - 2) | (0b0100000 >> (x - 1)));
                    }
                    case SHAPE_3 -> {
                        grid.set(y, grid.get(y) | (0b0010000 >> (x - 2)));
                        grid.set(y - 1, grid.get(y - 1) | (0b0010000 >> (x - 2)));
                        grid.set(y - 2, grid.get(y - 2) | (0b1110000 >> (x - 2)));
                    }
                    case SHAPE_4 -> {
                        for (var i = y; i > y - 4; i--) {
                            grid.set(i, grid.get(i) | (0b1000000 >> x));
                        }
                    }
                    case SHAPE_5 -> {
                        grid.set(y, grid.get(y) | (0b1100000 >> x));
                        grid.set(y - 1, grid.get(y - 1) | (0b1100000 >> x));
                    }
                }
            }
        }

        private enum Shape {
            SHAPE_1, // '-'
            SHAPE_2, // '+'
            SHAPE_3, // Backwards 'L'
            SHAPE_4, // 'I'
            SHAPE_5; // Square

            private int[] spawn(int y) {
                return switch (this) {
                    case SHAPE_1 -> new int[] { 2, y + 4 };
                    case SHAPE_2 -> new int[] { 3, y + 6 };
                    case SHAPE_3 -> new int[] { 4, y + 6 };
                    case SHAPE_4 -> new int[] { 2, y + 7 };
                    case SHAPE_5 -> new int[] { 2, y + 5 };
                };
            }
        }

        private static Supplier<Shape> nextShape() {
            return new Supplier<>() {
                private final Shape[] shapes = Shape.values();
                private int i = 0;

                @Override
                public Shape get() {
                    return shapes[i++ % shapes.length];
                }
            };
        }
    }

    private Supplier<Integer> nextPush() {
        return new Supplier<>() {
            private final char[] input = getPuzzleInput().trim().toCharArray();
            private int i = 0;

            @Override
            public Integer get() {
                return switch (input[i++ % input.length]) {
                    case '<' -> -1;
                    case '>' -> 1;
                    default -> throw new IllegalArgumentException("Invalid push: " + input[i - 1]);
                };
            }
        };
    }
}