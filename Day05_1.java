import common.Coordinates;
import common.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Part one of
 * https://adventofcode.com/2021/day/5
 *
 * @author Nerijus
 */
public class Day05_1 {
    private static final Pattern LINE_PATTERN = Pattern.compile("(?<x1>\\d+),(?<y1>\\d+) -> (?<x2>\\d+),(?<y2>\\d+)");

    public static void main(String[] args) {
        System.out.println("Points where at least two lines overlap: " + new Day05_1().getResult());
    }

    private int getResult() {
        List<Line> lines = readLines().stream().filter(Line::isHorizontalOrVertical).collect(Collectors.toList());
        int[][] map = prepareMap(lines);

        drawLines(map, lines);

        print(map);

        return countOverlap(map);
    }

    void print(int[][] map) {
        for (int[] row : map) {
            for (int cell : row) {
                if (cell == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(cell);
                }
            }
            System.out.print("\n");
        }
    }

    int[][] prepareMap(List<Line> lines) {
        int maxX = lines.stream().flatMapToInt(l -> IntStream.of(l.from.x, l.to.x)).max().stream().findFirst().orElse(-1);
        int maxY = lines.stream().flatMapToInt(l -> IntStream.of(l.from.y, l.to.y)).max().stream().findFirst().orElse(-1);
        return new int[maxY + 1][maxX + 1];
    }

    int countOverlap(int[][] map) {
        int count = 0;
        for (int[] row : map) {
            for (int cell : row) {
                if (cell > 1) {
                    count++;
                }
            }
        }
        return count;
    }

    void drawLines(int[][] map, List<Line> lines) {
        lines.forEach(line -> {
            if (line.isHorizontal()) {
                for (int x = Math.min(line.from.x, line.to.x); x <= Math.max(line.from.x, line.to.x); x++) {
                    map[line.from.y][x] = map[line.from.y][x] + 1;
                }
            } else if (line.isVertical()) {
                for (int y = Math.min(line.from.y, line.to.y); y <= Math.max(line.from.y, line.to.y); y++) {
                    map[y][line.from.x] = map[y][line.from.x] + 1;
                }
            } else {
                Integer[] xRange = range(line.from.x, line.to.x);
                Integer[] yRange = range(line.from.y, line.to.y);
                for (int i = 0; i < yRange.length; i++) {
                    map[yRange[i]][xRange[i]] = map[yRange[i]][xRange[i]] + 1;
                }
            }
        });
    }

    private Integer[] range(int from, int to) {
        List<Integer> range = new ArrayList<>();
        if (from < to) {
            for (int i = from; i <= to; i++) {
                range.add(i);
            }
        } else {
            for (int i = from; i >= to; i--) {
                range.add(i);
            }
        }
        return range.toArray(Integer[]::new);
    }

    List<Line> readLines() {
        return Inputs.readStrings("Day05").stream().map(source -> {
            Matcher matcher = LINE_PATTERN.matcher(source);
            if (matcher.find()) {
                int x1 = Integer.parseInt(matcher.group("x1"));
                int y1 = Integer.parseInt(matcher.group("y1"));
                int x2 = Integer.parseInt(matcher.group("x2"));
                int y2 = Integer.parseInt(matcher.group("y2"));
                return new Line(x1, y1, x2, y2);
            }
            throw new IllegalStateException("Could not parse line");
        }).collect(Collectors.toList());
    }
}