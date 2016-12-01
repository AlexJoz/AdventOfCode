import java.util.stream.IntStream;

/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 4: The Ideal Stocking Stuffer ---
 * <p>
 * Santa needs help mining some AdventCoins (very similar to bitcoins)
 * to use as gifts for all the economically forward-thinking little girls and boys.
 * <p>
 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes.
 * The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal.
 * To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
 * <p>
 * For example:
 * <p>
 * If your secret key is abcdef, the answer is 609043,
 * because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...),
 * and it is the lowest such number to do so.
 * <p>
 * If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash
 * starting with five zeroes is 1048970;
 * that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
 * <p>
 * --- Part Two ---
 * <p>
 * Now find one that starts with six zeroes.
 */

public class Day4 {
    public static int findNumber(String input, String startsWith) {
        return IntStream
                .range(1, Integer.MAX_VALUE)
                .filter(a -> Hash.md5(input + a).startsWith(startsWith))
                .findFirst()
                .getAsInt();
    }

    public static void main(String... args) {
        String input = args[0];

        // part 1
        System.out.println(findNumber(input, "0000"));

        // part 2
        System.out.println(findNumber(input, "000000"));
    }
}
