import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by A. Joz on 12/30/2015.
 * <p>
 * --- Day 15: Science for Hungry People ---
 * <p>
 * Today, you set out on the task of perfecting your milk-dunking cookie recipe.
 * All you have to do is find the right balance of ingredients.
 * <p>
 * Your recipe leaves room for exactly 100 teaspoons of ingredients.
 * You make a list of the remaining ingredients you could use to finish the recipe (your puzzle input)
 * and their properties per teaspoon:
 * <p>
 * capacity (how well it helps the cookie absorb milk)
 * durability (how well it keeps the cookie intact when full of milk)
 * flavor (how tasty it makes the cookie)
 * texture (how it improves the feel of the cookie)
 * calories (how many calories it adds to the cookie)
 * You can only measure ingredients in whole-teaspoon amounts accurately,
 * and you have to be accurate so you can reproduce your results in the future.
 * The total score of a cookie can be found by adding up each of the properties (negative totals become 0)
 * and then multiplying together everything except calories.
 * <p>
 * For instance, suppose you have these two ingredients:
 * <p>
 * Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
 * Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
 * Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of cinnamon
 * (because the amounts of each ingredient must add up to 100) would result in a cookie with the following properties:
 * <p>
 * A capacity of 44*-1 + 56*2 = 68
 * A durability of 44*-2 + 56*3 = 80
 * A flavor of 44*6 + 56*-2 = 152
 * A texture of 44*3 + 56*-1 = 76
 * Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now)
 * results in a total score of 62842880, which happens to be the best score possible given these ingredients.
 * If any properties had produced a negative total, it would have instead become zero, causing the whole score to multiply to zero.
 * <p>
 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make?
 * <p>
 * --- Part Two ---
 * <p>
 * Your cookie recipe becomes wildly popular!
 * Someone asks if you can make another recipe that has exactly 500 calories per cookie (so they can use it as a meal replacement).
 * Keep the rest of your award-winning process the same (100 teaspoons, same ingredients, same scoring system).
 * <p>
 * For example, given the ingredients above, if you had instead selected 40 teaspoons of butterscotch and 60 teaspoons of cinnamon
 * (which still adds to 100), the total calorie count would be 40*8 + 60*3 = 500.
 * The total score would go down, though: only 57600000, the best you can do in such trying circumstances.
 * <p>
 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie
 * you can make with a calorie total of 500?
 */
public class Day15 {

    public static void main(String[] args) {
        List<Ingridient> ingridientList = parseData(args);

        // part1:
        System.out.println(
                getMealScore(ingridientList, 0)
        );
        // part2:
        System.out.println(
                getMealScore(ingridientList, 500)
        );
    }

    private static int getMealScore(List<Ingridient> ingridientList, int matchCalories) {
        int finalScore = 0;
        for (int A = 0; A <= 100; A++) {
            for (int B = 0; B <= 100 - A; B++) {
                for (int C = 0; C <= 100 - A - B; C++) {
                    int D = 100 - A - B - C;
                    final Vector aI = ingridientList.get(0).getVector().dot(A);
                    final Vector bI = ingridientList.get(1).getVector().dot(B);
                    final Vector cI = ingridientList.get(2).getVector().dot(C);
                    final Vector dI = ingridientList.get(3).getVector().dot(D);
                    Vector sum = aI.plus(bI).plus(cI).plus(dI);
                    int callorA = ingridientList.get(0).getCalories() * A;
                    int callorB = ingridientList.get(1).getCalories() * B;
                    int callorC = ingridientList.get(2).getCalories() * C;
                    int callorD = ingridientList.get(3).getCalories() * D;
                    int callorSum = callorA + callorB + callorC + callorD;

                    if (sum.allPositive()) {
                        int score = sum.prod();
                        if (matchCalories == 0) {
                            if (score > finalScore)
                                finalScore = score;
                        } else if (score > finalScore && callorSum == matchCalories)
                            finalScore = score;
                    }
                }
            }
        }
        return finalScore;
    }

    private static List<Ingridient> parseData(String[] args) {
        return Stream.of(args)
                .map(x -> x.split(" "))
                .map(c -> new Ingridient(c[0], c[2], c[4], c[6], c[8], c[10]))
                .collect(Collectors.toList());
    }

    private static class Ingridient {
        String name;
        int capacity;
        int durability;
        int flavor;
        int texture;
        int calories;

        Ingridient(String name,
                   String capacity,
                   String durability,
                   String flavor,
                   String texture,
                   String calories) {

            this.name = name;
            this.capacity = Integer.parseInt(capacity.replace(",", ""));
            this.durability = Integer.parseInt(durability.replace(",", ""));
            this.flavor = Integer.parseInt(flavor.replace(",", ""));
            this.texture = Integer.parseInt(texture.replace(",", ""));
            this.calories = Integer.parseInt(calories);
        }

        public Vector getVector() {
            return new Vector(
                    this.capacity, this.durability, this.flavor, this.texture
            );
        }

        public int getCalories() {
            return this.calories;
        }

        @Override
        public String toString() {
            return "Ingridient{" +
                    "name='" + name + '\'' +
                    ", capacity=" + capacity +
                    ", durability=" + durability +
                    ", flavor=" + flavor +
                    ", texture=" + texture +
                    ", calories=" + calories +
                    '}';
        }
    }

    private static class Vector {
        private int[] data;

        public Vector(int cap, int dur, int fla, int tex) {
            this.data = new int[]{cap, dur, fla, tex};
        }

        public Vector(int[] arr) {
            this.data = arr;
        }

        public int prod() {
            int d = 1;
            for (int aData : this.data) {
                if (aData < 0) {
                    d *= 0;
                } else
                    d *= aData;
            }
            return d;
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "data=" + Arrays.toString(data) +
                    '}';
        }

        public Vector dot(Vector that) {
            int[] n = new int[data.length];

            for (int i = 0, vLength = data.length; i < vLength; i++) {
                n[i] = this.data[i] * that.data[i];
            }
            return new Vector(n);
        }

        public Vector dot(int k) {
            int[] n = new int[data.length];
            for (int i = 0, vLength = data.length; i < vLength; i++) {
                n[i] = this.data[i] * k;
            }
            return new Vector(n);
        }

        public Vector plus(Vector that) {
            int[] n = new int[data.length];

            for (int i = 0, vLength = data.length; i < vLength; i++) {
                n[i] = this.data[i] + that.data[i];
            }
            return new Vector(n);
        }

        public boolean allPositive() {
            for (int i : this.data) {
                if (i < 0) return false;
            }
            return true;
        }
    }
}
