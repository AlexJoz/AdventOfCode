/**
 * Created by A. Joz on 1/2/2016.
 * <p>
 * --- Day 20: Infinite Elves and Infinite Houses ---
 * <p>
 * To keep the Elves busy, Santa has them deliver some presents by hand, door-to-door.
 * He sends them down a street with infinite houses numbered sequentially: 1, 2, 3, 4, 5, and so on.
 * <p>
 * Each Elf is assigned a number, too, and delivers presents to houses based on that number:
 * <p>
 * The first Elf (number 1) delivers presents to every house: 1, 2, 3, 4, 5, ....
 * The second Elf (number 2) delivers presents to every second house: 2, 4, 6, 8, 10, ....
 * Elf number 3 delivers presents to every third house: 3, 6, 9, 12, 15, ....
 * There are infinitely many Elves, numbered starting with 1.
 * Each Elf delivers presents equal to ten times his or her number at each house.
 * <p>
 * So, the first nine houses on the street end up like this:
 * <p>
 * House 1 got 10 presents.
 * House 2 got 30 presents.
 * House 3 got 40 presents.
 * House 4 got 70 presents.
 * House 5 got 60 presents.
 * House 6 got 120 presents.
 * House 7 got 80 presents.
 * House 8 got 150 presents.
 * House 9 got 130 presents.
 * The first house gets 10 presents:  it is visited only by Elf 1, which delivers 1 * 10 = 10 presents.
 * The fourth house gets 70 presents, because it is visited by Elves 1, 2, and 4, for a total of 10 + 20 + 40 = 70 presents.
 * <p>
 * What is the lowest house number of the house to get at least as many presents as the number in your puzzle input?
 *
 * --- Part Two ---

 The Elves decide they don't want to visit an infinite number of houses.
 Instead, each Elf will stop after delivering presents to 50 houses.
 To make up for it, they decide to deliver presents equal to eleven times their number at each house.

 With these changes, what is the new lowest house number of the house to get at least as many presents as the number in your puzzle input?
 */
public class Day20 {
    public static void main(String[] args) {

        /*
        the task is simple:
        find the first (min)
        sum of all divisors,
        greater or eq to target number
        and return such numer =)
        */
        final int target = 36000000;
        final int max = target/10;
        int[] houses = new int[max];
        int answer = 0;

        boolean part1 = false;

        for (int elf = 1; elf < max; elf++) {
            if (part1) {
                for (int visited = elf; visited < max; visited += elf) {
                    houses[visited] += elf * 10;
                }
            } else {
                for (int visited = elf; (visited <= elf*50 && visited < max); visited += elf) {
                    houses[visited] += elf * 11;
                }
            }
        }

        for (int i = 0; i < max; i++) {
            if (houses[i] >= target) {
                answer = i;
                break;
            }
        }

        System.out.println(answer);

    }
}
