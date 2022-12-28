package net.akaritakai.aoc2022;

import java.util.*;
import java.util.stream.IntStream;

/**
 * In Day 7, we have to determine the state of a file system metadata store from a list of queries and answers that were
 * run against it.
 */
public class Puzzle07 extends AbstractPuzzle {
    public Puzzle07(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public String solvePart1() {
        var totalSize = new Input().directorySizes().filter(size -> size <= 100_000).sum();
        return String.valueOf(totalSize);
    }

    @Override
    public String solvePart2() {
        var input = new Input();

        var totalSpace = 70_000_000;
        var usedSpace = input.rootSize();
        var freeSpace = totalSpace - usedSpace;
        var spaceNeeded = 30_000_000 - freeSpace;

        var dirSize = input.directorySizes().filter(size -> size >= spaceNeeded).min().orElseThrow();
        return String.valueOf(dirSize);
    }

    private static final class Node {
        private final Node parent;
        private final Map<String, Node> children = new HashMap<>();
        private final boolean isDirectory;
        private final int size;

        private Node(Node parent) {
            this.parent = parent;
            this.isDirectory = true;
            this.size = 0;
        }

        private Node(Node parent, int size) {
            this.parent = parent;
            this.isDirectory = false;
            this.size = size;
        }
    }

    /**
     * Builds the file system metadata state from the queries and responses.
     */
    private final class Input {
        private final Node root = new Node(null);
        private final Map<Node, Integer> recursiveSizes = new HashMap<>();

        private Input() {
            var current = root;
            Scanner scanner = new Scanner(getPuzzleInput());
            var buffer = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine();
                if (!buffer.isEmpty() && line.startsWith("$")) {
                    current = processBuffer(buffer, current);
                    buffer.clear();
                }
                buffer.add(line);
            }
            processBuffer(buffer, current);
        }

        private Node processBuffer(List<String> buffer, Node current) {
            var command = buffer.get(0);
            if (command.startsWith("$ cd ")) {
                var path = command.substring(5);
                return switch (path) {
                    case "/" -> root;
                    case ".." -> current.parent;
                    default -> current.children.computeIfAbsent(path, k -> new Node(current));
                };
            } else if (command.equals("$ ls")) {
                buffer.stream().skip(1).forEach(response -> {
                    if (response.startsWith("dir ")) {
                        var path = response.substring(4);
                        current.children.computeIfAbsent(path, k -> new Node(current));
                    } else {
                        var parts = response.split(" ");
                        var size = Integer.parseInt(parts[0]);
                        var path = parts[1];
                        current.children.computeIfAbsent(path, k -> new Node(current, size));
                    }
                });
            }
            return current;
        }

        private int recursiveSize(Node node) {
            if (!node.isDirectory) {
                return node.size;
            }
            if (recursiveSizes.containsKey(node)) {
                return recursiveSizes.get(node);
            }
            var size = node.children.values().stream().mapToInt(this::recursiveSize).sum();
            recursiveSizes.put(node, size);
            return size;
        }

        private int rootSize() {
            return recursiveSize(root);
        }

        private IntStream directorySizes() {
            if (recursiveSizes.isEmpty()) {
                recursiveSize(root);
            }
            return recursiveSizes.values().stream().mapToInt(i -> i);
        }
    }
}