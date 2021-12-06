import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Part two of
 * https://adventofcode.com/2021/day/6
 *
 * @author Nerijus
 */
public class Day06_2 extends Day06_1 {
    private static final int DAY_COUNT = 256;

    public static void main(String[] args) {
        System.out.println("Fish count: " + new Day06_2().getResult());
    }

    private String getResult() {
        Map<Integer, BigDecimal> state = readInitialState();

        for (int i = 0; i < DAY_COUNT; i++) {
            Map<Integer, BigDecimal> newState = empty();
            for (Integer days : state.keySet()) {
                BigDecimal fishCount = state.get(days);
                if (days == 0) {
                    // changes to 6
                    newState.put(6, newState.get(6).add(fishCount));
                    // makes new
                    newState.put(8, fishCount);
                } else {
                    // moves to another day timer
                    newState.put(days - 1, newState.get(days - 1).add(fishCount));
                }
            }
            state = newState;
        }

        return state.values().stream().reduce(BigDecimal::add).orElseGet(() -> new BigDecimal(-1)).toString();
    }

    private Map<Integer, BigDecimal> readInitialState() {
        Map<Integer, BigDecimal> counts = empty();
        Arrays.stream(Inputs.readString("Day06").split(","))
                .map(Integer::parseInt)
                .forEach(f -> counts.put(f, counts.get(f).add(new BigDecimal(1))));
        return counts;
    }

    private HashMap<Integer, BigDecimal> empty() {
        // day timer - fish count
        return new HashMap<>(Map.of(
                0, new BigDecimal(0),
                1, new BigDecimal(0),
                2, new BigDecimal(0),
                3, new BigDecimal(0),
                4, new BigDecimal(0),
                5, new BigDecimal(0),
                6, new BigDecimal(0),
                7, new BigDecimal(0),
                8, new BigDecimal(0)
        ));
    }
}