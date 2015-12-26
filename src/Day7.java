import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by A. Joz, 2015.
 * <p>
 * --- Day 7: Some Assembly Required ---
 * <p>
 * This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates!
 * Unfortunately, little Bobby is a little under the recommended age range, and he needs help assembling the circuit.
 * <p>
 * Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535).
 * A signal is provided to each wire by a gate, another wire, or some specific value.
 * Each wire can only get a signal from one source, but can provide its signal to multiple destinations.
 * A gate provides no signal until all of its inputs have a signal.
 * <p>
 * The included instructions booklet describes how to connect the parts together:
 * x AND y -> z means to connect wires x and y to an AND gate, and then connect its output to wire z.
 * <p>
 * For example:
 * <p>
 * 123 -> x means that the signal 123 is provided to wire x.
 * x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
 * p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
 * NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
 * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift).
 * If, for some reason, you'd like to emulate the circuit instead, almost all programming languages
 * (for example, C, JavaScript, or Python) provide operators for these gates.
 * <p>
 * For example, here is a simple circuit:
 * <p>
 * 123 -> x
 * 456 -> y
 * x AND y -> d
 * x OR y -> e
 * x LSHIFT 2 -> f
 * y RSHIFT 2 -> g
 * NOT x -> h
 * NOT y -> i
 * After it is run, these are the signals on the wires:
 * <p>
 * d: 72
 * e: 507
 * f: 492
 * g: 114
 * h: 65412
 * i: 65079
 * x: 123
 * y: 456
 * In little Bobby's kit's instructions booklet (provided as your puzzle input),
 * what signal is ultimately provided to wire a?
 * <p>
 * --- Part Two ---
 * <p>
 * Now, take the signal you got on wire a, override wire b to that signal, and reset the other wires (including wire a).
 * What new signal is ultimately provided to wire a?
 */

public class Day7 {
    Map<String, Wire> circuit = new HashMap<>();
    Map<String, Integer> states = new HashMap<>();

    public static void main(String... args) {
        Day7 d7 = new Day7();

        for (String arg : args) {
            // fill map
            d7.circuit.put(d7.parseLine(arg).name, d7.parseLine(arg));
        }

        //part 1:
        int a = d7.circuit.get("a").getState();
        System.out.println("'a' wire: " + a);

        //part 2:
        d7.states.clear();
        // change "b" wire to "a" input;
        d7.circuit.get("b").input1 = "None";
        d7.circuit.get("b").input2 = Integer.toString(a);
        d7.circuit.get("b").op = "None";
        System.out.println("make change 'a' -> 'b'");
        int aNow = d7.circuit.get("a").getState();
        System.out.println("'a' now: " + aNow);
    }

    @NotNull
    private Wire parseLine(String line) {
        String cut1 = "->";
        String cutLeft = "\\s";

        String[] eq = line.split(cut1);

        String wName = eq[1].trim();
        String wInput1 = "";
        String wInput2 = "";
        String wOp = "";
        String[] left = eq[0].split(cutLeft);

        switch (left.length) {
            // like num ->
            case 1:
                wInput1 = "None";
                wOp = "None";
                wInput2 = left[0];
                break;
            // like NOT a ->
            case 2:
                wInput1 = "None";
                wOp = left[0];
                wInput2 = left[1];
                break;
            // like a OR b ->
            case 3:
                wInput1 = left[0];
                wOp = left[1];
                wInput2 = left[2];
                break;
        }
        return new Wire(wName, wInput1, wInput2, wOp);
    }

    private class Wire {
        private String name;
        private int state;
        private String op;
        private String input1;
        private String input2;

        private Wire(String name, String input1, String input2, String op) {
            this.name = name;
            this.input1 = input1;
            this.input2 = input2;
            this.op = op;
        }

        // if number parses int, else gets state from class instance
        private int getTrueInput(String input) {
            if (input.matches(".*\\d+.*")) return Integer.parseInt(input);
            return circuit.get(input).getState();
        }

        public int getState() {
            if (states.containsKey(this.name)) return states.get(this.name);
            int in2 = getTrueInput(input2);
            if (!op.equals("None")) {
                if (!input1.equals("None")) {
                    int in1 = getTrueInput(input1);
                    switch (op) {
                        case "AND":
                            state = in1 & in2;
                            break;
                        case "OR":
                            state = in1 | in2;
                            break;
                        case "LSHIFT":
                            state = in1 << in2;
                            break;
                        case "RSHIFT":
                            state = in1 >> in2;
                            break;
                    }
                } else {
                    state = ~in2 + 65536; // no first arg, so op == NOT, and make "unsigned" =)
                }
            } else state = in2; // no first arg and op
            // cache state
            states.put(this.name, state);

            return state;
        }
    }

}
