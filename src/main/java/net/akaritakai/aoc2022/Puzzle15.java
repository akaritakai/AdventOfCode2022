package net.akaritakai.aoc2022;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import org.jetbrains.annotations.NotNull;
import org.jheaps.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * In Day 15, we are trying to find a beacon using some sensor readings.
 * In part 1, we are trying to find points in a line that cannot be the beacon by merging and splitting intervals.
 * In part 2, we are trying to find the dead zone in our sensor network (this is where the beacon is). We solve it using
 * a series of integer SAT constraints.
 */
public class Puzzle15 extends AbstractPuzzle {

    public Puzzle15(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 15;
    }

    @Override
    public String solvePart1() {
        var tunnels = new Tunnels(getPuzzleInput());
        return String.valueOf(tunnels.nonBeaconsOnRow(2_000_000));
    }

    @Override
    public String solvePart2() {
        var tunnels = new Tunnels(getPuzzleInput());
        return String.valueOf(tunnels.findFrequency(4_000_000));
    }

    @VisibleForTesting
    static final class Tunnels {
        private final List<SensorAndBeacon> pairs = new ArrayList<>();

        Tunnels(String input) {
            var pattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
            for (var line : input.split("\n")) {
                var matcher = pattern.matcher(line);
                while (matcher.find()) {
                    var sensor = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                    var beacon = new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                    pairs.add(new SensorAndBeacon(sensor, beacon));
                }
            }
        }

        long nonBeaconsOnRow(int row) {
            var ranges = new TreeSet<Range>();

            // Add ranges of points that each sensor scanned on this line
            for (var pair : pairs) {
                var maxDistance = distance(pair.sensor, pair.beacon);
                var dy = Math.abs(row - pair.sensor.y);
                if (dy <= maxDistance) {
                    var dx = maxDistance - dy;
                    var range = new Range(pair.sensor.x - dx, pair.sensor.x + dx);
                    var floor = ranges.floor(range);
                    if (floor != null && floor.end >= range.start) {
                        ranges.remove(floor);
                        range = new Range(Math.min(floor.start, range.start), Math.max(floor.end, range.end));
                    }
                    var ceiling = ranges.ceiling(range);
                    if (ceiling != null && ceiling.start <= range.end) {
                        ranges.remove(ceiling);
                        range = new Range(Math.min(ceiling.start, range.start), Math.max(ceiling.end, range.end));
                    }
                    ranges.add(range);
                }
            }

            // Remove beacons from the ranges
            for (var pair : pairs) {
                if (pair.beacon.y == row) {
                    var range = new Range(pair.beacon.x, pair.beacon.x);
                    var floor = ranges.floor(range);
                    if (floor != null && floor.end >= range.start) {
                        ranges.remove(floor);
                        if (floor.start < range.start) {
                            ranges.add(new Range(floor.start, range.start - 1));
                        }
                        if (floor.end > range.end) {
                            ranges.add(new Range(range.end + 1, floor.end));
                        }
                    }
                }
            }

            // Return the size of the range
            return ranges.stream().mapToInt(range -> range.end - range.start + 1).sum();
        }

        @SuppressWarnings("unchecked")
        long findFrequency(int maxValue) {
            try (var context = new Context()) {
                var solver = context.mkSolver();

                // Define the point we want to find (x, y)
                var x = context.mkIntConst("x");
                var y = context.mkIntConst("y");

                // (x, y) must be between [0, maxValue]
                solver.add(context.mkGe(x, context.mkInt(0)));
                solver.add(context.mkLe(y, context.mkInt(maxValue)));
                solver.add(context.mkGe(y, context.mkInt(0)));
                solver.add(context.mkLe(y, context.mkInt(maxValue)));

                // For every sensor/beacon pair, (x, y) must be further away from the sensor than the beacon.
                for (var pair : pairs) {
                    var sensor = pair.sensor;
                    var beacon = pair.beacon;
                    var distance = mkDist(context, x, y, context.mkInt(sensor.x), context.mkInt(sensor.y));
                    var maxDistance = context.mkInt(distance(sensor, beacon));
                    solver.add(context.mkGt(distance, maxDistance));
                }

                // (x, y) has to be surrounded by excluded points.
                for (var d : new int[][] { {-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                    var x1 = context.mkAdd(x,  context.mkInt(d[0]));
                    var y1 = context.mkAdd(y, context.mkInt(d[1]));
                    var elements = pairs.stream()
                            .map(pair -> {
                                var distance1 = mkDist(context,
                                        x1, y1,
                                        context.mkInt(pair.sensor.x), context.mkInt(pair.sensor.y));
                                var distance2 = mkDist(context,
                                        context.mkInt(pair.beacon.x), context.mkInt(pair.beacon.y),
                                        context.mkInt(pair.sensor.x), context.mkInt(pair.sensor.y));
                                return context.mkLe(distance1, distance2);
                            })
                            .toArray(BoolExpr[]::new);
                    solver.add(context.mkOr(elements));
                }

                // Solve for the point and return the frequency
                solver.check();
                var a = Long.parseLong(solver.getModel().eval(x, false).toString());
                var b = Long.parseLong(solver.getModel().eval(y, false).toString());
                return (4_000_000 * a) + b;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static ArithExpr mkDist(Context context, ArithExpr x1, ArithExpr y1, ArithExpr x2, ArithExpr y2) {
        var dx = mkAbs(context, context.mkSub(x1, x2));
        var dy = mkAbs(context, context.mkSub(y1, y2));
        return context.mkAdd(dx, dy);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ArithExpr mkAbs(Context context, ArithExpr expr) {
        var zero = context.mkInt(0);
        return (ArithExpr) context.mkITE(context.mkGe(expr, zero), expr, context.mkSub(zero, expr));
    }

    private static int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private record SensorAndBeacon(Point sensor, Point beacon) {
    }

    private record Point(int x, int y) {
    }

    private record Range(int start, int end) implements Comparable<Range> {
        @Override
        public int compareTo(@NotNull Range other) {
            return Integer.compare(start, other.start);
        }
    }
}