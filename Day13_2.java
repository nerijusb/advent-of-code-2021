import java.util.Arrays;
import java.util.List;

/**
 * Part two of
 * https://adventofcode.com/2021/day/13
 *
 * @author Nerijus
 */
public class Day13_2 extends Day13_1 {
    public static void main(String[] args) {
        new Day13_2().getResult();
    }

    private void getResult() {
        List<Instruction> instructions = readInstructions();
        String[][] map = readMap();
        instructions.forEach(i -> fold(map, i));
        print(map);
    }

    void print(String[][] map) {
        for (String[] row : map) {
            if (Arrays.stream(row).noneMatch("#"::equals)) {
                return;
            }
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.print("\n");
        }
    }
}