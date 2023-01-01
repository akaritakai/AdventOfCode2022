package net.akaritakai.aoc2022;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In Day 18, we are calculating the surface area of surfaces in a 3D grid made of 1x1x1 cubes.
 * In part 1, we count the number of exposed surfaces.
 * In part 2, we find the bounding box for the surfaces and flood fill the interior, counting cube faces we encounter.
 */
public class Puzzle18 extends AbstractPuzzle {

    public Puzzle18(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var cubes = parseInput();
        var area = cubes.stream()
                .flatMap(cube -> Stream.of(
                        new Point(cube.x, cube.y, cube.z + 1),
                        new Point(cube.x, cube.y, cube.z - 1),
                        new Point(cube.x, cube.y + 1, cube.z),
                        new Point(cube.x, cube.y - 1, cube.z),
                        new Point(cube.x + 1, cube.y, cube.z),
                        new Point(cube.x - 1, cube.y, cube.z)))
                .filter(cube -> !cubes.contains(cube))
                .count();
        return String.valueOf(area);
    }

    @Override
    public String solvePart2() {
        var cubes = parseInput();
        var area = 0;

        var minX = cubes.stream().mapToInt(cube -> cube.x).min().orElseThrow() - 1;
        var maxX = cubes.stream().mapToInt(cube -> cube.x).max().orElseThrow() + 1;
        var minY = cubes.stream().mapToInt(cube -> cube.y).min().orElseThrow() - 1;
        var maxY = cubes.stream().mapToInt(cube -> cube.y).max().orElseThrow() + 1;
        var minZ = cubes.stream().mapToInt(cube -> cube.z).min().orElseThrow() - 1;
        var maxZ = cubes.stream().mapToInt(cube -> cube.z).max().orElseThrow() + 1;

        var seen = new HashSet<Point>();
        var stack = new Stack<Point>();
        stack.push(new Point(minX, minY, minZ));
        while (!stack.isEmpty()) {
            var cube = stack.pop();
            if (!seen.add(cube)) {
                continue; // Already processed this point
            }
            var up = new Point(cube.x, cube.y, cube.z + 1);
            if (up.z <= maxZ) {
                if (cubes.contains(up)) {
                    area++;
                } else {
                    stack.push(up);
                }
            }
            var down = new Point(cube.x, cube.y, cube.z - 1);
            if (down.z >= minZ) {
                if (cubes.contains(down)) {
                    area++;
                } else {
                    stack.push(down);
                }
            }
            var front = new Point(cube.x, cube.y + 1, cube.z);
            if (front.y <= maxY) {
                if (cubes.contains(front)) {
                    area++;
                } else {
                    stack.push(front);
                }
            }
            var back = new Point(cube.x, cube.y - 1, cube.z);
            if (back.y >= minY) {
                if (cubes.contains(back)) {
                    area++;
                } else {
                    stack.push(back);
                }
            }
            var right = new Point(cube.x + 1, cube.y, cube.z);
            if (right.x <= maxX) {
                if (cubes.contains(right)) {
                    area++;
                } else {
                    stack.push(right);
                }
            }
            var left = new Point(cube.x - 1, cube.y, cube.z);
            if (left.x >= minX) {
                if (cubes.contains(left)) {
                    area++;
                } else {
                    stack.push(left);
                }
            }
        }
        return String.valueOf(area);
    }

    private Set<Point> parseInput() {
        return getPuzzleInput()
                .lines()
                .map(line -> {
                    var parts = line.split(",");
                    return new Point(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]));
                })
                .collect(Collectors.toSet());
    }

    private record Point(int x, int y, int z) {
    }
}