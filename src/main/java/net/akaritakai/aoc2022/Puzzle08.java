package net.akaritakai.aoc2022;

import java.util.Arrays;
import java.util.Stack;

/**
 * In Day 8, we're looking to find the visibility within a forest of trees of various heights. We can calculate heights
 * within the forest by processing the visibility directions using a monotonic stack in O(m*n).
 */
public class Puzzle08 extends AbstractPuzzle {

    public Puzzle08(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var grid = parseInput();
        var numRows = grid.length;
        var numCols = grid[0].length;
        var visible = new boolean[numRows][numCols];

        for (var row = 0; row < numRows; row++) {
            // Visible from left edge
            var height = -1;
            for (var col = 0; col < numCols; col++) {
                if (grid[row][col] > height) {
                    visible[row][col] = true;
                    height = grid[row][col];
                }
            }
            // Visible from right edge
            height = -1;
            for (var col = numCols - 1; col >= 0; col--) {
                if (grid[row][col] > height) {
                    visible[row][col] = true;
                    height = grid[row][col];
                }
            }
        }

        for (var col = 0; col < numCols; col++) {
            // Visible from top edge
            var height = -1;
            for (var row = 0; row < numRows; row++) {
                if (grid[row][col] > height) {
                    visible[row][col] = true;
                    height = grid[row][col];
                }
            }
            // Visible from bottom edge
            height = -1;
            for (var row = numRows - 1; row >= 0; row--) {
                if (grid[row][col] > height) {
                    visible[row][col] = true;
                    height = grid[row][col];
                }
            }
        }

        // Count the number of visible trees
        var numVisible = 0;
        for (var row = 0; row < numRows; row++) {
            for (var col = 0; col < numCols; col++) {
                if (visible[row][col]) {
                    numVisible++;
                }
            }
        }
        return String.valueOf(numVisible);
    }

    @Override
    public String solvePart2() {
        var grid = parseInput();
        var numRows = grid.length;
        var numCols = grid[0].length;
        var score = new int[numRows][numCols];

        // Populate the initial score grid with the multiplicative identity
        for (var row = 0; row < numRows; row++) {
            Arrays.fill(score[row], 1);
        }

        Stack<Integer> stack = new Stack<>();
        for (var row = 0; row < numRows; row++) {
            // Add right-facing visibility to the score using a monotonic stack
            for (var col = 0; col < numCols; col++) {
                while (!stack.isEmpty() && grid[row][col] >= grid[row][stack.peek()]) {
                    var i = stack.pop();
                    score[row][i] *= col - i;
                }
                stack.push(col);
            }
            while (!stack.isEmpty()) {
                var i = stack.pop();
                score[row][i] *= numCols - i - 1;
            }
            // Add left-facing visibility to the score using a monotonic stack
            for (var col = numCols - 1; col >= 0; col--) {
                while (!stack.isEmpty() && grid[row][col] >= grid[row][stack.peek()]) {
                    var i = stack.pop();
                    score[row][i] *= i - col;
                }
                stack.push(col);
            }
            while (!stack.isEmpty()) {
                var i = stack.pop();
                score[row][i] *= i;
            }
        }

        for (var col = 0; col < numCols; col++) {
            // Add down-facing visibility to the score using a monotonic stack
            for (var row = 0; row < numRows; row++) {
                while (!stack.isEmpty() && grid[row][col] >= grid[stack.peek()][col]) {
                    var i = stack.pop();
                    score[i][col] *= row - i;
                }
                stack.push(row);
            }
            while (!stack.isEmpty()) {
                var i = stack.pop();
                score[i][col] *= numRows - i - 1;
            }
            // Add up-facing visibility to the score using a monotonic stack
            for (var row = numRows - 1; row >= 0; row--) {
                while (!stack.isEmpty() && grid[row][col] >= grid[stack.peek()][col]) {
                    var i = stack.pop();
                    score[i][col] *= i - row;
                }
                stack.push(row);
            }
            while (!stack.isEmpty()) {
                var i = stack.pop();
                score[i][col] *= i;
            }
        }

        // Find the largest score
        var maxScore = Arrays.stream(score)
                .flatMapToInt(Arrays::stream)
                .max()
                .orElseThrow();
        return String.valueOf(maxScore);
    }

    private int[][] parseInput() {
        return getPuzzleInput().lines()
                .map(line -> line.chars().map(c -> c - '0').toArray())
                .toArray(int[][]::new);
    }
}