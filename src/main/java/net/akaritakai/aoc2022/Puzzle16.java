package net.akaritakai.aoc2022;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * In Day 16, we are looking at calculating the accumulated flow by traversing a graph and opening valves.
 * We use the Floyd-Warshall algorithm to calculate the all-pairs shortest path to condense the graph to only nodes that
 * have valves with flow rate.
 * Then we use backtracking to find the best path through the graph, memoizing the best result for each combination of
 * valves opened.
 * In part 2, we find two disjoint combinations of valves opened that sum to the maximum flow rate which we do by
 * looping over our memoized results in O(2^(2n)).
 */
public class Puzzle16 extends AbstractPuzzle {

    public Puzzle16(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 16;
    }

    @Override
    public String solvePart1() {
        var valves = new Valves(30);
        var memo = valves.calculateFlow();
        var best = Arrays.stream(memo).max().orElseThrow();
        return String.valueOf(best);
    }

    @Override
    public String solvePart2() {
        var valves = new Valves(26);
        var memo = valves.calculateFlow();
        var condensedMemo = IntStream.range(0, (1 << valves.numValves))
                .filter(i -> memo[i] > 0)
                .mapToObj(i -> new int[]{i, memo[i]})
                .sorted((a, b) -> Integer.compare(b[1], a[1]))
                .toArray(int[][]::new);
        var best = 0;
        for (var i = 0; i <= condensedMemo.length; i++) {
            if (condensedMemo[i][1] * 2 < best) {
                break;
            }
            for (var j = i + 1; j < condensedMemo.length; j++) {
                if ((condensedMemo[i][0] & condensedMemo[j][0]) == 0) {
                    best = Math.max(best, condensedMemo[i][1] + condensedMemo[j][1]);
                }
            }
        }
        return String.valueOf(best);
    }

    private final class Valves {
        private static final Pattern PATTERN = Pattern.compile("Valve (\\S+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)");

        private final int maxTime;
        private final int numValves;
        private final int[] flowRates;
        private final int[][] distances;

        private int[] calculateFlow() {
            var memo = new int[1 << numValves];
            calculateFlow(memo, 0, 0, 0, 0, 0);
            return memo;
        }

        private void calculateFlow(int[] memo, int minute, int position, int used, int flowRate, int accumulatedFlow) {
            memo[used] = Math.max(memo[used], accumulatedFlow + (maxTime - minute) * flowRate);
            for (var i = 0; i < numValves; i++) {
                if ((used & (1 << i)) == 0) {
                    var distance = distances[position][i] + 1;
                    if (minute + distance < maxTime) {
                        calculateFlow(memo, minute + distance, i, used | (1 << i), flowRate + flowRates[i], accumulatedFlow + flowRate * distance);
                    }
                }
            }
        }

        private Valves(int maxTime) {
            this.maxTime = maxTime;

            // Read the input
            List<String> valves = new ArrayList<>();
            var flowRates = new HashMap<String, Integer>();
            Multimap<String, String> tunnelMap = MultimapBuilder.hashKeys().hashSetValues().build();
            for (var line : getPuzzleInput().split("\n")) {
                var matcher = PATTERN.matcher(line);
                if (matcher.matches()) {
                    var valve = matcher.group(1);
                    valves.add(valve);
                    var flowRate = Integer.parseInt(matcher.group(2));
                    flowRates.put(valve, flowRate);
                    var tunnels = matcher.group(3).split(", ");
                    for (var tunnel : tunnels) {
                        tunnelMap.put(valve, tunnel);
                    }
                }
            }

            // Use Floyd-Warshall to calculate the shortest distance between all pairs of valves
            var distances = new HashMap<Pair<String, String>, Integer>();
            for (var valve1 : valves) {
                for (var valve2 : valves) {
                    distances.put(Pair.of(valve1, valve2), 30);
                }
            }
            for (var entry : tunnelMap.entries()) {
                var valve1 = entry.getKey();
                var valve2 = entry.getValue();
                distances.put(Pair.of(valve1, valve2), 1);
            }
            for (var valve : valves) {
                distances.put(Pair.of(valve, valve), 0);
            }
            for (var k : valves) {
                for (var i : valves) {
                    for (var j : valves) {
                        var distance = distances.get(Pair.of(i, k)) + distances.get(Pair.of(k, j));
                        if (distances.get(Pair.of(i, j)) > distance) {
                            distances.put(Pair.of(i, j), distance);
                        }
                    }
                }
            }

            // Compile the results
            valves.removeIf(valve -> flowRates.get(valve) == 0);
            if (!valves.contains("AA")) {
                valves.add("AA");
            }
            Collections.sort(valves);
            this.numValves = valves.size();
            this.flowRates = new int[this.numValves];
            for (var i = 0; i < this.numValves; i++) {
                this.flowRates[i] = flowRates.get(valves.get(i));
            }
            this.distances = new int[this.numValves][this.numValves];
            for (String valve1 : valves) {
                for (String valve2 : valves) {
                    this.distances[valves.indexOf(valve1)][valves.indexOf(valve2)] = distances.get(Pair.of(valve1, valve2));
                }
            }
        }
    }
}