import common.Coordinates;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * https://adventofcode.com/2021/day/13
 *
 * @author Nerijus
 */
public class Day13_1 {
    public static void main(String[] args) {
        System.out.println("Dots visible after completing just the first fold: " + new Day13_1().getResult());
    }

    private int getResult() {
        List<Instruction> instructions = readInstructions();
        String[][] map = readMap();
        fold(map, instructions.get(0));

        return Arrays.stream(map)
                .flatMap(Arrays::stream)
                .mapToInt(cell -> cell.equals("#") ? 1 : 0)
                .sum();
    }

    List<Instruction> readInstructions() {
        String instructions = Inputs.readString("Day13").split("\n\n")[1];
        return Arrays.stream(instructions.split("\n"))
                .map(i -> i.split(" ")[2])
                .map(i -> i.split("="))
                .map(i -> new Instruction(i[0], Integer.parseInt(i[1])))
                .collect(Collectors.toList());
    }

    String[][] readMap() {
        String points = Inputs.readString("Day13").split("\n\n")[0];
        List<Coordinates> coordinates =
                Arrays.stream(points.split("\n"))
                        .map(p -> p.split(","))
                        .map(p -> new Coordinates(Integer.parseInt(p[0]), Integer.parseInt(p[1])))
                        .collect(Collectors.toList());

        int maxX = coordinates.stream().mapToInt(c -> c.x).max().orElseThrow();
        int maxY = coordinates.stream().mapToInt(c -> c.y).max().orElseThrow();
        int dimension = Math.max(maxX, maxY);

        String[][] map = new String[dimension + 4][dimension + 4];
        for (String[] strings : map) {
            Arrays.fill(strings, ".");
        }
        coordinates.forEach(c -> map[c.y][c.x] = "#");

        return map;
    }

    void fold(String[][] map, Instruction instruction) {
        if (instruction.axis.equals("y")) {
            for (int mapY = 0; mapY < instruction.value; mapY++) {
                for (int x = 0; x < map[mapY].length; x++) {
                    if (map[instruction.value + (instruction.value - mapY)][x].equals("#")) {
                        map[mapY][x] = map[instruction.value + (instruction.value - mapY)][x];
                        map[instruction.value + (instruction.value - mapY)][x] = ".";
                    }
                }
            }
        } else {
            for (int y = 0; y < map[0].length; y++) {
                for (int mapX = 0; mapX < instruction.value; mapX++) {
                    if (map[y][instruction.value + (instruction.value - mapX)].equals("#")) {
                        map[y][mapX] = map[y][instruction.value + (instruction.value - mapX)];
                        map[y][instruction.value + (instruction.value - mapX)] = ".";
                    }
                }
            }
        }
    }

    static class Instruction {
        String axis;
        int value;

        public Instruction(String axis, int value) {
            this.axis = axis;
            this.value = value;
        }
    }
}