import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
 * <p>
 * Santa is delivering presents to an infinite two-dimensional grid of houses.
 * <p>
 * He begins by delivering a present to the house at his starting location,
 * and then an elf at the North Pole calls him via radio and tells him where to move next.
 * Moves are always exactly one house to the north (^), south (v), east (>), or west (<).
 * After each move, he delivers another present to the house at his new location.
 * <p>
 * However, the elf back at the north pole has had a little too much eggnog,
 * and so his directions are a little off, and Santa ends up visiting some houses more than once.
 * How many houses receive at least one present?
 * <p>
 * For example:
 * <p>
 * > delivers presents to 2 houses: one at the starting location, and one to the east.
 * ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
 * ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
 * <p>
 * --- Part Two ---
 * <p>
 * The next year, to speed up the process, Santa creates a robot version of himself,
 * Robo-Santa, to deliver presents with him.
 * <p>
 * Santa and Robo-Santa start at the same location (delivering two presents to the same starting house),
 * then take turns moving based on instructions from the elf,
 * who is eggnoggedly reading from the same script as the previous year.
 * <p>
 * This year, how many houses receive at least one present?
 * <p>
 * For example:
 * <p>
 * ^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
 * ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
 * ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
 */

public class Day3 {

    private class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void goUp() {
            this.x++;
        }

        public void goDown() {
            this.x--;
        }

        public void goRight() {
            this.y++;
        }

        public void goLeft() {
            this.y--;
        }

        public Point clone() {
            return new Point(this.x, this.y);
        }

        @Override
        public String toString() {
            return this.x + " " + this.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    private int countHouses(String input) {
        Point currentP = new Point(0, 0);
        HashSet<Point> houses = new HashSet<>();

        for (char c :
                input.toCharArray()) {
            switch (c) {
                case '^':
                    currentP.goUp();
                    houses.add(currentP.clone());
                    break;
                case 'v':
                    currentP.goDown();
                    houses.add(currentP.clone());
                    break;
                case '>':
                    currentP.goRight();
                    houses.add(currentP.clone());
                    break;
                case '<':
                    currentP.goLeft();
                    houses.add(currentP.clone());
                    break;
            }
        }
        return houses.size();
    }

    private int countHousesWithRobo(String input) {
        Point currentSanta = new Point(0, 0);
        Point currentRoboSatna = new Point(0, 0);
        Point current;
        HashSet<Point> houses = new HashSet<>();
        houses.add(currentSanta);
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (i % 2 == 0) current = currentRoboSatna;
            else current = currentSanta;
            char c = charArray[i];
            switch (c) {
                case '^':
                    current.goUp();
                    houses.add(current.clone());
                    break;
                case 'v':
                    current.goDown();
                    houses.add(current.clone());
                    break;
                case '>':
                    current.goRight();
                    houses.add(current.clone());
                    break;
                case '<':
                    current.goLeft();
                    houses.add(current.clone());
                    break;
            }
        }
        return houses.size();
    }

    public static void main(String... args) {
        String input = args[0];
        Day3 task = new Day3();
        // task 1
        System.out.println(task.countHouses(input));

        // task 2
        System.out.println(task.countHousesWithRobo(input));
    }
}
