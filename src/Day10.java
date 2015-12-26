import org.jetbrains.annotations.NotNull;

/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 10: Elves Look, Elves Say ---
 * <p>
 * Today, the Elves are playing a game called look-and-say.
 * They take turns making sequences by reading aloud the previous sequence
 * and using that reading as the next sequence.
 * For example, 211 is read as "one two, two ones", which becomes 1221 (1 2, 2 1s).
 * <p>
 * Look-and-say sequences are generated iteratively,
 * using the previous value as input for the next step.
 * For each step, take the previous value, and replace each run of digits (like 111)
 * with the number of digits (3) followed by the digit itself (1).
 * <p>
 * For example:
 * <p>
 * 1 becomes 11 (1 copy of digit 1).
 * 11 becomes 21 (2 copies of digit 1).
 * 21 becomes 1211 (one 2 followed by one 1).
 * 1211 becomes 111221 (one 1, one 2, and two 1s).
 * 111221 becomes 312211 (three 1s, two 2s, and one 1).
 * Starting with the digits in your puzzle input, apply this process 40 times.
 * What is the length of the result?
 * <p>
 * --- Part Two ---
 * <p>
 * Neat, right? You might also enjoy hearing John Conway talking about this sequence
 * (that's Conway of Conway's Game of Life fame).
 * <p>
 * Now, starting again with the digits in your puzzle input,
 * apply this process 50 times.
 * What is the length of the new result?
 */

public class Day10 {

    public static void main(String... args) {
        // part1:
        System.out.println(encode("1113222113", 40).length());
        // part2:
        System.out.println(encode("1113222113", 50).length());
    }

    @NotNull
    private static String encode(String s, int n) {
        if (n == 0) return s;
        StringBuilder sb = new StringBuilder();
        char current = ' ';
        int count = 0;
        for (char c : s.toCharArray()) {
            if (current == ' ') {
                current = c;
            }
            if (c == current) {
                count++;
            } else {
                sb.append(count).append(current);
                count = 1;
                current = c;
            }
        }
        sb.append(count).append(current);

        s = encode(sb.toString(), n - 1);

        return s;
    }
}
