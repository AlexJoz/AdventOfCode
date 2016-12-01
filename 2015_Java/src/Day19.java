import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by A. Joz on 12/31/2015.
 * <p>
 * --- Day 19: Medicine for Rudolph ---
 * <p>
 * Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
 * <p>
 * Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made medicine.
 * Unfortunately, Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
 * <p>
 * The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant,
 * capable of constructing any Red-Nosed Reindeer molecule you need.
 * It works by starting with some input molecule and then doing a series of replacements,
 * one per step, until it has the right molecule.
 * <p>
 * However, the machine has to be calibrated before it can be used.
 * Calibration involves determining the number of molecules that can be generated in one step from a given starting point.
 * <p>
 * For example, imagine a simpler machine that supports only the following replacements:
 * <p>
 * H => HO
 * H => OH
 * O => HH
 * Given the replacements above and starting with HOH, the following molecules could be generated:
 * <p>
 * HOOH (via H => HO on the first H).
 * HOHO (via H => HO on the second H).
 * OHOH (via H => OH on the first H).
 * HOOH (via H => OH on the second H).
 * HHHH (via O => HH).
 * So, in the example above, there are 4 distinct molecules (not five, because HOOH appears twice) after one replacement from HOH.
 * Santa's favorite molecule, HOHOHO, can become 7 distinct molecules (over nine replacements: six from H, and three from O).
 * <p>
 * The machine replaces without regard for the surrounding characters.
 * For example, given the string H2O, the transition H => OO would result in OO2O.
 * <p>
 * Your puzzle input describes all of the possible replacements and, at the bottom,
 * the medicine molecule for which you need to calibrate the machine.
 * How many distinct molecules can be created after all the different ways you can do one replacement on the medicine molecule?
 * <p>
 * --- Part Two ---
 * <p>
 * Now that the machine is calibrated, you're ready to begin molecule fabrication.
 * <p>
 * Molecule fabrication always begins with just a single electron, e, and applying replacements one at a time,
 * just like the ones during calibration.
 * <p>
 * For example, suppose you have the following replacements:
 * <p>
 * e => H
 * e => O
 * H => HO
 * H => OH
 * O => HH
 * If you'd like to make HOH, you start with e, and then make the following replacements:
 * <p>
 * e => O to get O
 * O => HH to get HH
 * H => OH (on the second H) to get HOH
 * So, you could make HOH after 3 steps. Santa's favorite molecule, HOHOHO, can be made in 6 steps.
 * <p>
 * How long will it take to make the medicine?
 * Given the available replacements and the medicine molecule in your puzzle input,
 * what is the fewest number of steps to go from e to the medicine molecule?
 */
public class Day19 {
    private static final String molecule = "CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl";
    private static final String molecule2 = "e";
    private static final String moleculeTest1 = "HOH"; //test
    private static final String moleculeTest2 = "HOHOHO"; //test
    public static Map<String, StringBuilder> data = new ConcurrentHashMap<>();
    public static Map<String, StringBuilder> reverseData = new ConcurrentHashMap<>();
    private static int maxStep;

    public static void main(String[] args) {
        parseData(args);

        // part 1:
        System.out.println(
                "Part 1: " + generateAndCountNewMolecules(molecule, data).stream().distinct().count()
        );

        // part 2:
        moleculeFabrication(molecule, molecule2, 0);
        System.out.println(
                "Part 2: " + maxStep
        );
    }

    private static void moleculeFabrication(String from, String desired, int step) {
        step++;
        //System.out.println(step);

        List<String> list = new ArrayList<>();
        while (list.isEmpty()) {
            list = generateAndCountNewMolecules(from, reverseData);
            if (list.contains(desired)) {
                maxStep = step;
                return;
            }
        }
        // get last and continue recursively
        moleculeFabrication(list.stream().distinct().findAny().get(), desired, step);
    }

    private static List<String> generateAndCountNewMolecules(String from, Map<String, StringBuilder> useMap) {
        Comparator<String> byLength = (e1, e2) -> e1.length() >= e2.length() ? -1 : 1;

        //collects output molecules
        List<String> list = new ArrayList<>();

        List<String> l = new ArrayList<>(useMap.keySet());
        l.sort(byLength);
        // Each element, i.e. Si, Al, etc...but start from longest
        for (String s : l) {
            Matcher m = Pattern.compile("(" + s + ")").matcher(from);
            // find in molecule
            while (m.find()) {
                String part = m.group();
                // for each from array of replacement
                for (String rep : useMap.get(part).toString().split(" ")) {
                    String out = from.substring(0, m.start()) + rep + from.substring(m.end(), from.length());
                    list.add(out);
                }
            }
        }
        // System.out.println("New Molecules: " + Arrays.toString(list.stream().distinct().toArray()));
        return list;
    }

    private static void parseData(String[] args) {
        //parse input arg lines
        Pattern su = Pattern.compile("^(\\w+) => (\\w+)$");
        for (String arg : args) {
            Matcher m = su.matcher(arg);
            while (m.find()) {
                String k = m.group(1);
                String v = m.group(2);
                // reverseData for backward bruteforce
                reverseData.put(v, new StringBuilder(k));
                if (data.putIfAbsent(k, new StringBuilder().append(v).append(" ")) == null) continue;
                data.get(k).append(v).append(" ");
            }
        }
    }
}

