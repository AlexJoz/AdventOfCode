import java.util.ArrayList;

/**
 * Recursively generates permutations for String arrays;
 * Input: String[]
 * Output: ArrayList<String[]>
 *
 * @author Kushtrim
 * @author A. Joz
 * @version 2.00
 */

public class GeneratePermutations {
    ArrayList<String> permutations;

    public GeneratePermutations(String[] arr) {
        this.permutations = generatePermutations(arr);
    }

    public ArrayList<String[]> getPerumutations() {
        ArrayList<String[]> list = new ArrayList<>();
        for (String s : permutations) {
           list.add(s.split(" "));
        }
        return list;
    }

    private ArrayList<String> generatePermutations(String[] elements) {
        ArrayList<String> permutations = new ArrayList<String>();
        if (elements.length == 2) {

            String x1 = elements[0] + " " + elements[1];
            String x2 = elements[1] + " " + elements[0];
            permutations.add(x1);
            permutations.add(x2);

        } else {
            for (int i = 0; i < elements.length; i++) {
                String[] elements2 = new String[elements.length - 1];
                int kalo = 0;
                for (int j = 0; j < elements2.length; j++) {
                    if (i == j) {
                        kalo = 1;
                    }
                    elements2[j] = elements[j + kalo];
                }
                ArrayList<String> k2 = generatePermutations(elements2);
                for (String x : k2) {
                    String s = elements[i] + " " + x;
                    permutations.add(s);
                }
            }
        }

        return permutations;
    }
}