import java.util.*;
import java.util.stream.Collectors;

/**
 * Part one of
 * https://adventofcode.com/2021/day/4
 *
 * @author Nerijus
 */
public class Day04_1 {
    public static void main(String[] args) {
        System.out.println("Final score for first board to win: " + new Day04_1().getResult());
    }

    private int getResult() {
        List<Integer> drawnNumbers = readDrawnNumbers();
        List<BingoBoard> bingoBoards = readBoards();

        for (Integer number : drawnNumbers) {
            bingoBoards.forEach(board -> board.mark(number));
            Optional<BingoBoard> winningBoard = bingoBoards.stream().filter(BingoBoard::isWinner).findFirst();
            if (winningBoard.isPresent()) {
                return winningBoard.get().getUnmarked().stream().mapToInt(i -> i).sum() * number;
            }
        }

        return -1;
    }

    List<Integer> readDrawnNumbers() {
        return Arrays.stream(Inputs.readStrings("Day04").get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    List<BingoBoard> readBoards() {
        List<BingoBoard> boards = new ArrayList<>();
        String input = Inputs.readString("Day04");
        String[] source = input.split("\n\n");
        // skip first as it is drawn numbers
        for (int i = 1; i < source.length; i++) {
            boards.add(new BingoBoard(source[i]));
        }

        return boards;
    }

    static class BingoBoard {
        BingoNumber[][] numbers = new BingoNumber[5][5];

        public BingoBoard(String source) {
            String[] lines = source.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                String[] num = line.trim().split("[\\W]+");
                for (int j = 0; j < num.length; j++) {
                    numbers[i][j] = new BingoNumber(Integer.parseInt(num[j]));
                }
            }
        }

        void mark(int number) {
            Arrays.stream(numbers).flatMap(Arrays::stream).forEach(bn -> {
                if (bn.number == number) {
                    bn.mark();
                }
            });
        }

        boolean isWinner() {
            // check rows
            for (int i = 0; i < 5; i++) {
                if (Arrays.stream(numbers[i]).allMatch(BingoNumber::isMarked)) {
                    return true;
                }
            }
            // check columns
            for (int column = 0; column < 5; column++) {
                boolean rowMarked = true;
                for (int row = 0; row < 5; row++) {
                    if (numbers[row][column].isUnmarked()) {
                        rowMarked = false;
                    }
                }
                if (rowMarked) {
                    return true;
                }
            }
            return false;
        }

        public List<Integer> getUnmarked() {
            return Arrays.stream(numbers)
                    .flatMap(Arrays::stream)
                    .filter(BingoNumber::isUnmarked)
                    .map(bingoNumber -> bingoNumber.number)
                    .collect(Collectors.toList());
        }
    }

    static class BingoNumber {
        int number;
        boolean marked;

        public BingoNumber(int number) {
            this.number = number;
        }

        void mark() {
            this.marked = true;
        }

        public boolean isMarked() {
            return marked;
        }

        public boolean isUnmarked() {
            return !marked;
        }
    }
}