import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by A. Joz on 12/31/2015.
 * <p>
 * --- Day 18: Like a GIF For Your Yard ---
 * <p>
 * After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed.
 * You arrange them in a 100x100 grid.
 * <p>
 * Never one to let you down, Santa again mails you instructions on the ideal lighting configuration.
 * With so few lights, he says, you'll have to resort to animation.
 * <p>
 * Start by setting your lights to the included initial configuration (your puzzle input).
 * A # means "on", and a . means "off".
 * <p>
 * Then, animate your grid in steps, where each step decides the next configuration based on the current one.
 * Each light's next state (either on or off) depends on its current state and the current states
 * of the eight lights adjacent to it (including diagonals).
 * Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".
 * <p>
 * For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8,
 * and the light marked B, which is on an edge, only has the neighbors marked 1 through 5:
 * <p>
 * 1B5...
 * 234...
 * ......
 * ..123.
 * ..8A4.
 * ..765.
 * <p>
 * The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:
 * <p>
 * A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
 * A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
 * All of the lights update simultaneously; they all consider the same current state before moving to the next.
 * <p>
 * Here's a few steps from an example configuration of another 6x6 grid:
 * <p>
 * Initial state:
 * .#.#.#
 * ...##.
 * #....#
 * ..#...
 * #.#..#
 * ####..
 * <p>
 * After 1 step:
 * ..##..
 * ..##.#
 * ...##.
 * ......
 * #.....
 * #.##..
 * <p>
 * After 2 steps:
 * ..###.
 * ......
 * ..###.
 * ......
 * .#....
 * .#....
 * <p>
 * After 3 steps:
 * ...#..
 * ......
 * ...#..
 * ..##..
 * ......
 * ......
 * <p>
 * After 4 steps:
 * ......
 * ......
 * ..##..
 * ..##..
 * ......
 * ......
 * After 4 steps, this example has four lights on.
 * <p>
 * In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
 * <p>
 * --- Part Two ---
 * <p>
 * You flip the instructions over; Santa goes on to point out that this is all just an implementation of Conway's Game of Life.
 * At least, it was, until you notice that something's wrong with the grid of lights you bought: four lights,
 * one in each corner, are stuck on and can't be turned off.
 * The example above will actually run like this:
 * <p>
 * Initial state:
 * ##.#.#
 * ...##.
 * #....#
 * ..#...
 * #.#..#
 * ####.#
 * <p>
 * After 1 step:
 * #.##.#
 * ####.#
 * ...##.
 * ......
 * #...#.
 * #.####
 * <p>
 * After 2 steps:
 * #..#.#
 * #....#
 * .#.##.
 * ...##.
 * .#..##
 * ##.###
 * <p>
 * After 3 steps:
 * #...##
 * ####.#
 * ..##.#
 * ......
 * ##....
 * ####.#
 * <p>
 * After 4 steps:
 * #.####
 * #....#
 * ...#..
 * .##...
 * #.....
 * #.#..#
 * <p>
 * After 5 steps:
 * ##.###
 * .##..#
 * .##...
 * .##...
 * #.#...
 * ##...#
 * After 5 steps, this example now has 17 lights on.
 * <p>
 * In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the on state,
 * how many lights are on after 100 steps?
 */

public class Day18 {
    private static boolean[][] grid = new boolean[102][102];

    public static void main(String[] args) {
        // change to 2 for second part =)
        int part = 1;

        // fill grid with init data
        initGrid(args);

        // steps
        animate(100, part);

        //answer:
        int sum = countOn();
        System.out.println(
                "Part " + part + " : " + sum
        );
    }

    // returns number of "true" elements in grid
    private static int countOn() {
        int sum = 0;
        for (boolean[] booleen : grid) {
            for (boolean b : booleen) {
                if (b) sum++;
            }
        }
        return sum;
    }

    // animates grid for "i" steps
    private static void animate(int i, int part) {
        for (int k = 0; k < i; k++) {
            // clone grid, to save state, while led is changing
            final boolean[][] tempGrid = Arrays.stream(grid)
                    .map(boolean[]::clone)
                    .toArray(boolean[][]::new);
            int last = grid.length - 1;
            for (int x = 1; x < last; x++) {
                for (int y = 1; y < last; y++) {
                    // hold corner values unchanged for part2
                    if (part == 2) {
                        if ((x == 1 && y == 1) ||
                                (x == last - 1 && y == 1) ||
                                (x == 1 && y == last - 1) ||
                                (x == last - 1 && y == last - 1)) continue;
                    }
                    grid[x][y] = getNewState(x, y, tempGrid);
                }
            }
        }


    }

    // returns new state depends on 8 neighbors state
    private static boolean getNewState(int i, int j, boolean[][] tempGrid) {
        final boolean ledIsOn = tempGrid[i][j];

        final List<Boolean> neighbours = new ArrayList<>();
        neighbours.add(tempGrid[i - 1][j - 1]);
        neighbours.add(tempGrid[i - 1][j]);
        neighbours.add(tempGrid[i - 1][j + 1]);

        neighbours.add(tempGrid[i][j - 1]);
        neighbours.add(tempGrid[i][j + 1]);

        neighbours.add(tempGrid[i + 1][j - 1]);
        neighbours.add(tempGrid[i + 1][j]);
        neighbours.add(tempGrid[i + 1][j + 1]);

        long countOn = neighbours.stream().filter(x -> x).count();

        if (ledIsOn)
            if (countOn == 2 || countOn == 3) return true;
        if (!ledIsOn)
            if (countOn == 3) return true;

        return false;
    }

    // initialize values for whole grid, but holds "border" of 1 "off" led around grid
    private static void initGrid(String[] args) {

        for (int x = 0; x < args.length; x++) {
            String arg = args[x];
            //System.out.println(arg);
            char[] charArray = arg.toCharArray();
            for (int y = 0; y < charArray.length; y++) {
                char c = charArray[y];
                if (c == '#') grid[x + 1][y + 1] = true;
            }
        }
    }
}
