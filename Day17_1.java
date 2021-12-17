import common.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part one of
 * https://adventofcode.com/2021/day/17
 *
 * @author Nerijus
 */
public class Day17_1 {
    private static final Pattern INPUT_PATTERN = Pattern.compile("target area: x=(?<xFrom>-?\\d+)..(?<xTo>\\d+), y=(?<yFrom>-?\\d+)..(?<yTo>-?\\d+)");

    public static void main(String[] args) {
        System.out.println("Highest y position: " + new Day17_1().getResult());
    }

    private int getResult() {
        TargetArea area = readTargetArea("Day17");
        List<Integer> maxYs = new ArrayList<>();
        for (int xVelocity = -100; xVelocity < 100; xVelocity++) {
            for (int yVelocity = -100; yVelocity < 100; yVelocity++) {
                int maxY = 0;
                Coordinates probe = new Coordinates(0, 0);
                Coordinates velocity = new Coordinates(xVelocity, yVelocity);
                do {
                    step(probe, velocity);
                    maxY = Math.max(maxY, probe.y);
                    if (area.isIn(probe)) {
                        maxYs.add(maxY);
                        System.out.printf("In target: (%d,%d), max Y: %d%n", xVelocity, yVelocity, maxY);
                        break;
                    }
                } while (!area.isOver(probe));
            }
        }
        return maxYs.stream().mapToInt(i -> i).max().orElse(-1);
    }

    void step(Coordinates probe, Coordinates velocity) {
        probe.x = probe.x + velocity.x;
        probe.y = probe.y + velocity.y;
        if (velocity.x > 0) {
            velocity.x = velocity.x - 1;
        } else if (velocity.x < 0) {
            velocity.x = velocity.x + 1;
        }
        velocity.y = velocity.y - 1;
    }

    TargetArea readTargetArea(String input) {
        String area = Inputs.readString(input);
        Matcher matcher = INPUT_PATTERN.matcher(area);
        if (matcher.find()) {
            return new TargetArea(
                    Integer.parseInt(matcher.group("xFrom")),
                    Integer.parseInt(matcher.group("xTo")),
                    Integer.parseInt(matcher.group("yFrom")),
                    Integer.parseInt(matcher.group("yTo")));
        }
        throw new IllegalStateException("Could not read input");
    }

    static class TargetArea {
        int xFrom;
        int xTo;
        int yFrom;
        int yTo;

        public TargetArea(int xFrom, int xTo, int yFrom, int yTo) {
            this.xFrom = xFrom;
            this.xTo = xTo;
            this.yFrom = yFrom;
            this.yTo = yTo;
        }

        boolean isIn(Coordinates position) {
            return position.x >= xFrom &&
                    position.x <= xTo &&
                    position.y >= yFrom &&
                    position.y <= yTo;
        }

        boolean isOver(Coordinates position) {
            return position.x > xTo || position.y < yFrom;
        }
    }
}