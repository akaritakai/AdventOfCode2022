package net.akaritakai.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * In Day 22, we are traversing a maze via a series of instructions. The maze can wrap around (in part 1 by wrapping in
 * the row/column direction and in part 2 by being part of a cube).
 * In part 2, everyone's input had the same cube size and arrangement -- just a different maze, so we hardcode the
 * transitions between faces.
 */
public class Puzzle22 extends AbstractPuzzle {

    // Instructions for how to move along the path
    // Values less than 0 are special: -1 indicates a left turn and -2 indicates a right turn
    private static final int LEFT_INSTRUCTION = -1;
    private static final int RIGHT_INSTRUCTION = -2;

    public Puzzle22(String puzzleInput) {
        super(puzzleInput);
    }

    private static Point findOrigin(char[][] maze) {
        for (var col = 0; col < maze[0].length; col++) {
            if (maze[0][col] == '.') {
                return new Point(0, col);
            }
        }
        throw new IllegalArgumentException("No origin found");
    }

    @Override
    public String solvePart1() {
        char[][] grid = parseGrid();
        var numRows = grid.length;
        var numCols = grid[0].length;

        // Calculate edges for wrapping
        var leftEdges = new int[numRows]; // The leftmost column for each row
        var rightEdges = new int[numRows]; // The rightmost column for each row
        for (var row = 0; row < numRows; row++) {
            for (var col = 0; col < numCols; col++) {
                if (grid[row][col] != ' ') {
                    leftEdges[row] = col;
                    break;
                }
            }
            for (var col = numCols - 1; col >= 0; col--) {
                if (grid[row][col] != ' ') {
                    rightEdges[row] = col;
                    break;
                }
            }
        }
        var topEdges = new int[numCols]; // The topmost row for each column
        var bottomEdges = new int[numCols]; // The bottommost row for each column
        for (var col = 0; col < numCols; col++) {
            for (var row = 0; row < numRows; row++) {
                if (grid[row][col] != ' ') {
                    topEdges[col] = row;
                    break;
                }
            }
            for (var row = numRows - 1; row >= 0; row--) {
                if (grid[row][col] != ' ') {
                    bottomEdges[col] = row;
                    break;
                }
            }
        }

        // Create the move function
        var moveFn = new MoveFn() {
            @Override
            public Optional<Pair<Point, Direction>> move(Point point, Direction direction) {
                var row = point.row();
                var col = point.col();
                switch (direction) {
                    case UP -> {
                        row--;
                        if (row < 0 || grid[row][col] == ' ') {
                            row = bottomEdges[col];
                        }
                    }
                    case DOWN -> {
                        row++;
                        if (row >= numRows || grid[row][col] == ' ') {
                            row = topEdges[col];
                        }
                    }
                    case LEFT -> {
                        col--;
                        if (col < 0 || grid[row][col] == ' ') {
                            col = rightEdges[row];
                        }
                    }
                    case RIGHT -> {
                        col++;
                        if (col >= numCols || grid[row][col] == ' ') {
                            col = leftEdges[row];
                        }
                    }
                }
                if (grid[row][col] == '#') {
                    return Optional.empty();
                } else {
                    return Optional.of(Pair.of(new Point(row, col), direction));
                }
            }
        };
        return String.valueOf(findPassword(moveFn, findOrigin(grid)));
    }

    @Override
    public String solvePart2() {
        var grid = parseGrid();

        // Create the move function
        var moveFn = new MoveFn() {
            private static final Point FRONT = new Point(0, 1);
            private static final Point RIGHT = new Point(0, 2);
            private static final Point DOWN = new Point(1, 1);
            private static final Point LEFT = new Point(2, 0);
            private static final Point BACK = new Point(2, 1);
            private static final Point UP = new Point(3, 0);

            @Override
            public Optional<Pair<Point, Direction>> move(Point point, Direction direction) {
                // Our face
                Point face = new Point(point.row / 50, point.col / 50);

                // Our position on the face
                var innerRow = point.row() % 50;
                var innerCol = point.col() % 50;

                // Determine our new position wrapping
                if (innerRow == 0 && direction == Direction.UP) {
                    if (face.equals(FRONT)) {
                        point = new Point(UP.row * 50 + innerCol, UP.col * 50);
                        direction = Direction.RIGHT;
                    } else if (face.equals(RIGHT)) {
                        point = new Point(UP.row * 50 + 49, UP.col * 50 + innerCol);
                    } else if (face.equals(DOWN)) {
                        point = new Point(FRONT.row * 50 + 49, FRONT.col * 50 + innerCol);
                    } else if (face.equals(LEFT)) {
                        point = new Point(DOWN.row * 50 + innerCol, DOWN.col * 50);
                        direction = Direction.RIGHT;
                    } else if (face.equals(BACK)) {
                        point = new Point(DOWN.row * 50 + 49, DOWN.col * 50 + innerCol);
                    } else if (face.equals(UP)) {
                        point = new Point(LEFT.row * 50 + 49, LEFT.col * 50 + innerCol);
                    }
                } else if (innerRow == 49 && direction == Direction.DOWN) {
                    if (face.equals(FRONT)) {
                        point = new Point(DOWN.row * 50, DOWN.col * 50 + innerCol);
                    } else if (face.equals(RIGHT)) {
                        point = new Point(DOWN.row * 50 + innerCol, DOWN.col * 50 + 49);
                        direction = Direction.LEFT;
                    } else if (face.equals(DOWN)) {
                        point = new Point(BACK.row * 50, BACK.col * 50 + innerCol);
                    } else if (face.equals(LEFT)) {
                        point = new Point(UP.row * 50, UP.col * 50 + innerCol);
                    } else if (face.equals(BACK)) {
                        point = new Point(UP.row * 50 + innerCol, UP.col * 50 + 49);
                        direction = Direction.LEFT;
                    } else if (face.equals(UP)) {
                        point = new Point(RIGHT.row * 50, RIGHT.col * 50 + innerCol);
                    }
                } else if (innerCol == 0 && direction == Direction.LEFT) {
                    if (face.equals(FRONT)) {
                        point = new Point(LEFT.row * 50 + (49 - innerRow), LEFT.col * 50);
                        direction = Direction.RIGHT;
                    } else if (face.equals(RIGHT)) {
                        point = new Point(FRONT.row * 50 + innerRow, FRONT.col * 50 + 49);
                    } else if (face.equals(DOWN)) {
                        point = new Point(LEFT.row * 50, LEFT.col * 50 + innerRow);
                        direction = Direction.DOWN;
                    } else if (face.equals(LEFT)) {
                        point = new Point(FRONT.row * 50 + (49 - innerRow), FRONT.col * 50);
                        direction = Direction.RIGHT;
                    } else if (face.equals(BACK)) {
                        point = new Point(LEFT.row * 50 + innerRow, LEFT.col * 50 + 49);
                    } else if (face.equals(UP)) {
                        point = new Point(FRONT.row * 50, FRONT.col * 50 + innerRow);
                        direction = Direction.DOWN;
                    }
                } else if (innerCol == 49 && direction == Direction.RIGHT) {
                    if (face.equals(FRONT)) {
                        point = new Point(RIGHT.row * 50 + innerRow, RIGHT.col * 50);
                    } else if (face.equals(RIGHT)) {
                        point = new Point(BACK.row * 50 + (49 - innerRow), BACK.col * 50 + 49);
                        direction = Direction.LEFT;
                    } else if (face.equals(DOWN)) {
                        point = new Point(RIGHT.row * 50 + 49, RIGHT.col * 50 + innerRow);
                        direction = Direction.UP;
                    } else if (face.equals(LEFT)) {
                        point = new Point(BACK.row * 50 + innerRow, BACK.col * 50);
                    } else if (face.equals(BACK)) {
                        point = new Point(RIGHT.row * 50 + (49 - innerRow), RIGHT.col * 50 + 49);
                        direction = Direction.LEFT;
                    } else if (face.equals(UP)) {
                        point = new Point(BACK.row * 50 + 49, BACK.col * 50 + innerRow);
                        direction = Direction.UP;
                    }
                } else {
                    point = point.move(direction);
                }
                if (grid[point.row][point.col] == '#') {
                    return Optional.empty();
                } else {
                    return Optional.of(Pair.of(point, direction));
                }
            }
        };
        return String.valueOf(findPassword(moveFn, findOrigin(grid)));
    }

    private long findPassword(MoveFn moveFn, Point point) {
        var direction = Direction.RIGHT;
        for (var instruction : parseInstructions()) {
            if (instruction == LEFT_INSTRUCTION) {
                direction = direction.turnLeft();
            } else if (instruction == RIGHT_INSTRUCTION) {
                direction = direction.turnRight();
            } else {
                for (var i = 0; i < instruction; i++) {
                    var move = moveFn.move(point, direction);
                    if (move.isEmpty()) break;
                    point = move.get().getLeft();
                    direction = move.get().getRight();
                }
            }
        }
        return 1000L * (point.row + 1) + 4L * (point.col + 1) + direction.ordinal();
    }

    // Parses the maze into a 2D array of characters.
    private char[][] parseGrid() {
        var rowLines = getPuzzleInput().split("\n\n")[0].split("\n");
        var numRows = rowLines.length;
        var numCols = Arrays.stream(rowLines).mapToInt(String::length).max().orElseThrow();
        var maze = new char[numRows][numCols];
        for (var row = 0; row < numRows; row++) {
            var line = rowLines[row];
            var col = 0;
            while (col < line.length()) {
                maze[row][col] = line.charAt(col);
                col++;
            }
            while (col < numCols) {
                maze[row][col] = ' ';
                col++;
            }
        }
        return maze;
    }

    private List<Integer> parseInstructions() {
        var instructions = new ArrayList<Integer>();
        var matcher = Pattern.compile("(\\d+|[LR])").matcher(getPuzzleInput().split("\n\n")[1]);
        while (matcher.find()) {
            switch (matcher.group(1)) {
                case "L" -> instructions.add(LEFT_INSTRUCTION);
                case "R" -> instructions.add(RIGHT_INSTRUCTION);
                default -> instructions.add(Integer.parseInt(matcher.group(1)));
            }
        }
        return instructions;
    }

    private enum Direction {
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1),
        UP(-1, 0);

        private final int row;
        private final int col;

        Direction(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Direction turnRight() {
            return switch (this) {
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case UP -> RIGHT;
            };
        }

        Direction turnLeft() {
            return switch (this) {
                case RIGHT -> UP;
                case DOWN -> RIGHT;
                case LEFT -> DOWN;
                case UP -> LEFT;
            };
        }
    }

    private interface MoveFn {
        Optional<Pair<Point, Direction>> move(Point point, Direction direction);
    }

    private record Point(int row, int col) {
        Point move(Direction direction) {
            return new Point(row + direction.row, col + direction.col);
        }
    }
}