import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Part one of
 * https://adventofcode.com/2021/day/3
 *
 * @author Nerijus
 */
public class Day03_1 {
    public static void main(String[] args) {
        System.out.println("Power consumption of the submarine: " + new Day03_1().getResult());
    }

    private int getResult() {
        StringBuilder gammaBinary = new StringBuilder();
        StringBuilder epsilonBinary = new StringBuilder();
        List<String> binaryNumbers = Inputs.readStrings("Day03");
        for (int position = 0; position < binaryNumbers.get(0).length(); position++) {
            BinaryStatistics statistics = getStatisticsAt(position, binaryNumbers);
            gammaBinary.append(statistics.mostCommon(0));
            epsilonBinary.append(statistics.leastCommon(0));
        }

        System.out.println("Gamma rate: " + gammaBinary);
        System.out.println("Epsilon rate: " + epsilonBinary);
        return toDecimal(gammaBinary.toString()) * toDecimal(epsilonBinary.toString());
    }

    BinaryStatistics getStatisticsAt(int position, List<String> binaryNumbers) {
        List<Integer> numbers = binaryNumbers.stream().map(bn -> Integer.parseInt("" + bn.charAt(position))).collect(toList());
        long ones = numbers.stream().filter(n -> n == 1).count();
        long zeroes = numbers.stream().filter(n -> n == 0).count();
        return new BinaryStatistics(ones, zeroes);
    }

    int toDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    static class BinaryStatistics {
        long ones;
        long zeroes;

        public BinaryStatistics(long ones, long zeroes) {
            this.ones = ones;
            this.zeroes = zeroes;
        }

        int mostCommon(int ifEqual) {
            if (ones == zeroes) {
                return ifEqual;
            }
            return ones > zeroes ? 1 : 0;
        }

        int leastCommon(int ifEqual) {
            if (ones == zeroes) {
                return ifEqual;
            }
            return ones < zeroes ? 1 : 0;
        }
    }
}