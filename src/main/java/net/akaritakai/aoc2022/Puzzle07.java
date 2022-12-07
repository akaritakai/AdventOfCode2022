package net.akaritakai.aoc2022;

import java.util.HashMap;
import java.util.Map;

/**
 * In Day 7, we have to determine the state of a file system metadata store from a list of queries and answers that were
 * run against it.
 */
public class Puzzle07 extends AbstractPuzzle {
    private final Node root = new Node(null);

    public Puzzle07(String puzzleInput) {
        super(puzzleInput);

        // Parse the input
        var node = root;
        var blocks = getPuzzleInput().split("\\$");
        for (var block : blocks) {
            var lines = block.split("\n");
            var command = lines[0].trim();
            if (command.equals("cd /")) {
                node = root;
            } else if (command.equals("cd ..")) {
                node = node.parent;
            } else if (command.startsWith("cd ")) {
                var name = command.substring(3);
                final Node n = node;
                node = node.children.computeIfAbsent(name, k -> new Node(n));
            } else if (command.equals("ls")) {
                for (var i = 1; i < lines.length; i++) {
                    var line = lines[i].trim();
                    if (line.startsWith("dir ")) {
                        var name = line.substring(4);
                        final Node n = node;
                        node.children.computeIfAbsent(name, k -> new Node(n));
                    } else if (!line.isEmpty()) {
                        var parts = line.split(" ");
                        var size = Integer.parseInt(parts[0]);
                        var name = parts[1];
                        final Node n = node;
                        node.children.computeIfAbsent(name, k -> new Node(n, size));
                    }
                }
            }
        }
    }

    @Override
    public int getDay() {
        return 7;
    }
    @Override
    public String solvePart1() {
        var dirSizes = new HashMap<Node, Integer>();
        recursiveSize(dirSizes, root);
        var totalSize = dirSizes.values().stream()
                .filter(size -> size <= 100_000)
                .mapToInt(Integer::intValue)
                .sum();
        return String.valueOf(totalSize);
    }

    @Override
    public String solvePart2() {
        var dirSizes = new HashMap<Node, Integer>();

        var totalSpace = 70_000_000;
        var usedSpace = recursiveSize(dirSizes, root);
        var freeSpace = totalSpace - usedSpace;
        var spaceNeeded = 30_000_000 - freeSpace;

        var dirSize = dirSizes.values().stream()
                .filter(size -> size >= spaceNeeded)
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow();
        return String.valueOf(dirSize);
    }

    private int recursiveSize(Map<Node, Integer> dirMemo, Node node) {
        if (!node.isDirectory) {
            return node.size;
        }
        if (dirMemo.containsKey(node)) {
            return dirMemo.get(node);
        }
        var size = node.children.values().stream()
                .mapToInt(child -> recursiveSize(dirMemo, child))
                .sum();
        dirMemo.put(node, size);
        return size;
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
}