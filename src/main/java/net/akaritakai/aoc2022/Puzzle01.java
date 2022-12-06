package net.akaritakai.aoc2022;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * In Day 1, we're tasked with summing the numbers in n groups and finding the k largest of those groups. We do this in
 * O(n log k) time using a min-heap.
 */
public class Puzzle01 extends AbstractPuzzle {
    private final int[] groupSums;

    public Puzzle01(String puzzleInput) {
        super(puzzleInput);

        // Get the sum of the values for each group
        var groups = getPuzzleInput().split("\n\n");
        groupSums = new int[groups.length];
        for (var i = 0; i < groups.length; i++) {
            var group = groups[i];
            var groupLines = group.split("\n");
            for (var groupLine : groupLines) {
                groupSums[i] += Integer.parseInt(groupLine);
            }
        }
    }

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public String solvePart1() {
        var max = Arrays.stream(groupSums).max().orElseThrow();
        return String.valueOf(max);
    }

    @Override
    public String solvePart2() {
        var heap = new PriorityQueue<Integer>(4);
        for (var groupSum : groupSums) {
            heap.add(groupSum);
            if (heap.size() >= 4) {
                heap.poll();
            }
        }
        var maxSum = heap.stream().reduce(Integer::sum).orElseThrow();
        return String.valueOf(maxSum);
    }
}
