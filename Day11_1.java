import common.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Part one of
 * https://adventofcode.com/2021/day/11
 *
 * @author Nerijus
 */
public class Day11_1 {
    public static void main(String[] args) {
        System.out.println("Total flashes after 100 steps: " + new Day11_1().getResult());
    }

    private long getResult() {
        int[][] map = readMap();

        int totalFlashes = 0;
        for (int i = 0; i < 100; i++) {
            totalFlashes = totalFlashes + executeStep(map);
            System.out.println("Step: " + (i + 1));
            print(map);
        }


        return totalFlashes;
    }

    int executeStep(int[][] map) {
        List<Coordinates> flashes = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int value = map[y][x];
                if ((value + 1) > 9) {
                    flashes.add(new Coordinates(x, y));
                    value = 0;
                } else {
                    value = value + 1;
                }
                map[y][x] = value;
            }
        }
        int totalFlashes = flashes.size();
        while (flashes.size() > 0) {
            flashes = handleFlashes(map, flashes);
            totalFlashes = totalFlashes + flashes.size();
        }
        return totalFlashes;
    }

    private List<Coordinates> handleFlashes(int[][] map, List<Coordinates> flashes) {
        List<Coordinates> newFlashes = new ArrayList<>();
        flashes.forEach(flash -> {
            List<Coordinates> adjacent = flash.allValidAdjacentAndDiagonal(map[0].length, map.length);
            adjacent.forEach(a -> {
                if (map[a.y][a.x] != 0) {
                    map[a.y][a.x] = map[a.y][a.x] + 1;
                }
                if (map[a.y][a.x] > 9) {
                    map[a.y][a.x] = 0;
                    newFlashes.add(new Coordinates(a.x, a.y));
                }
            });
        });
        return newFlashes;
    }

    int[][] readMap() {
        List<String> rows = Inputs.readStrings("Day11");
        int[][] map = new int[rows.size()][rows.get(0).length()];
        for (int y = 0; y < rows.size(); y++) {
            String[] row = rows.get(y).split("");
            for (int x = 0; x < row.length; x++) {
                map[y][x] = Integer.parseInt(row[x]);
            }
        }
        return map;
    }

    void print(int[][] map) {
        for (int[] row : map) {
            for (int cell : row) {
                System.out.print(cell);
            }
            System.out.print("\n");
        }
    }
}