import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by A. Joz on 12/31/2015.
 * --- Day 16: Aunt Sue ---
 * <p>
 * Your Aunt Sue has given you a wonderful gift, and you'd like to send her a thank you card.
 * However, there's a small problem: she signed it "From, Aunt Sue".
 * <p>
 * You have 500 Aunts named "Sue".
 * <p>
 * So, to avoid sending the card to the wrong person, you need to figure out which Aunt Sue
 * (which you conveniently number 1 to 500, for sanity) gave you the gift. You open the present and, as luck would have it,
 * good ol' Aunt Sue got you a My First Crime Scene Analysis Machine! Just what you wanted.
 * Or needed, as the case may be.
 * <p>
 * The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few specific compounds in a given sample,
 * as well as how many distinct kinds of those compounds there are.
 * According to the instructions, these are what the MFCSAM can detect:
 * <p>
 * children, by human DNA age analysis.
 * cats. It doesn't differentiate individual breeds.
 * Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.
 * goldfish. No other kinds of fish.
 * trees, all in one group.
 * cars, presumably by exhaust or gasoline or something.
 * perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
 * In fact, many of your Aunts Sue have many of these. You put the wrapping from the gift into the MFCSAM.
 * It beeps inquisitively at you a few times and then prints out a message on ticker tape:
 * <p>
 * children: 3
 * cats: 7
 * samoyeds: 2
 * pomeranians: 3
 * akitas: 0
 * vizslas: 0
 * goldfish: 5
 * trees: 3
 * cars: 2
 * perfumes: 1
 * <p>
 * You make a list of the things you can remember about each Aunt Sue.
 * Things missing from your list aren't zero - you simply don't remember the value.
 * <p>
 * What is the number of the Sue that got you the gift?
 * <p>
 * --- Part Two ---
 * <p>
 * As you're about to send the thank you note, something in the MFCSAM's instructions catches your eye.
 * Apparently, it has an outdated retroencabulator, and so the output from the machine isn't exact values -
 * some of them indicate ranges.
 * <p>
 * In particular, the cats and trees readings indicates that there are greater than that many
 * (due to the unpredictable nuclear decay of cat dander and tree pollen),
 * while the pomeranians and goldfish readings indicate that there are fewer than that many
 * (due to the modial interaction of magnetoreluctance).
 * <p>
 * What is the number of the real Aunt Sue?
 */

public class Day16 {
    final static Map<String, Integer> etalonProps = new HashMap<>();
    final static Sue etalonSue = new Sue(10000, etalonProps);

    static {
        etalonProps.put("children", 3);
        etalonProps.put("cats", 7);
        etalonProps.put("samoyeds", 2);
        etalonProps.put("pomeranians", 3);
        etalonProps.put("akitas", 0);
        etalonProps.put("vizslas", 0);
        etalonProps.put("goldfish", 5);
        etalonProps.put("trees", 3);
        etalonProps.put("cars", 2);
        etalonProps.put("perfumes", 1);
    }

    public static void main(String[] args) {
        List<Sue> sues = Stream.of(args)
                .map(Sue::new)
                .collect(Collectors.toList());

        // part1:
        System.out.println(
                sues.stream()
                        .filter(x ->
                                x.matches(etalonSue, 1))
                        .findFirst()
                        .get()
                        .id
        );

        // part2:
        System.out.println(
                sues.stream()
                        .filter(x ->
                                x.matches(etalonSue, 2))
                        .findFirst()
                        .get()
                        .id
        );
    }

    static class Sue {
        private final Map<String, Integer> properties;
        private int id;

        public Sue(String arg) {
            this.properties = new HashMap<>();

            //parse input arg lines
            Pattern su = Pattern.compile("^Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)$");
            Matcher m = su.matcher(arg);
            while (m.find()) {
                this.id = Integer.parseInt(m.group(1));
                this.properties.put(m.group(2), Integer.parseInt(m.group(3)));
                this.properties.put(m.group(4), Integer.parseInt(m.group(5)));
                this.properties.put(m.group(6), Integer.parseInt(m.group(7)));
            }
        }

        public Sue(int id, Map map) {
            this.id = id;
            this.properties = map;
        }

        public int getProperty(String p) {
            return properties.getOrDefault(p, -1);
        }

        public boolean matches(Sue that, int part) {
            return matches(that, "children", part) &&
                    matches(that, "cats", part) &&
                    matches(that, "samoyeds", part) &&
                    matches(that, "pomeranians", part) &&
                    matches(that, "akitas", part) &&
                    matches(that, "vizslas", part) &&
                    matches(that, "goldfish", part) &&
                    matches(that, "trees", part) &&
                    matches(that, "cars", part) &&
                    matches(that, "perfumes", part);
        }

        // helper function
        private boolean matches(Sue that, String property, int part) {
            if (this.getProperty(property) == -1 || that.getProperty(property) == -1) {
                return true;
            }
            if (part == 2) {
                if (property.equals("cats") || property.equals("trees"))
                    return this.getProperty(property) > that.getProperty(property);

                if (property.equals("pomeranians") || property.equals("goldfish"))
                    return this.getProperty(property) < that.getProperty(property);

                return this.getProperty(property) == that.getProperty(property);
            }
            return this.getProperty(property) == that.getProperty(property);
        }

        @Override
        public String toString() {
            return "Sue{" +
                    "properties=" + properties +
                    ", id=" + id +
                    '}';
        }
    }
}
