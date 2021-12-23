import java.util.*;
import java.util.stream.Collectors;

/**
 * Part one of
 * https://adventofcode.com/2021/day/14
 *
 * @author Nerijus
 */
public class Day14_1 {
    public static void main(String[] args) {
        System.out.println("" + new Day14_1().getResult());
    }

    private int getResult() {
        String[] parts = Inputs.readString("Day14").split("\n\n");
        String template = parts[0];
        Map<String, String> instructions = readInstructions(parts[1]);
        LinkedList<String> chain = new LinkedList<>(List.of(template.split("")));
        LinkedList<String> newChain = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            while (!chain.isEmpty()) {
                String first = chain.pop();
                if (chain.isEmpty()) {
                    newChain.addLast(first);
                    break;
                }
                String second = chain.peek();
                String output = instructions.get(first + second);
                newChain.addLast(first);
                if (output != null) {
                    newChain.addLast(output);
                } else {
                    newChain.addLast(second);
                }
            }
            System.out.print(i + "\n");
            newChain.forEach(System.out::print);
            System.out.print("\n");
            chain = newChain;
            newChain = new LinkedList<>();
        }

        return calculateResult(chain);
    }

    private int calculateResult(LinkedList<String> chain) {
        Set<String> letters = new HashSet<>(chain);
        int max = letters.stream().mapToInt(l -> (int) chain.stream().filter(l::equals).count()).max().orElseThrow();
        int min = letters.stream().mapToInt(l -> (int) chain.stream().filter(l::equals).count()).min().orElseThrow();
        return max - min;
    }

    Map<String, String> readInstructions(String instructions) {
        return Arrays.stream(instructions.split("\n"))
                .collect(Collectors.toMap(i -> i.split(" -> ")[0], i -> i.split(" -> ")[1]));
    }
}