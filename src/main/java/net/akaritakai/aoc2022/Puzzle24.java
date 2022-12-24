package net.akaritakai.aoc2022;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * In Day 24, we're calculating the path through a maze with obstacles that move periodically.
 * We solve the maze by calculating neighbors at each step and using an A* search with a manhattan distance heuristic.
 */
public class Puzzle24 extends AbstractPuzzle {

    public Puzzle24(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 24;
    }

    @Override
    public String solvePart1() {
        var blizzards = new MazeState();
        var goal = findPath(blizzards, new Vertex(0, -1, 0), blizzards.width - 1, blizzards.height);
        return String.valueOf(goal.time);
    }

    @Override
    public String solvePart2() {
        var blizzards = new MazeState();
        var goal = findPath(blizzards, new Vertex(0, -1, 0), blizzards.width - 1, blizzards.height);
        goal = findPath(blizzards, goal, 0, -1);
        goal = findPath(blizzards, goal, blizzards.width - 1, blizzards.height);
        return String.valueOf(goal.time);
    }

    private static Vertex findPath(MazeState blizzards, Vertex vertex, int endX, int endY) {
        var openSet = new PriorityQueue<Vertex>((v1, v2) -> {
            int score1 = v1.time + Math.abs(v1.x - endX) + Math.abs(v1.y - endY);
            int score2 = v2.time + Math.abs(v2.x - endX) + Math.abs(v2.y - endY);
            return Integer.compare(score1, score2);
        });
        var seen = new HashSet<Vertex>();
        openSet.add(vertex);
        while (!openSet.isEmpty()) {
            var current = openSet.poll();
            if (current.x == endX && current.y == endY) {
                return current;
            }
            if (!seen.add(current)) {
                continue;
            }
            if (blizzards.isSafe(current.x + 1, current.y, current.time + 1)) {
                openSet.add(new Vertex(current.x + 1, current.y, current.time + 1));
            }
            if (blizzards.isSafe(current.x, current.y + 1, current.time + 1)) {
                openSet.add(new Vertex(current.x, current.y + 1, current.time + 1));
            }
            if (blizzards.isSafe(current.x, current.y, current.time + 1)) {
                openSet.add(new Vertex(current.x, current.y, current.time + 1));
            }
            if (blizzards.isSafe(current.x - 1, current.y, current.time + 1)) {
                openSet.add(new Vertex(current.x - 1, current.y, current.time + 1));
            }
            if (blizzards.isSafe(current.x, current.y - 1, current.time + 1)) {
                openSet.add(new Vertex(current.x, current.y - 1, current.time + 1));
            }
        }
        throw new IllegalStateException("No path found");
    }


    /** Holds snapshot states for the Blizzards. Assumes they don't leave the maze. */
    private final class MazeState {
        // A list of (x,dx) coordinates for blizzards moving left/right at a given y at the start
        private final int[][][] rowBlizzards;

        // A list of (y,dy) coordinates for blizzards moving up/down at a given x at the start
        private final int[][][] colBlizzards;
        private final int height;
        private final int width;

        private MazeState() {
            var lines = getPuzzleInput().trim().split("\n");
            height = lines.length - 2;
            width = lines[0].length() - 2;

            rowBlizzards = IntStream.range(0, height).mapToObj(y -> IntStream.range(0, width).boxed()
                    .flatMap(x -> switch (lines[y + 1].charAt(x + 1)) {
                        case '>' -> Stream.of(new int[]{x, 1});
                        case '<' -> Stream.of(new int[]{x, -1});
                        default -> Stream.of();
                    }).toArray(int[][]::new)).toArray(int[][][]::new);
            colBlizzards = IntStream.range(0, width).mapToObj(x -> IntStream.range(0, height).boxed()
                    .flatMap(y -> switch (lines[y + 1].charAt(x + 1)) {
                        case '^' -> Stream.of(new int[]{y, -1});
                        case 'v' -> Stream.of(new int[]{y, 1});
                        default -> Stream.of();
                    }).toArray(int[][]::new)).toArray(int[][][]::new);
        }

        private boolean isSafe(int x, int y, int time) {
            if (time < 0) return false;
            if (x == 0 && y == -1) return true;
            if (x == width - 1 && y == height) return true;
            if (x < 0 || x >= width) return false;
            if (y < 0 || y >= height) return false;
            for (int[] blizz : rowBlizzards[y]) {
                var col = (blizz[0] + blizz[1] * time) % width;
                if (col < 0) {
                    col += width;
                }
                if (col == x) {
                    return false;
                }
            }
            for (int[] blizz : colBlizzards[x]) {
                var row = (blizz[0] + blizz[1] * time) % height;
                if (row < 0) {
                    row += height;
                }
                if (row == y) {
                    return false;
                }
            }
            return true;
        }
    }

    private record Vertex(int x, int y, int time) {
    }
}
