import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 6: Probably a Fire Hazard ---
 * <p>
 * Because your neighbors keep defeating you in the holiday house decorating contest year after year,
 * you've decided to deploy one million lights in a 1000x1000 grid.
 * <p>
 * Furthermore, because you've been especially nice this year,
 * Santa has mailed you instructions on how to display the ideal lighting configuration.
 * <p>
 * Lights in your grid are numbered from 0 to 999 in each direction;
 * the lights at each corner are at 0,0, 0,999, 999,999, and 999,0.
 * The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs.
 * Each coordinate pair represents opposite corners of a rectangle, inclusive;
 * a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.
 * <p>
 * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
 * <p>
 * For example:
 * <p>
 * turn on 0,0 through 999,999 would turn on (or leave on) every light.
 * toggle 0,0 through 999,0 would toggle the first line of 1000 lights,
 * turning off the ones that were on, and turning on the ones that were off.
 * turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
 * <p>
 * After following the instructions, how many lights are lit?
 *
 * --- Part Two ---
 *
 * You just finish implementing your winning light pattern when you realize
 * you mistranslated Santa's message from Ancient Nordic Elvish.
 *
 * The light grid you bought actually has individual brightness controls;
 * each light can have a brightness of zero or more. The lights all start at zero.
 *
 * The phrase turn on actually means that you should increase the brightness of those lights by 1.
 *
 * The phrase turn off actually means that you should decrease the brightness of those lights by 1,
 * to a minimum of zero.
 *
 * The phrase toggle actually means that you should increase the brightness of those lights by 2.
 *
 * What is the total brightness of all lights combined after following Santa's instructions?
 *
 * For example:
 *
 * turn on 0,0 through 0,0 would increase the total brightness by 1.
 * toggle 0,0 through 999,999 would increase the total brightness by 2000000.
 *
 */

interface Partible {
    int countOnLeds();
    void proceedLed(String command, int x, int y);
}

public class Day6 {
    @NotNull
    private static String parseCommand(String s) {
        if (s.matches("^toggle.*")) return "toggle";
        if (s.matches("^turn on.*")) return "on";
        if (s.matches("^turn of.*")) return "off";
        return "Unsupported";
    }

    // returns coords as list: [x, y, x, y]
    private static ArrayList<Integer> parseCoords(String s) {
        Pattern pat = Pattern.compile("(\\d+,\\d+)");
        Matcher m = pat.matcher(s);
        ArrayList<Integer> out = new ArrayList<>();
        while (m.find()) {
            final String[] split = m.group().split(",");
            out.add(Integer.parseInt(split[0])); // add x
            out.add(Integer.parseInt(split[1])); // add y
        }
        return out;
    }

    public static void main(String... args) {
        Day6 d6 = new Day6();
        Part1 p1 = new Part1();
        Part2 p2 = new Part2();

        for (String s : args) {
            d6.satisfy(s, p1);
            d6.satisfy(s, p2);
        }
        // Part 1:
        System.out.println("Part1: " + p1.countOnLeds());
        // Part 2:
        System.out.println("Part2: " + p2.countOnLeds());

    }

    private void satisfy(String s, Partible c) {
        String command = parseCommand(s);

        ArrayList<Integer> rect = parseCoords(s);
        System.out.println(rect);
        IntStream.range(rect.get(0), rect.get(2) + 1)
                .forEach(x ->
                        IntStream.range(rect.get(1), rect.get(3) + 1)
                                .forEach(y ->
                                        c.proceedLed(command, x, y)));

    }
}

class Part1 implements Partible {
    private boolean[][] board = new boolean[1000][1000];

    @Override
    public int countOnLeds() {
        return Arrays.deepToString(board).replaceAll("[^t]", "").length();
    }

    @Override
    public void proceedLed(String command, int x, int y) {
        switch (command) {
            case "toggle":
                this.board[x][y] = !board[x][y];
                break;
            case "on":
                this.board[x][y] = true;
                break;
            case "off":
                this.board[x][y] = false;
                break;
        }
    }
}

class Part2 implements Partible {
    private int[][] board = new int[1000][1000];

    @Override
    public int countOnLeds() {
        return Arrays.stream(board).flatMapToInt(x -> Arrays.stream(x)).sum();
    }

    @Override
    public void proceedLed(String command, int x, int y) {
        switch (command) {
            case "toggle":
                this.board[x][y] += 2;
                break;
            case "on":
                this.board[x][y] += 1;
                break;
            case "off":
                if (this.board[x][y] > 0) this.board[x][y] -= 1;
                break;
        }
    }
}