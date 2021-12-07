import java.util.List;
import java.util.stream.IntStream;

/**
 * Part two of
 * https://adventofcode.com/2021/day/7
 *
 * @author Nerijus
 */
public class Day07_2 extends Day07_1 {
    public static void main(String[] args) {
        System.out.println("Fuel spent to align to position: " + new Day07_2().getResult());
    }

    private int getResult() {
        List<Integer> depths = readInitialState();
        return IntStream.range(min(depths), max(depths))
                .map(i -> depths.stream()
                        .mapToInt(depth -> Math.abs(depth - i))
                        .map(delta -> (delta * delta + delta) / 2)
                        .sum())
                .min()
                .orElse(-1);
    }
}