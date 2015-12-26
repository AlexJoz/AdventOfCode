import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 9: All in a Single Night ---
 * <p>
 * Every year, Santa manages to deliver all of his presents in a single night.
 * <p>
 * This year, however, he has some new locations to visit;
 * his elves have provided him the distances between every pair of locations.
 * He can start and end at any two (different) locations he wants, but he must visit each location exactly once.
 * What is the shortest distance he can travel to achieve this?
 * <p>
 * For example, given the following distances:
 * <p>
 * London to Dublin = 464
 * London to Belfast = 518
 * Dublin to Belfast = 141
 * The possible routes are therefore:
 * <p>
 * Dublin -> London -> Belfast = 982
 * London -> Dublin -> Belfast = 605
 * London -> Belfast -> Dublin = 659
 * Dublin -> Belfast -> London = 659
 * Belfast -> Dublin -> London = 605
 * Belfast -> London -> Dublin = 982
 * The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.
 * <p>
 * What is the distance of the shortest route?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * The next year, just to show off, Santa decides to take the route with the longest distance instead.
 * <p>
 * He can still start and end at any two (different) locations he wants,
 * and he still must visit each location exactly once.
 * <p>
 * For example, given the distances above, the longest route would be 982
 * via (for example) Dublin -> London -> Belfast.
 * <p>
 * What is the distance of the longest route?
 */

public class Day9 {

    public static void main(String... args) {
        // find unique cities
        Set<String> list = new TreeSet<>();
        for (String arg : args) {
            String[] split = arg.split(" ");
            list.add(split[0]); //from
            list.add(split[2]); //to
        }

        // add key-numbers for each city
        final AtomicInteger i = new AtomicInteger(0);
        Map<String, Integer> m = list.stream()
                .collect(Collectors.toMap(s -> s, v -> i.getAndIncrement()));

        // fill weight matrix
        int[][] wights = new int[list.size()][list.size()];
        for (String arg : args) {
            String[] split = arg.split(" ");
            wights[m.get(split[0])][m.get(split[2])] = Integer.parseInt(split[4]);
            wights[m.get(split[2])][m.get(split[0])] = Integer.parseInt(split[4]);
        }
        // permutate
        GeneratePermutations permGen = new GeneratePermutations(list.toArray(new String[list.size()]));
        final ArrayList<String[]> perumutations = permGen.getPerumutations();
        int km = Integer.MAX_VALUE;
        int manykm = 0;
        for (String[] a :
                perumutations) {

            // calc shortest and longest paths
            int path = 0;
            for (int k = 0; k < a.length - 1; k++) {
                path += wights[m.get(a[k])][m.get(a[k + 1])];
            }

            if (path < km) km = path;
            if (path > manykm) manykm = path;
        }

        // part1:
        System.out.println("Min path: " + km);

        // part2:
        System.out.println("Max path: " + manykm);

    }
}
