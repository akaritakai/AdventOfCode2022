package net.akaritakai.aoc2022;

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
    public int getDay() {
        return 1;
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
        return new Iterator<>() {
            final String input = getPuzzleInput();
            int i = 0;

            @Override
            public Integer next() {
                var sum = 0;
                while (hasNext()) {
                    var c = input.charAt(i++);
                    if (c == '\n') break; // End of group
                    var n = 0;
                    do {
                        n = (10 * n) + (c - '0');
                        c = input.charAt(i++);
                    } while (c != '\n'); // End of line
                    sum += n;
                }
                return sum;
            }

            @Override
            public boolean hasNext() {
                return i < input.length();
            }
        };
    }
}
