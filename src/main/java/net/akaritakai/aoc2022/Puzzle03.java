package net.akaritakai.aoc2022;

import java.util.BitSet;

/**
 * In Day 3, we are comparing multiple Strings and finding the single character they have in common. We do this by
 * converting each String to a BitSet representing character inclusion/exclusion and then finding the intersection of
 * all the BitSets.
 */
public class Puzzle03 extends AbstractPuzzle {
    public Puzzle03(String puzzleInput) {
        super(puzzleInput);
    }

    private static int findCommonBit(String... strings) {
        var bitSet = toBitSet(strings[0]);
        for (var i = 1; i < strings.length; i++) {
            bitSet.and(toBitSet(strings[i]));
        }
        return bitSet.nextSetBit(0) + 1;
    }

    private static BitSet toBitSet(String s) {
        var bitSet = new BitSet(52);
        s.chars().forEach(c -> {
            if (c >= 'a' && c <= 'z') {
                bitSet.set(c - 'a');
            } else if (c >= 'A' && c <= 'Z') {
                bitSet.set(c - 'A' + 26);
            }
        });
        return bitSet;
    }

    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public String solvePart1() {
        var sum = getPuzzleInput().lines()
                .mapToInt(rucksack -> {
                    var compartment1 = rucksack.substring(0, rucksack.length() / 2);
                    var compartment2 = rucksack.substring(rucksack.length() / 2);
                    return findCommonBit(compartment1, compartment2);
                })
                .sum();
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var sum = 0;
        var rucksacks = getPuzzleInput().split("\n");
        for (var i = 0; i < rucksacks.length; i += 3) {
            sum += findCommonBit(rucksacks[i], rucksacks[i + 1], rucksacks[i + 2]);
        }
        return String.valueOf(sum);
    }
}
