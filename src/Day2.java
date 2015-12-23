import java.util.Arrays;
import java.util.Collections;

/**
 * Created by A. Joz, 2015.
 * <p>
 * <p>
 * --- Day 2: I Was Told There Would Be No Math ---
 * <p>
 * The elves are running low on wrapping paper, and so they need to submit an order for more. They have a list of the dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need.
 * <p>
 * Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required wrapping paper for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper for each present: the area of the smallest side.
 * <p>
 * For example:
 * <p>
 * A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper
 * plus 6 square feet of slack, for a total of 58 square feet.
 * A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper
 * plus 1 square foot of slack, for a total of 43 square feet.
 * All numbers in the elves' list are in feet.
 * <p>
 * How many total square feet of wrapping paper should they order?
 * <p>
 * --- Part Two ---
 * <p>
 * The elves are also running low on ribbon. Ribbon is all the same width,
 * so they only have to worry about the length they need to order, which they would again like to be exact.
 * <p>
 * The ribbon required to wrap a present is the shortest distance around its sides, or the smallest perimeter of any one face.
 * Each present also requires a bow made out of ribbon as well;
 * the feet of ribbon required for the perfect bow is equal to the cubic feet of volume of the present.
 * Don't ask how they tie the bow, though; they'll never tell.
 * <p>
 * For example:
 * <p>
 * A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap the present
 * plus 2*3*4 = 24 feet of ribbon for the bow, for a total of 34 feet.
 * A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap the present
 * plus 1*1*10 = 10 feet of ribbon for the bow, for a total of 14 feet.
 * <p>
 * How many total feet of ribbon should they order?
 */

public class Day2 {
    public static void main(String... args) {
        int totalFeet = 0;
        int totalRibbon = 0;
        for (String box :
                args) {
            totalFeet += calcArea(box);
            totalRibbon += calcRibbon(box);
        }
        //part 1
        System.out.println(totalFeet);
        //part 2
        System.out.println(totalRibbon);
    }

    private static int calcRibbon(String box) {
        String[] dims = box.split("x", 3);
        // get area of 2 small sides
        int smallArea = Arrays.asList(dims)
                .stream()
                .map(Integer::valueOf)
                .sorted(Collections.reverseOrder())
                .skip(1)
                .reduce((a, b) -> a + b)
                .get()
                .intValue() * 2;

        // get product of dims
        int product = Arrays.asList(dims)
                .stream()
                .mapToInt(Integer::parseInt)
                .reduce((p, i) -> p * i)
                .getAsInt();

        return smallArea + product;
    }

    private static int calcArea(String box) {
        int area = 0;
        String[] dims = box.split("x", 3);
        // unbox dims
        int l = Integer.parseInt(dims[0]);
        int w = Integer.parseInt(dims[1]);
        int h = Integer.parseInt(dims[2]);
        // get faces
        int a = l * w;
        int b = w * h;
        int c = h * l;
        // get min face and add it to the total area of material needed
        int min = Arrays.asList(a, b, c)
                .stream()
                .min(Integer::compareTo)
                .get();
        area = 2 * l * w + 2 * w * h + 2 * h * l + min;
        return area;
    }
}
