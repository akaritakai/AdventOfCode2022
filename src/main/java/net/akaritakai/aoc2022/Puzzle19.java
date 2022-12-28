package net.akaritakai.aoc2022;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * In Day 19, we are trying to find the maximum number of materials we can get in a certain timeframe.
 * Robots passively gather materials at a certain rate and we can spend materials and time to build more robots.
 * Solving this involves recursing the decision tree and carefully pruning non-optimal branches.
 */
public class Puzzle19 extends AbstractPuzzle {

    public Puzzle19(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 19;
    }

    @Override
    public String solvePart1() {
        var sum = Arrays.stream(parseInput())
                .map(blueprint -> new Mining(blueprint, 24))
                .mapToInt(ms -> ms.blueprint.number * ms.simulate())
                .sum();
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var results = Arrays.stream(parseInput())
                .limit(3)
                .map(blueprint -> new Mining(blueprint, 32))
                .mapToInt(Mining::simulate)
                .toArray();
        return String.valueOf(results[0] * results[1] * results[2]);
    }

    private Blueprint[] parseInput() {
        return getPuzzleInput().lines().map(Blueprint::parse).toArray(Blueprint[]::new);
    }

    @VisibleForTesting
    static final class Mining {
        private final Blueprint blueprint;
        private final int maxTime;
        private final int[] maxCosts;
        private int bestGeodes;

        @VisibleForTesting
        Mining(Blueprint blueprint, int maxTime) {
            this.blueprint = blueprint;
            this.maxTime = maxTime;
            this.maxCosts = new int[]{
                    Math.max(Math.max(blueprint.costs[0][0], blueprint.costs[1][0]), Math.max(blueprint.costs[2][0], blueprint.costs[3][0])),
                    Math.max(Math.max(blueprint.costs[0][1], blueprint.costs[1][1]), Math.max(blueprint.costs[2][1], blueprint.costs[3][1])),
                    Math.max(Math.max(blueprint.costs[0][2], blueprint.costs[1][2]), Math.max(blueprint.costs[2][2], blueprint.costs[3][2])),
                    Math.max(Math.max(blueprint.costs[0][3], blueprint.costs[1][3]), Math.max(blueprint.costs[2][3], blueprint.costs[3][3]))};
        }

        private static int buildTime(int[] costs, int[] resources, int[] robots) {
            int time = 0;
            for (int i = 0; i < 3; i++) {
                if (costs[i] == 0) continue;
                if (costs[i] > 0 && robots[i] == 0) return Integer.MAX_VALUE;
                time = Math.max(time, (costs[i] - resources[i] + robots[i] - 1) / robots[i]);
            }
            return time;
        }

        @VisibleForTesting
        int simulate() {
            bestGeodes = 0;
            simulate(new int[4], new int[]{1, 0, 0, 0}, maxTime);
            return bestGeodes;
        }

        private int simulate(int[] resources, int[] robots, int remaining) {
            // Prune if we cannot mine more geodes than our current best (i.e. if we constantly made geode robots)
            int maxGeodes = resources[3] + remaining * robots[3] + (remaining * (remaining - 1)) / 2;
            if (maxGeodes <= bestGeodes) return 0;

            // Prune if we are out of time
            if (remaining == 0) {
                return resources[3];
            }

            // Make a geode robot if we have the resources right now
            if (resources[0] >= blueprint.costs[3][0] && resources[2] >= blueprint.costs[3][2]) {
                int[] nextResources = resources.clone();
                int[] nextRobots = robots.clone();
                for (int j = 0; j < 4; j++) nextResources[j] += robots[j] - blueprint.costs[3][j];
                nextRobots[3]++;
                return simulate(nextResources, nextRobots, remaining - 1);
            }

            int best = resources[3] + robots[3] * remaining; // The most geodes we can get if we do nothing
            for (int i = 3; i >= 0; i--) {
                // Prune if we would not benefit from making the robot type
                if (i < 3 && robots[i] >= maxCosts[i]) continue;

                // Prune if our time will expire before we can gather enough resources to make the robot type
                int delay = buildTime(blueprint.costs[i], resources, robots) + 1;
                if (delay > remaining - 1) continue;

                // Recurse on building the robot type
                int[] nextResources = resources.clone();
                int[] nextRobots = robots.clone();
                for (int j = 0; j < 4; j++) nextResources[j] += robots[j] * delay - blueprint.costs[i][j];
                nextRobots[i]++;
                best = Math.max(best, simulate(nextResources, nextRobots, remaining - delay));
            }
            bestGeodes = Math.max(bestGeodes, best);
            return best;
        }
    }

    @VisibleForTesting
    record Blueprint(int number, int[][] costs) {
        private static final Pattern PATTERN = Pattern.compile("Blueprint (\\d+): "
                + "Each ore robot costs (\\d+) ore. "
                + "Each clay robot costs (\\d+) ore. "
                + "Each obsidian robot costs (\\d+) ore and (\\d+) clay. "
                + "Each geode robot costs (\\d+) ore and (\\d+) obsidian.");

        private static Blueprint parse(String line) {
            var matcher = PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid blueprint: " + line);
            }
            return new Blueprint(
                    Integer.parseInt(matcher.group(1)),
                    new int[][]{
                            {Integer.parseInt(matcher.group(2)), 0, 0, 0},
                            {Integer.parseInt(matcher.group(3)), 0, 0, 0},
                            {Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), 0, 0},
                            {Integer.parseInt(matcher.group(6)), 0, Integer.parseInt(matcher.group(7)), 0}
                    });
        }
    }
}