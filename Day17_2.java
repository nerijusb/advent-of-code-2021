import common.Coordinates;

/**
 * Part two of
 * https://adventofcode.com/2021/day/17
 *
 * @author Nerijus
 */
public class Day17_2 extends Day17_1 {
    public static void main(String[] args) {
        System.out.println("Distinct initial velocity values: " + new Day17_2().getResult());
    }

    private int getResult() {
        TargetArea area = readTargetArea("Day17");
        int count = 0;
        for (int xVelocity = -500; xVelocity < 500; xVelocity++) {
            for (int yVelocity = -500; yVelocity < 500; yVelocity++) {
                Coordinates probe = new Coordinates(0, 0);
                Coordinates velocity = new Coordinates(xVelocity, yVelocity);
                do {
                    step(probe, velocity);
                    if (area.isIn(probe)) {
                        count++;
                        break;
                    }
                } while (!area.isOver(probe));
            }
        }
        return count;
    }
}