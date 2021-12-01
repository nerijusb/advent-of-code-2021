import java.util.List;

/**
 * Part one of
 * https://adventofcode.com/2021/day/1
 *
 * @author Nerijus
 */
public class Day01_1 {
    public static void main(String[] args) {
        System.out.println("Measurements larger than the previous measurement: " + new Day01_1().getResult());
    }

    private int getResult() {
        return countLargerThanPrevious(Inputs.readInts("Day01"));
    }

    int countLargerThanPrevious(List<Integer> depths) {
        int current = depths.get(0);
        int total = 0;
        for (Integer depth : depths) {
            if (depth > current) {
                total++;
            }
            current = depth;
        }
        return total;
    }
}
