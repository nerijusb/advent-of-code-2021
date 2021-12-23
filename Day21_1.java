/**
 * Part one of
 * https://adventofcode.com/2021/day/21
 *
 * @author Nerijus
 */
public class Day21_1 {
    // private static final Pattern INPUT_PATTERN = Pattern.compile("Player (?<player>\\d) starting position: (?<position>\\d)");

    public static void main(String[] args) {
        System.out.println("Score multiplying losing player score by the number of times the die was rolled: " + new Day21_1().getResult());
    }

    private int getResult() {
        DeterministicDice dice = new DeterministicDice();
        Player player1 = new Player(4);
        Player player2 = new Player(7);

        while (true) {
            player1.move(dice.rollThree());
            System.out.println("Player 1 moves to space " + player1.position + " for a total score of " + player1.score);
            if (player1.score >= 1000) {
                return dice.rollCount * player2.score;
            }
            player2.move(dice.rollThree());
            System.out.println("Player 2 moves to space " + player2.position + " for a total score of " + player2.score);
            if (player2.score >= 1000) {
                return dice.rollCount * player1.score;
            }
        }
    }

    static class Player {
        int position;
        int score;

        public Player(int position) {
            this.position = position;
        }

        void move(int places) {
            int newPosition = (position + places) % 10;
            if (newPosition == 0) {
                position = 10;
            } else {
                position = newPosition;
            }
            score = score + position;
        }
    }

    static class DeterministicDice {
        int current = 0;
        int rollCount = 0;

        int roll() {
            rollCount++;
            if (current == 100) {
                current = 1;
            } else {
                current++;
            }
            return current;
        }

        int rollThree() {
            return roll() + roll() + roll();
        }
    }
}