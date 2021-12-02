import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Part one of
 * https://adventofcode.com/2021/day/2
 *
 * @author Nerijus
 */
public class Day02_1
{
    public static void main(String[] args)
    {
        System.out.println("Horizontal position X final depth = " + new Day02_1().getResult());
    }

    private int getResult()
    {
        List<Instruction> instructions = readInstructions();

        int horizontalPosition = 0;
        int depth = 0;

        for (Instruction instruction : instructions)
        {
            switch (instruction.direction)
            {
                case FORWARD -> horizontalPosition = horizontalPosition + instruction.amount;
                case UP -> depth = depth - instruction.amount;
                case DOWN -> depth = depth + instruction.amount;
            }
        }

        return horizontalPosition * depth;
    }

    List<Instruction> readInstructions()
    {
        return Inputs.readStrings("Day02")
                .stream()
                .map(i -> {
                    String[] parts = i.split(" ");
                    InstructionDirection direction = InstructionDirection.valueOf(parts[0].toUpperCase(Locale.ROOT));
                    int amount = Integer.parseInt(parts[1]);
                    return new Instruction(direction, amount);
                })
                .collect(Collectors.toList());
    }

    static class Instruction
    {
        InstructionDirection direction;
        int amount;

        Instruction(InstructionDirection direction, int amount)
        {
            this.direction = direction;
            this.amount = amount;
        }
    }

    enum InstructionDirection
    {
        FORWARD,
        UP,
        DOWN
    }
}