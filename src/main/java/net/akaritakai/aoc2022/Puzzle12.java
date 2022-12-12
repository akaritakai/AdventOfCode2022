package net.akaritakai.aoc2022;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;

/**
 * In Day 12, we are finding the shortest path through a graph using Dijkstra's algorithm.
 */
public class Puzzle12 extends AbstractPuzzle {

    public Puzzle12(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 12;
    }

    @Override
    public String solvePart1() {
        var maze = new Maze();
        var alg = new BidirectionalDijkstraShortestPath<>(maze.graph);
        var path = alg.getPath(maze.end, maze.start);
        return String.valueOf(path.getLength());
    }

    @Override
    public String solvePart2() {
        var maze = new Maze();
        var paths = new DijkstraShortestPath<>(maze.graph).getPaths(maze.end);
        var min = maze.minPoints.stream()
                .map(paths::getPath)
                .filter(Objects::nonNull)
                .mapToInt(GraphPath::getLength)
                .min()
                .orElseThrow();
        return String.valueOf(min);
    }

    private final class Maze {
        private final Graph<Point, DefaultWeightedEdge> graph;
        private final Set<Point> minPoints = new HashSet<>();
        private final Point start;
        private final Point end;

        private Maze() {
            Point start = null;
            Point end = null;
            var heights = new HashMap<Point, Character>();

            // Add the vertices to the graph
            var lines = getPuzzleInput().split("\n");
            graph = new SimpleDirectedGraph<>(DefaultWeightedEdge.class);
            for (var y = 0; y < lines.length; y++) {
                for (var x = 0; x < lines[0].length(); x++) {
                    Point point = new Point(x, y);
                    switch (lines[y].charAt(x)) {
                        case 'S' -> {
                            start = point;
                            heights.put(point, 'a');
                            minPoints.add(point);
                        }
                        case 'E' -> {
                            end = point;
                            heights.put(point, 'z');
                        }
                        case 'a' -> {
                            heights.put(point, 'a');
                            minPoints.add(point);
                        }
                        default -> heights.put(point, lines[y].charAt(x));
                    }
                    graph.addVertex(point);
                }
            }

            // Add the edges to the graph
            heights.forEach((vertex, height) -> {
                var neighbors = List.of(
                        new Point(vertex.x - 1, vertex.y),
                        new Point(vertex.x + 1, vertex.y),
                        new Point(vertex.x, vertex.y - 1),
                        new Point(vertex.x, vertex.y + 1));
                neighbors.forEach(neighbor -> {
                    if (graph.containsVertex(neighbor) && height - 1 <= heights.get(neighbor)) {
                        graph.addEdge(vertex, neighbor);
                    }
                });
            });
            this.start = start;
            this.end = end;
        }
    }

    private record Point(int x, int y) {
    }
}