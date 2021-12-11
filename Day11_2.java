/**
 * Part two of
 * https://adventofcode.com/2021/day/11
 *
 * @author Nerijus
 */
public class Day11_2 extends Day11_1 {
    public static void main(String[] args) {
        System.out.println("First step during which all octopuses flash: " + new Day11_2().getResult());
    }

    private int getResult() {
        int[][] map = readMap();
        int step = 0;
        while (true) {
            step++;
            executeStep(map);
            if (allFlash(map)) {
                return step;
            }
        }
    }

    private boolean allFlash(int[][] map) {
        for (int[] row : map) {
            for (int cell : row) {
                if (cell != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}