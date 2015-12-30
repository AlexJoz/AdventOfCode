import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by A. Joz on 12/30/2015.
 * <p>
 * --- Day 14: Reindeer Olympics ---
 * <p>
 * This year is the Reindeer Olympics!
 * Reindeer can fly at high speeds, but must rest occasionally to recover their energy.
 * Santa would like to know which of his reindeer is fastest, and so he has them race.
 * <p>
 * Reindeer can only either be flying (always at their top speed) or resting (not moving at all),
 * and always spend whole seconds in either state.
 * <p>
 * For example, suppose you have the following Reindeer:
 * <p>
 * Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
 * Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
 * After one second, Comet has gone 14 km, while Dancer has gone 16 km.
 * After ten seconds, Comet has gone 140 km, while Dancer has gone 160 km.
 * On the eleventh second, Comet begins resting (staying at 140 km), and Dancer continues on for a total distance of 176 km.
 * On the 12th second, both reindeer are resting. They continue to rest until the 138th second, when Comet flies for another ten seconds.
 * On the 174th second, Dancer flies for another 11 seconds.
 * <p>
 * In this example, after the 1000th second, both reindeer are resting, and Comet is in the lead at 1120 km
 * (poor Dancer has only gotten 1056 km by that point).
 * So, in this situation, Comet would win (if the race ended at 1000 seconds).
 * <p>
 * Given the descriptions of each reindeer (in your puzzle input),
 * after exactly 2503 seconds, what distance has the winning reindeer traveled?
 * <p>
 * --- Part Two ---
 * <p>
 * Seeing how reindeer move in bursts, Santa decides he's not pleased with the old scoring system.
 * <p>
 * Instead, at the end of each second, he awards one point to the reindeer currently in the lead.
 * (If there are multiple reindeer tied for the lead, they each get one point.)
 * He keeps the traditional 2503 second time limit, of course, as doing otherwise would be entirely ridiculous.
 * <p>
 * Given the example reindeer from above, after the first second, Dancer is in the lead and gets one point.
 * He stays in the lead until several seconds into Comet's second burst: after the 140th second,
 * Comet pulls into the lead and gets his first point.
 * Of course, since Dancer had been in the lead for the 139 seconds before that, he has accumulated 139 points by the 140th second.
 * <p>
 * After the 1000th second, Dancer has accumulated 689 points, while poor Comet, our old champion, only has 312.
 * So, with the new scoring system, Dancer would win (if the race ended at 1000 seconds).
 * <p>
 * Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds,
 * how many points does the winning reindeer have?
 */

public class Day14 {
    public static void main(String[] args) {
        List<Reindeer> m = parseData(args);
        System.out.println(m);

        // part1:
        System.out.println(
                calcDistanceOfWinner(m, 2503)
        );

        //part2: ( not so pretty as 1 one =( )
        System.out.println(
                calcScoreOfWinner(m, 2503)
        );
    }

    private static int calcScoreOfWinner(List<Reindeer> reindeers, int i) {
        for (int j = 1; j < i; j++) {
            for (Reindeer deer : reindeers) {
                deer.currentRun++;
                if (deer.shouldGo) {
                    deer.mileage += deer.speed;
                    if (deer.currentRun == deer.goes) {
                        deer.shouldGo = false;
                        deer.currentRun = 0;
                    }
                } else {
                    if (deer.currentRun == deer.waits) {
                        deer.shouldGo = true;
                        deer.currentRun = 0;
                    }
                }
            }
            int maxMiliage = reindeers.stream().mapToInt(x -> x.mileage).max().getAsInt();
            reindeers.stream()
                    .filter(x -> x.mileage >= maxMiliage)
                    .forEach(x -> x.score++);
        }
        return reindeers.stream()
                .mapToInt(x -> x.score)
                .max()
                .getAsInt();
    }

    private static int calcDistanceOfWinner(List<Reindeer> reindeers, int i) {
        return reindeers.parallelStream()
                .mapToInt(deer -> deer.speed * deer.goes * count(deer, i))
                .max()
                .getAsInt();
    }

    // calculates how many times deer can do run in "i" seconds
    private static int count(Reindeer r, int i) {
        int total = r.getWorkingTime();
        return (i % total > r.goes) ? (i / total) + 1 : i / total;
    }

    private static List<Reindeer> parseData(String[] args) {
        return Stream.of(args)
                .map(s -> s.split(" "))
                .map(s -> new Reindeer(s[0], s[3], s[6], s[13]))
                .collect(Collectors.toList());
    }

    private static class Reindeer {
        String name;
        int speed;
        int goes;
        int waits;
        int mileage;
        int score;
        boolean shouldGo;
        int currentRun;

        Reindeer(String name, String speed, String goes, String waits) {
            this.name = name;
            this.speed = Integer.parseInt(speed);
            this.goes = Integer.parseInt(goes);
            this.waits = Integer.parseInt(waits);
            this.shouldGo = true;
            this.currentRun = 0;
        }

        public int getWorkingTime() {
            return this.goes + this.waits;
        }

        @Override
        public String toString() {
            return "Reindeer{" +
                    "name='" + name + '\'' +
                    ", speed=" + speed +
                    ", goes=" + goes +
                    ", waits=" + waits +
                    '}';
        }
    }
}
