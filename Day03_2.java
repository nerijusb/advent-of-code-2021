import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Part two of
 * https://adventofcode.com/2021/day/3
 *
 * @author Nerijus
 */
public class Day03_2 extends Day03_1 {
    public static void main(String[] args) {
        System.out.println("Life support rating: " + new Day03_2().getResult());
    }

    private int getResult() {
        List<String> binaryNumbers = Inputs.readStrings("Day03");
        String scrubberRating = findRating(binaryNumbers, statistics -> statistics.mostCommon(1));
        String generatorRating = findRating(binaryNumbers, statistics -> statistics.leastCommon(0));

        System.out.println("CO2 scrubber rating: " + scrubberRating);
        System.out.println("Oxygen generator rating: " + generatorRating);
        return toDecimal(scrubberRating) * toDecimal(generatorRating);
    }

    private String findRating(List<String> binaryNumbers, Function<BinaryStatistics, Integer> rule) {
        List<String> allNumbers = List.copyOf(binaryNumbers);
        String resultingBinary = "";
        while (allNumbers.size() > 1) {
            for (int position = 0; position < 12; position++) {
                BinaryStatistics statistics = getStatisticsAt(position, allNumbers);
                resultingBinary = resultingBinary + rule.apply(statistics);
                allNumbers = filterByPrefix(allNumbers, resultingBinary);
                if (allNumbers.size() == 1) {
                    break;
                }
            }
        }

        return allNumbers.get(0);
    }

    private List<String> filterByPrefix(List<String> binaryNumbers, String prefix) {
        return binaryNumbers.stream().filter(bn -> bn.startsWith(prefix)).collect(Collectors.toList());
    }
}