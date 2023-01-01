package net.akaritakai.aoc2022;

/**
 * In Day 3, we are comparing multiple Strings and finding the single character they have in common. We do this by
 * converting each String to a bit set representing character inclusion/exclusion and then finding the intersection of
 * all the bit sets.
 */
public class Puzzle03 extends AbstractPuzzle {
    public Puzzle03(String puzzleInput) {
        super(puzzleInput);
    }

    private static long bitSet(String s) {
        return s.chars()
                .mapToLong(c -> 1L << (c >= 'a' ? c - 'a' : c - 'A' + 26))
                .reduce(0, (a, b) -> a | b);
    }

    @Override
    public String solvePart1() {
        var sum = getPuzzleInput().lines()
                .mapToInt(rucksack -> {
                    var compartment1 = bitSet(rucksack.substring(0, rucksack.length() / 2));
                    var compartment2 = bitSet(rucksack.substring(rucksack.length() / 2));
                    return Long.numberOfTrailingZeros(compartment1 & compartment2) + 1;
                })
                .sum();
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var sum = 0;
        var rucksacks = getPuzzleInput().split("\n");
        for (var i = 0; i < rucksacks.length; i += 3) {
            var rucksack1 = bitSet(rucksacks[i]);
            var rucksack2 = bitSet(rucksacks[i + 1]);
            var rucksack3 = bitSet(rucksacks[i + 2]);
            sum += Long.numberOfTrailingZeros(rucksack1 & rucksack2 & rucksack3) + 1;
        }
        return String.valueOf(sum);
    }
}
