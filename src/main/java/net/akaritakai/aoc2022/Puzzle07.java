package net.akaritakai.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * In Day 7, we have to determine the state of a file system metadata store from a list of queries and answers that were
 * run against it.
 */
public class Puzzle07 extends AbstractPuzzle {
    public Puzzle07(String puzzleInput) {
        super(puzzleInput);
    }

    private static IntStream directorySizes(Node root) {
        var stack = new Stack<Node>();
        stack.push(root);
        var sizes = new ArrayList<Integer>();
        while (!stack.isEmpty()) {
            var node = stack.pop();
            if (node.isDirectory) {
                sizes.add(node.size);
                stack.addAll(node.children.values());
            }
        }
        return sizes.stream().mapToInt(Integer::intValue);
    }

    private static Node parseQueryAndAnswer(Node root, Node current, String queryAndAnswer) {
        var query = queryAndAnswer.lines().findFirst().orElseThrow();
        if (query.startsWith("$ cd ")) {
            var matcher = Pattern.compile("\\$ cd (\\S+).*").matcher(query);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid query: " + query);
            }
            var path = matcher.group(1);
            return switch (path) {
                case "/" -> root;
                case ".." -> current.parent;
                default -> current.children.computeIfAbsent(path, k -> new Node(current));
            };
        } else if (query.startsWith("$ ls")) {
            var answer = queryAndAnswer.lines().skip(1);
            answer.forEach(line -> {
                if (line.startsWith("dir ")) {
                    var path = line.substring(4);
                    current.children.computeIfAbsent(path, k -> new Node(current));
                } else {
                    var parts = line.split(" ");
                    var size = Integer.parseInt(parts[0]);
                    var path = parts[1];
                    if (!current.children.containsKey(path)) {
                        current.children.put(path, new Node(current, size));
                        var parent = current;
                        while (parent != null) {
                            parent.size += size;
                            parent = parent.parent;
                        }
                    }
                }
            });
        }
        return current;
    }

    @Override
    public String solvePart1() {
        var totalSize = directorySizes(buildFileTree()).filter(size -> size <= 100_000).sum();
        return String.valueOf(totalSize);
    }

    @Override
    public String solvePart2() {
        var root = buildFileTree();

        var totalSpace = 70_000_000;
        var usedSpace = root.size;
        var freeSpace = totalSpace - usedSpace;
        var spaceNeeded = 30_000_000 - freeSpace;

        var dirSize = directorySizes(root).filter(size -> size >= spaceNeeded).min().orElseThrow();
        return String.valueOf(dirSize);
    }

    private Node buildFileTree() {
        var root = new Node(null);
        var matcher = Pattern.compile("\\$[^$]+").matcher(getPuzzleInput());
        var current = root;
        while (matcher.find()) {
            current = parseQueryAndAnswer(root, current, matcher.group());
        }
        return root;
    }

    private static final class Node {
        private final Node parent;
        private final Map<String, Node> children = new HashMap<>();
        private final boolean isDirectory;
        private int size;

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