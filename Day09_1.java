import java.util.ArrayList;
import java.util.List;

import common.Coordinates;

/**
 * Part one of
 * https://adventofcode.com/2021/day/9
 *
 * @author Nerijus
 */
public class Day09_1 {
    public static void main(String[] args) {
        System.out.println("Sum of the risk levels: " + new Day09_1().getResult());
    }

    private long getResult() {
        int[][] map = Inputs.readIntMap("Day09");
        return calculateRiskLevel(map);
    }

    private long calculateRiskLevel(int[][] map)
    {
        List<Integer> lowPoints = new ArrayList<>();
        for (int y = 0; y < map.length; y++)
        {
            int[] row = map[y];
            for (int x = 0; x < row.length; x++)
            {
                int level = row[x];
                boolean lowPoint = new Coordinates(x, y)
                        .allValidAdjacent(row.length, map.length)
                        .stream()
                        .allMatch(ap -> map[ap.y][ap.x] > level);
                if (lowPoint) {
                    lowPoints.add(level);
                }
            }
        }
        return lowPoints.stream().mapToInt(i -> i + 1).sum();
    }
}