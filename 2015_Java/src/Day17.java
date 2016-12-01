import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by A. Joz on 12/31/2015.
 * <p>
 * --- Day 17: No Such Thing as Too Much ---
 * <p>
 * The elves bought too much eggnog again - 150 liters this time.
 * To fit it all into your refrigerator, you'll need to move it into smaller containers.
 * You take an inventory of the capacities of the available containers.
 * <p>
 * For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters.
 * If you need to store 25 liters, there are four ways to do it:
 * <p>
 * 15 and 10
 * 20 and 5 (the first 5)
 * 20 and 5 (the second 5)
 * 15, 5, and 5
 * Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?
 * <p>
 * --- Part Two ---
 * <p>
 * While playing with all the containers in the kitchen, another load of eggnog arrives!
 * The shipping and receiving department is requesting as many containers as you can spare.
 * <p>
 * Find the minimum number of containers that can exactly fit all 150 liters of eggnog.
 * How many different ways can you fill that number of containers and still hold exactly 150 litres?
 * <p>
 * In the example above, the minimum number of containers was two.
 * There were three ways to use that many containers, and so the answer there would be 3.
 */
public class Day17 {
    final static ConcurrentMap<Integer, AtomicLong> bottleMap = new ConcurrentHashMap<>();
    private static final int[] bottles = new int[]{50, 44, 11, 49, 42, 46, 18, 32, 26, 40, 21, 7, 18, 43, 10, 47, 36, 24, 22, 40};
    // private static final int[] bottles = new int[]{20, 15, 10, 5, 5}; //small test input, use 25 as litres var

    public static void main(String[] args) {
        /*
        sum, bottles index, bottle counter, how many liters collect
        ! recursive;
        */
        countAll(0, 0, 0, 150);

        //part 1:
        System.out.println(
                "Total: " +
                        bottleMap
                                .values()
                                .stream()
                                .map(AtomicLong::intValue)
                                .reduce((x, y) -> x + y)
                                .get()
        );

        //part 2:
        int minBottles = bottleMap
                .keySet()
                .stream()
                .map(Integer::valueOf)
                .min(Integer::compare)
                .get();
        System.out.println(
                "Number of bottles + permutation quantity: " + bottleMap
        );
        System.out.println(
                "So, min number of bottles:" + minBottles +
                        " Permutations:" + bottleMap.get(minBottles)
        );
    }

    private static void countAll(int sum, int index, int bottle, int liters) {
        bottle++;
        for (int i = index; i < bottles.length; i++) {
            if (sum + bottles[i] == liters) {
                // fill map like (Key -> number of bottles, Value -> increment number of permutations on every "found" strike)
                bottleMap.putIfAbsent(bottle, new AtomicLong(0));
                bottleMap.get(bottle).incrementAndGet();
            } else if (sum + bottles[i] < liters) countAll(sum + bottles[i], i + 1, bottle, liters);
        }
    }
}

