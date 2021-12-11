import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Part two of
 * https://adventofcode.com/2021/day/10
 *
 * @author Nerijus
 */
public class Day10_2 extends Day10_1 {
    private static final Map<String, Integer> SCORES = Map.of(")", 1, "]", 2, "}", 3, ">", 4);

    public static void main(String[] args) {
        System.out.println("Middle score: " + new Day10_2().getResult());
    }

    private Long getResult() {
        List<Long> scores = Inputs.readStrings("Day10")
                .stream()
                .mapToLong(this::scoreFor)
                .filter(score -> score > 0)
                .sorted()
                .boxed()
                .collect(Collectors.toList());

        return scores.get(scores.size() / 2);
    }

    private long scoreFor(String line) {
        List<String> chars = List.of(line.split(""));
        LinkedList<String> stack = new LinkedList<>();
        for (String aChar : chars) {
            if (isOpenChar(aChar)) {
                stack.push(aChar);
            } else {
                String openTag = stack.pop();
                if (isMismatch(openTag, aChar)) {
                    return 0;
                }
            }
        }
        return scoreForIncomplete(String.join("", stack));
    }

    private long scoreForIncomplete(String line) {
        String missing = line
                .replaceAll("\\{", "}")
                .replaceAll("\\[", "]")
                .replaceAll("\\(", ")")
                .replaceAll("<", ">");

        long score = 0;
        for (String missingChar : missing.split("")) {
            score = score * 5;
            score = score + SCORES.get(missingChar);
        }

        return score;
    }
}