import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Part one of
 * https://adventofcode.com/2021/day/10
 *
 * @author Nerijus
 */
public class Day10_1 {
    private static final Map<String, Integer> SCORES = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);

    public static void main(String[] args) {
        System.out.println("Syntax error score: " + new Day10_1().getResult());
    }

    private int getResult() {
        return Inputs.readStrings("Day10")
                .stream()
                .mapToInt(this::scoreFor)
                .sum();
    }

    private int scoreFor(String line) {
        List<String> chars = List.of(line.split(""));
        LinkedList<String> stack = new LinkedList<>();
        for (String aChar : chars) {
            if (isOpenChar(aChar)) {
                stack.push(aChar);
            } else {
                if (stack.isEmpty()) {
                    return 0;
                } else {
                    String openTag = stack.pop();
                    if (isMismatch(openTag, aChar)) {
                        return SCORES.get(aChar);
                    }
                }
            }
        }
        return 0;
    }

    boolean isMismatch(String openTag, String closeTag) {
        return !List.of("()", "[]", "{}", "<>").contains(openTag + closeTag);
    }

    boolean isOpenChar(String aChar) {
        return List.of("(", "[", "{", "<").contains(aChar);
    }
}