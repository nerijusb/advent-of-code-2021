import java.util.ArrayList;
import java.util.List;

/**
 * Part two of
 * https://adventofcode.com/2021/day/1
 *
 * @author Nerijus
 */
public class Day01_2 extends Day01_1 {
    public static void main(String[] args) {
        System.out.println("Sums larger than the previous sum: " + new Day01_2().getResult());
    }

    private int getResult() {
        List<Integer> depths = Inputs.readInts("Day01");
        List<Integer> sums = new ArrayList<>();

        for (int i = 0; i < depths.size(); i++) {
            int sum = depths.get(i);
            if (depths.size() > i + 1) {
                sum = sum + depths.get(i + 1);
            }
            if (depths.size() > i + 2) {
                sum = sum + depths.get(i + 2);
                sums.add(sum);
            }
        }
        return countLargerThanPrevious(sums);
    }
}
