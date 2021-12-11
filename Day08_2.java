import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part two of
 * https://adventofcode.com/2021/day/8
 * <p>
 * Rules used:
 * 0: 6parts > size(6) & !allOf(4) & allOf(7)
 * 1: 2parts > size(2)
 * 2: 5parts > size(5) & (remaining)
 * 3: 5parts > size(5) & allOf(7)
 * 4: 4parts > size(4)
 * 5: 5parts > size(5) & anyThreeOf(4)
 * 6: 6parts > size(6) & !allOf(7) & !allOf(4)
 * 7: 3parts > size(3)
 * 8: 7parts > size(7)
 * 9: 6parts > size(6) & allOf(4) & allOf(7)
 *
 * @author Nerijus
 */
public class Day08_2 extends Day08_1 {
    public static void main(String[] args) {
        System.out.println("Sum of all output values: " + new Day08_2().getResult());
    }

    private int getResult() {
        List<String> rows = Inputs.readStrings("Day08");
        return rows.stream().mapToInt(this::getOutput).sum();
    }

    private int getOutput(String row) {
        String[] parts = row.split(" \\| ");
        String[] inputNumbers = parts[0].split(" ");
        String[] outputNumbers = parts[1].split(" ");

        StringBuilder output = new StringBuilder();
        for (String outputNumber : outputNumbers) {
            output.append(decode(outputNumber, decodeMapping(inputNumbers)));
        }
        return Integer.parseInt(output.toString());
    }

    private Map<Integer, String[]> decodeMapping(String[] inputNumbers) {
        Map<Integer, String[]> mapping = new HashMap<>();
        // find known first
        mapping.put(1, findBySize(inputNumbers, 2));
        mapping.put(4, findBySize(inputNumbers, 4));
        mapping.put(7, findBySize(inputNumbers, 3));
        mapping.put(8, findBySize(inputNumbers, 7));
        // find rest
        updateMapping(inputNumbers, mapping);
        return mapping;
    }

    private String[] findBySize(String[] inputNumbers, int size) {
        return Arrays.stream(inputNumbers)
                .filter(n -> n.length() == size)
                .findFirst()
                .map(n -> n.split(""))
                .orElseThrow();
    }

    void updateMapping(String[] encodedNumbers, Map<Integer, String[]> mapping) {
        for (String encoded : encodedNumbers) {
            String[] encodedParts = encoded.split("");
            if (encoded.length() == 2) {
                mapping.put(1, encodedParts);
            } else if (encoded.length() == 4) {
                mapping.put(4, encodedParts);
            } else if (encoded.length() == 3) {
                mapping.put(7, encodedParts);
            } else if (encoded.length() == 7) {
                mapping.put(8, encodedParts);
            } else if (encoded.length() == 6) {
                String[] four = mapping.get(4);
                if (List.of(encodedParts).containsAll(List.of(four))) {
                    String[] seven = mapping.get(7);
                    if (List.of(encodedParts).containsAll(List.of(seven))) {
                        mapping.put(9, encodedParts);
                    }
                } else {
                    String[] seven = mapping.get(7);
                    if (List.of(encodedParts).containsAll(List.of(seven))) {
                        mapping.put(0, encodedParts);
                    } else {
                        mapping.put(6, encodedParts);
                    }
                }
                // size 5
            } else {
                String[] seven = mapping.get(7);
                if (Arrays.asList(encodedParts).containsAll(List.of(seven))) {
                    mapping.put(3, encodedParts);
                } else if (containsThreeOf(encoded, mapping.get(4))) {
                    mapping.put(5, encodedParts);
                } else {
                    mapping.put(2, encodedParts);
                }
            }
        }
    }

    private boolean containsThreeOf(String number, String[] parts) {
        List<String> numberParts = List.of(number.split(""));
        return Arrays.stream(parts).filter(numberParts::contains).count() == 3;
    }

    private int decode(String outputNumber, Map<Integer, String[]> mapping) {
        String[] outputNumberParts = outputNumber.split("");
        return mapping.entrySet().stream()
                .filter(e -> e.getValue().length == outputNumber.length())
                .filter(e -> List.of(e.getValue()).containsAll(List.of(outputNumberParts)))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }
}