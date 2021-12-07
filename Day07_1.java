import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Part one of
 * https://adventofcode.com/2021/day/7
 *
 * @author Nerijus
 */
public class Day07_1 {
    public static void main(String[] args) {
        System.out.println("Fuel spent to align to position: " + new Day07_1().getResult());
    }

    private int getResult() {
        List<Integer> depths = readInitialState();
        return IntStream.range(min(depths), max(depths))
                .map(i -> depths.stream().mapToInt(depth -> Math.abs(depth - i)).sum())
                .min()
                .orElse(-1);
    }

    int max(List<Integer> depths) {
        return depths.stream().mapToInt(i -> i).max().orElseThrow();
    }

    int min(List<Integer> depths) {
        return depths.stream().mapToInt(i -> i).min().orElseThrow();
    }

    List<Integer> readInitialState() {
        return Arrays.stream(Inputs.readString("Day07").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}