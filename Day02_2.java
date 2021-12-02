import java.util.List;

/**
 * Part two of
 * https://adventofcode.com/2021/day/2
 *
 * @author Nerijus
 */
public class Day02_2 extends Day02_1 {
    public static void main(String[] args) {
        System.out.println("Horizontal position X final depth = " + new Day02_2().getResult());
    }

    private int getResult() {
        List<Instruction> instructions = readInstructions();

        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (Instruction instruction : instructions)
        {
            switch (instruction.direction)
            {
                case UP -> aim = aim - instruction.amount;
                case DOWN -> aim = aim + instruction.amount;
                case FORWARD -> {
                    horizontalPosition = horizontalPosition + instruction.amount;
                    depth = depth + aim * instruction.amount;
                }
            }
        }
        return horizontalPosition * depth;
    }
}