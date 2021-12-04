import java.util.List;

/**
 * Part two of
 * https://adventofcode.com/2021/day/4
 *
 * @author Nerijus
 */
public class Day04_2 extends Day04_1 {
    public static void main(String[] args) {
        System.out.println("Final score for last board to win: " + new Day04_2().getResult());
    }

    private int getResult() {
        List<Integer> drawnNumbers = readDrawnNumbers();
        List<BingoBoard> bingoBoards = readBoards();

        for (Integer number : drawnNumbers) {
            bingoBoards.forEach(board -> board.mark(number));
            if (bingoBoards.size() > 1) {
                bingoBoards.removeIf(BingoBoard::isWinner);
            } else {
                BingoBoard last = bingoBoards.get(0);
                if (last.isWinner()) {
                    return last.getUnmarked().stream().mapToInt(i -> i).sum() * number;
                }
            }
        }
        return -1;
    }
}