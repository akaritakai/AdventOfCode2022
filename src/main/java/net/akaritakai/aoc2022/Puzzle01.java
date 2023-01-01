package net.akaritakai.aoc2022;

import java.util.Arrays;
import java.util.Iterator;

/**
 * In Day 1, we're tasked with summing the numbers in n groups and finding the k largest of those groups. We do this in
 * O(n log k) time using a min-heap.
 */
public class Puzzle01 extends AbstractPuzzle {
    public Puzzle01(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var max = 0; // The most calories carried by an elf
        var it = parseInput();
        while (it.hasNext()) {
            max = Math.max(max, it.next());
        }
        return String.valueOf(max);
    }

    @Override
    public String solvePart2() {
        var heap = new int[4]; // Min heap with that holds the k-largest values
        var it = parseInput();
        while (it.hasNext()) {
            heap[0] = it.next();

            // Bubble down
            var i = 0;
            while (true) {
                var largest = i;
                var left = 2 * i + 1;
                var right = 2 * i + 2;
                if (left < heap.length && heap[left] < heap[largest]) {
                    largest = left;
                }
                if (right < heap.length && heap[right] < heap[largest]) {
                    largest = right;
                }
                if (largest == i) {
                    break;
                }
                var temp = heap[i];
                heap[i] = heap[largest];
                heap[largest] = temp;
                i = largest;
            }
        }
        return String.valueOf(heap[1] + heap[2] + heap[3]);
    }

    /**
     * Returns an iterator that for each elf yields the Calories they're carrying.
     */
    private Iterator<Integer> parseInput() {
        return Arrays.stream(getPuzzleInput().split("\n\n"))
                .map(elf -> elf.lines().mapToInt(Integer::parseInt).sum())
                .iterator();
    }
}
