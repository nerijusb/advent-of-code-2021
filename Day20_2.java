/**
 * Part two of
 * https://adventofcode.com/2021/day/20
 *
 * @author Nerijus
 */
public class Day20_2 extends Day20_1 {
    public static void main(String[] args) {
        System.out.println("Pixels are lit in the resulting image: " + new Day20_2().getResult());
    }

    private int getResult() {
        String[] parts = Inputs.readString("Day20").split("\n\n");
        Algorithm algorithm = new Algorithm(parts[0]);
        String[][] image = shift(readImage(parts[1]), 240);
        print(image);
        System.out.println();

        for (int i = 0; i < 50; i++) {
            image = enhance(image, algorithm);
        }
        // this approach produces lit borders, so subtract them from the final image
        image = crop(image, 240);
        print(image);

        return countLitPixels(image);
    }
}