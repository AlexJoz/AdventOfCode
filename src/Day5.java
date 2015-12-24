import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Created by A, Joz, 2015.
 * <p>
 * --- Day 5: Doesn't He Have Intern-Elves For This? ---
 * <p>
 * Santa needs help figuring out which strings in his text file are naughty or nice.
 * <p>
 * A nice string is one with all of the following properties:
 * <p>
 * It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
 * It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
 * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
 * <p>
 * For example:
 * <p>
 * <b>ugknbfddgicrmopn</b> is nice because it has at least three vowels
 * (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
 * <b>aaa</b> is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
 * <b>jchzalrnumimnmhp</b> is naughty because it has no double letter.
 * <b>haegwjzuvuyypxyu</b> is naughty because it contains the string xy.
 * <b>dvszwmarrgswjxmb</b> is naughty because it contains only one vowel.
 * <p>
 * How many strings are nice?
 * <p>
 * --- Part Two ---
 * <p>
 * Realizing the error of his ways, Santa has switched to a better model of determining
 * whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.
 * <p>
 * Now, a nice string is one with all of the following properties:
 * <p>
 * It contains a pair of any two letters that appears at least twice in the string without overlapping,
 * like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
 * It contains at least one letter which repeats with exactly one letter between them,
 * like xyx, abcdefeghi (efe), or even aaa.
 * <p>
 * For example:
 * <p>
 * qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
 * xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
 * uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
 * ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
 * How many strings are nice under these new rules?
 */

public class Day5 {
    private static HashSet<String> vowels = new HashSet<>();

    static {
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");
    }

    public static void main(String... args) {
        int counter = 0;
        int counter2 = 0;
        for (String input :
                args) {
            final String[] split = input.split("");
            if (checkVovels(split) && checkTwice(split) && checkStrings(input)) counter++;

            if (checkPair(input)) counter2++;
        }

        // part 1
        System.out.println(counter);

        // part 2
        System.out.println(counter2);
    }

    // let's try regexp for part 2 =)
    private static boolean checkPair(String input) {
        if (input.matches(".*(.).\\1.*"))
            return input.matches(".*(\\w\\w).*\\1.*");
        return false;
    }

    // gives true if "dubled"-letter appears
    private static boolean checkTwice(String[] split) {
        for (int i = 0; i < split.length - 1; i++) {
            String current = split[i];
            String next = split[i + 1];
            if (current.equalsIgnoreCase(next)) return true;
        }
        return false;
    }

    // gives true if not contains expected strings
    private static boolean checkStrings(String input) {
        return !(input.contains("ab") || input.contains("cd") || input.contains("pq") || input.contains("xy"));
    }

    // gives true if at least 3 vowels appears
    private static boolean checkVovels(String[] split) {
        return Stream.of(split).filter(s -> vowels.contains(s)).count() >= 3;
    }
}
