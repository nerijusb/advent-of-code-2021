import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part two of
 * https://adventofcode.com/2021/day/7
 *
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
        System.out.println("Sum of all output values:" + new Day08_2().getResult());
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
        mapping.put(1, Arrays.stream(inputNumbers).filter(n -> n.length() == 2).findFirst().map(n -> n.split("")).orElseThrow());
        mapping.put(4, Arrays.stream(inputNumbers).filter(n -> n.length() == 4).findFirst().map(n -> n.split("")).orElseThrow());
        mapping.put(7, Arrays.stream(inputNumbers).filter(n -> n.length() == 3).findFirst().map(n -> n.split("")).orElseThrow());
        mapping.put(8, Arrays.stream(inputNumbers).filter(n -> n.length() == 7).findFirst().map(n -> n.split("")).orElseThrow());
        // find rest
        mapping.put(0, find(0, inputNumbers, mapping).split(""));
        mapping.put(2, find(2, inputNumbers, mapping).split(""));
        mapping.put(3, find(3, inputNumbers, mapping).split(""));
        mapping.put(5, find(5, inputNumbers, mapping).split(""));
        mapping.put(6, find(6, inputNumbers, mapping).split(""));
        mapping.put(9, find(9, inputNumbers, mapping).split(""));
        return mapping;
    }

    String find(int toFind, String[] encodedNumbers, Map<Integer, String[]> mapping) {
        for (String encoded : encodedNumbers) {
            if (encoded.length() == 2) {
                if (toFind == 1) {
                    return encoded;
                }
            } else if (encoded.length() == 4) {
                if (toFind == 4) {
                    return encoded;
                }
            } else if (encoded.length() == 3) {
                if (toFind == 7) {
                    return encoded;
                }
            } else if (encoded.length() == 7) {
                if (toFind == 8) {
                    return encoded;
                }
            } else if (encoded.length() == 6) {
                String[] four = mapping.get(4);
                if (List.of(encoded.split("")).containsAll(List.of(four))) {
                    String[] seven = mapping.get(7);
                    if (List.of(encoded.split("")).containsAll(List.of(seven))) {
                        if (toFind == 9) {
                            return encoded;
                        }
                    }
                } else {
                    String[] seven = mapping.get(7);
                    if (List.of(encoded.split("")).containsAll(List.of(seven))) {
                        if (toFind == 0) {
                            return encoded;
                        }
                    } else {
                        if (toFind == 6) {
                            return encoded;
                        }
                    }
                }
            // size 5
            } else {
                String[] seven = mapping.get(7);
                if (Arrays.asList(encoded.split("")).containsAll(List.of(seven))) {
                    if (toFind == 3) {
                        return encoded;
                    }
                } else if (containsThreeOf(encoded, mapping.get(4))) {
                    if (toFind == 5) {
                        return encoded;
                    }
                } else {
                    if (toFind == 2) {
                        return encoded;
                    }
                }
            }
        }

        throw new IllegalStateException("Could not find encoded number for: " + toFind);
    }

    private boolean containsThreeOf(String number, String[] parts) {
        List<String> numberParts = Arrays.asList(number.split(""));
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