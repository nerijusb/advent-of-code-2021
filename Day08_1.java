import java.util.Arrays;

/**
 * Part one of
 * https://adventofcode.com/2021/day/8
 *
 * @author Nerijus
 */
public class Day08_1 {
    public static void main(String[] args) {
        System.out.println("Digits 1, 4, 7, or 8 appear: " + new Day08_1().getResult());
    }

    private long getResult() {
        return Inputs.readStrings("Day08")
                .stream()
                .map(row -> row.split(" \\| ")[1])
                .flatMap(output -> Arrays.stream(output.split(" ")))
                .mapToInt(String::length)
                .filter(partSize -> Arrays.asList(2, 3, 4, 7).contains(partSize))
                .count();
    }
}