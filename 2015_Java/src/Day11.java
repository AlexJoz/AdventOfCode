/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 11: Corporate Policy ---
 * <p>
 * Santa's previous password expired, and he needs help choosing a new one.
 * <p>
 * To help him remember his new password after the old one expires,
 * Santa has devised a method of coming up with a password based on the previous one.
 * Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons),
 * so he finds his new password by incrementing his old password string repeatedly until it is valid.
 * <p>
 * Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on.
 * Increase the rightmost letter one step; if it was z, it wraps around to a,
 * and repeat with the next letter to the left until one doesn't wrap around.
 * <p>
 * Unfortunately for Santa, a new Security-Elf recently started,
 * and he has imposed some additional password requirements:
 * <p>
 * Passwords must include one increasing straight of at least three letters,
 * like abc, bcd, cde, and so on, up to xyz.
 * They cannot skip letters; abd doesn't count.
 * Passwords may not contain the letters i, o, or l,
 * as these letters can be mistaken for other characters and are therefore confusing.
 * Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
 * <p>
 * For example:
 * <p>
 * hijklmmn meets the first requirement
 * (because it contains the straight hij) but fails the second requirement requirement (because it contains i and l).
 * abbceffg meets the third requirement
 * (because it repeats bb and ff) but fails the first requirement.
 * abbcegjk fails the third requirement,
 * because it only has one double letter (bb).
 * <p>
 * The next password after abcdefgh is abcdffaa.
 * The next password after ghijklmn is ghjaabcc,
 * because you eventually skip all the passwords that start with ghi..., since i is not allowed.
 * <p>
 * Given Santa's current password (your puzzle input), what should his next password be?
 * <p>
 * --- Part Two ---
 * <p>
 * Santa's password expired again. What's the next one?
 */
public class Day11 {
    String password;

    public Day11(String password) {

        this.password = password;

        if (isValid(this.password)) this.password = increment(this.password);
        while (!isValid(this.password)) {
            this.password = increment(this.password);
        }

    }

    // returns true if sequential substring appears (i.e -> abc, jhk or xyz, etc)
    private static boolean containsABC(String s) {
        char[] charArray = s.toCharArray();
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength - 2; i++) {
            char one = charArray[i];
            char two = charArray[i + 1];
            char three = charArray[i + 2];
            if ((one == two - 1) && (one == three - 2)) return true;
        }
        return false;
    }

    private static boolean hasInvalidChars(String s) {
        return (s.contains("i") || s.contains("o") || s.contains("l"));
    }

    private static boolean hasTwoPairs(String s) {
        boolean twoPairs = false;

        for (int i = 0, pairs = 0; i < s.length() - 1; i++) {
            char current = s.charAt(i);
            char next = s.charAt(i + 1);

            if (!twoPairs && current == next) {
                i++;

                if (++pairs == 2) {
                    twoPairs = true;
                }
            }
        }
        return twoPairs;
    }

    // checks password for validity
    private static boolean isValid(String s) {
        return s.matches("^[a-z]{8}$") && !hasInvalidChars(s) && containsABC(s) && hasTwoPairs(s);
    }

    private static String increment(String s) {

        char[] chars = s.toCharArray();
        if (chars[chars.length - 1] != 'z') {
            chars[chars.length - 1]++;
            s = new String(chars);
        } else {
            for (int i = chars.length - 2; i >= 0; i--) {
                chars[chars.length - 1] = 'a';
                if (chars[i] != 'z') {
                    chars[i]++;
                    s = new String(chars);
                    break;
                } else {
                    chars[i] = 'a';
                }
            }
        }
        return new String(chars);
    }

    public static void main(String... args) {
        // part 1:
        Day11 d11_1 = new Day11("hxbxwxba");
        System.out.println(d11_1.password);

        //part 2:
        Day11 d11_2 = new Day11(d11_1.password);
        System.out.println(d11_2.password);
    }

}
