import common.Line;

import java.util.List;

/**
 * Part two of
 * https://adventofcode.com/2021/day/5
 *
 * @author Nerijus
 */
public class Day05_2 extends Day05_1 {
    public static void main(String[] args) {
        System.out.println("Points where at least two lines overlap: " + new Day05_2().getResult());
    }

    private int getResult() {
        List<Line> lines = readLines();
        int[][] map = prepareMap(lines);
        drawLines(map, lines);
        print(map);
        return countOverlap(map);
    }
}