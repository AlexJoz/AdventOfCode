import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 12: JSAbacusFramework.io ---
 * <p>
 * Santa's Accounting-Elves need help balancing the books after a recent order.
 * Unfortunately, their accounting software uses a peculiar storage format.
 * That's where you come in.
 * <p>
 * They have a JSON document which contains a variety of things:
 * arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings.
 * Your first job is to simply find all of the numbers throughout the document and add them together.
 * <p>
 * For example:
 * <p>
 * [1,2,3] and {"a":2,"b":4} both have a sum of 6.
 * [[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
 * {"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
 * [] and {} both have a sum of 0.
 * You will not encounter any strings containing numbers.
 * <p>
 * --- Part Two ---
 * <p>
 * Uh oh - the Accounting-Elves have realized that they double-counted everything red.
 * <p>
 * Ignore any object (and all of its children) which has any property with the value "red".
 * Do this only for objects ({...}), not arrays ([...]).
 * <p>
 * [1,2,3] still has a sum of 6.
 * [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
 * {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
 * [1,"red",5] has a sum of 6, because "red" in an array has no effect.
 */
public class Day12 {

    public static void main(String... args) {
        /*
        simple regex for part 1,
        finds any digit or signed digit and collect whole sum
        */
        Pattern p = Pattern.compile("([0-9]+|-[0-9])+");
        int sum = 0;
        int sum2 = 0;

        Matcher m = p.matcher(args[0]);
        while (m.find()) {
            sum += Integer.parseInt(m.group());
        }
        // part1:
        System.out.println(sum);

        /*
        primitive usage of Gson JSON lib:
        parses string as List and recursively collects the sum of all digits
        except with "red" mark (see task)
        */
        Gson gson = new Gson();
        Object obj = gson.fromJson(args[0], Object.class);
        //System.out.println(obj);
        ArrayList<Object> contents = (ArrayList<Object>) obj;
        for (Object o : contents) {
            sum2 += getNumbers(o);
        }
        // part2:
        System.out.println(sum2);

    }

    @NotNull
    private static Integer getNumbers(Object obje) {
        int s = 0;
        if (obje instanceof LinkedTreeMap) {
            LinkedTreeMap<String, Object> fr = (LinkedTreeMap<String, Object>) obje;
            for (Map.Entry<String, Object> entry : fr.entrySet()) {
                if ("red".equals(entry.getValue())) return 0;
                s += getNumbers(entry.getValue());
            }
            return s;
        }

        if (obje instanceof List) {
            for (Object ob2 : (List) obje) {
                s += getNumbers(ob2);
            }
            return s;
        }

        if (obje instanceof Number) {
            return ((Double) obje).intValue();
        }
        return 0;
    }
}
