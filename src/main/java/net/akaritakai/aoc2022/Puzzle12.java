package net.akaritakai.aoc2022;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * In Day 12, we are finding the shortest path through a graph using Dijkstra's algorithm.
 */
public class Puzzle12 extends AbstractPuzzle {

    public Puzzle12(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(new Maze().part1Path());
    }

    @Override
    public String solvePart2() {
        return String.valueOf(new Maze().part2Path());
    }

    private final class Maze {
        private static final int[][] DIRECTIONS = new int[][]{
                {-1, 0}, // Up
                {1, 0}, // Down
                {0, -1}, // Left
                {0, 1}, // Right
        };
        private final int height;
        private final int width;

        private final int[][] heights;
        private final int[] start = new int[2];
        private final int[] end = new int[2];

        private Maze() {
            var lines = getPuzzleInput().trim().split("\n");
            height = lines.length;
            width = lines[0].length();
            heights = new int[height][width];
            for (var row = 0; row < height; row++) {
                for (var col = 0; col < width; col++) {
                    switch (lines[row].charAt(col)) {
                        case 'S' -> {
                            start[0] = row;
                            start[1] = col;
                            heights[row][col] = 0;
                        }
                        case 'E' -> {
                            end[0] = row;
                            end[1] = col;
                            heights[row][col] = 25;
                        }
                        default -> heights[row][col] = lines[row].charAt(col) - 'a';
                    }
                }
            }
        }

        private int part1Path() {
            // Find the shortest distance between the end point and the start point.
            // We use A* with a Manhattan distance heuristic.
            var gScore = new int[height][width];
            var fScore = new int[height][width];
            for (var row = 0; row < height; row++) {
                for (var col = 0; col < width; col++) {
                    gScore[row][col] = Integer.MAX_VALUE;
                    fScore[row][col] = Integer.MAX_VALUE;
                }
            }
            var queue = new PriorityQueue<int[]>(Comparator.comparingInt(p -> fScore[p[0]][p[1]]));

            // Enqueue the origin (end)
            gScore[end[0]][end[1]] = 0;
            fScore[end[0]][end[1]] = Math.abs(end[0] - start[0]) + Math.abs(end[1] - start[1]);
            queue.add(end);

            // Perform the search
            while (!queue.isEmpty()) {
                var current = queue.poll();
                if (current[0] == start[0] && current[1] == start[1]) {
                    return gScore[current[0]][current[1]];
                }

                // Explore neighbors
                var score = gScore[current[0]][current[1]] + 1;
                for (var direction : DIRECTIONS) {
                    var row = current[0] + direction[0];
                    var col = current[1] + direction[1];
                    if (row >= 0 && row < height && col >= 0 && col < width // in bounds
                            && heights[current[0]][current[1]] - 1 <= heights[row][col] // reachable neighbor
                            && score < gScore[row][col]) { // score improvement
                        gScore[row][col] = score;
                        fScore[row][col] = score + Math.abs(row - start[0]) + Math.abs(col - start[1]);
                        queue.add(new int[]{row, col});
                    }
                }
            }
            throw new IllegalStateException("No path found");
        }

        private int part2Path() {
            // Find the shortest distance between the end point and any valley (height == 0).
            // We use Dijkstra's algorithm.
            var distance = new int[height][width];
            for (var row = 0; row < height; row++) {
                for (var col = 0; col < width; col++) {
                    distance[row][col] = Integer.MAX_VALUE;
                }
            }
            var queue = new PriorityQueue<int[]>(Comparator.comparingInt(p -> distance[p[0]][p[1]]));

            // Enqueue the origin (end)
            distance[end[0]][end[1]] = 0;
            queue.add(end);

            // Perform the search
            while (!queue.isEmpty()) {
                var current = queue.poll();
                if (heights[current[0]][current[1]] == 0) {
                    return distance[current[0]][current[1]];
                }

                // Explore neighbors
                var score = distance[current[0]][current[1]] + 1;
                for (var direction : DIRECTIONS) {
                    var row = current[0] + direction[0];
                    var col = current[1] + direction[1];
                    if (row >= 0 && row < height && col >= 0 && col < width // in bounds
                            && heights[current[0]][current[1]] - 1 <= heights[row][col] // reachable neighbor
                            && score < distance[row][col]) { // score improvement
                        distance[row][col] = score;
                        queue.add(new int[]{row, col});
                    }
                }
            }
            throw new IllegalStateException("No path found");
        }
    }
}