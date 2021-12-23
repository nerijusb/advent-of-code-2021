import common.Coordinates;

/**
 * Part one of
 * https://adventofcode.com/2021/day/20
 *
 * @author Nerijus
 */
public class Day20_1 {
    public static void main(String[] args) {
        System.out.println("Pixels are lit in the resulting image: " + new Day20_1().getResult());
    }

    private int getResult() {
        String[] parts = Inputs.readString("Day20").split("\n\n");
        Algorithm algorithm = new Algorithm(parts[0]);
        String[][] image = shift(readImage(parts[1]), 10);
        print(image);
        System.out.println();

        for (int i = 0; i < 2; i++) {
            image = enhance(image, algorithm);
        }
        // this approach produces lit borders, so subtract them from the final image
        image = crop(image, 108);
        print(image);

        return countLitPixels(image);
    }

    String[][] crop(String[][] image, int size) {
        int offset = (image.length - size) / 2;
        String[][] croppedImage = new String[size][size];
        for (int y = 0; y < croppedImage.length; y++) {
            for (int x = 0; x < croppedImage[y].length; x++) {
                croppedImage[y][x] = image[y+offset][x+offset];
            }
        }
        return croppedImage;
    }

    String[][] readImage(String image) {
        String[] rows = image.split("\n");
        String[][] map = new String[rows.length][rows[0].length()];
        for (int y = 0; y < rows.length; y++) {
            String[] points = rows[y].split("");
            for (int x = 0; x < points.length; x++) {
                map[y][x] = points[x];
            }
        }
        return map;
    }

    String[][] shift(String[][] image, int offset) {
        String[][] newImage = new String[image.length + offset][image[0].length + offset];
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                newImage[y + (offset / 2)][x + (offset / 2)] = image[y][x];
            }
        }
        for (int y = 0; y < newImage.length; y++) {
            for (int x = 0; x < newImage[y].length; x++) {
                newImage[y][x] = newImage[y][x] == null ? "." : newImage[y][x];
            }
        }
        return newImage;
    }

    void print(String[][] map) {
        for (String[] row : map) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.print("\n");
        }
    }

    int countLitPixels(String[][] image) {
        int count = 0;
        for (String[] rows : image) {
            for (String pixel : rows) {
                if ("#".equals(pixel)) {
                    count++;
                }
            }
        }
        return count;
    }

    String[][] enhance(String[][] image, Algorithm algorithm) {
        String[][] newImage = new String[image.length][image[0].length];
        for (int y = 0; y < newImage.length; y++) {
            for (int x = 0; x < newImage[y].length; x++) {
                newImage[y][x] = enhancedPixel(image, algorithm, x, y);
            }
        }
        return newImage;
    }

    private String enhancedPixel(String[][] image, Algorithm algorithm, int x, int y) {
        Coordinates c = new Coordinates(x, y);
        String binaryIndex = toBinary(
                pixel(image, c.bottomLeft()) + pixel(image, c.bottom()) + pixel(image, c.bottomRight()) +
                        pixel(image, c.left()) + pixel(image, c) + pixel(image, c.right()) +
                        pixel(image, c.topLeft()) + pixel(image, c.top()) + pixel(image, c.topRight()));
        return algorithm.get(binaryIndex);
    }

    private String toBinary(String pixels) {
        return pixels.replaceAll("\\.", "0").replaceAll("#", "1");
    }

    private String pixel(String[][] image, Coordinates position) {
        if (position.y < 0 || position.y >= image.length) {
            return ".";
        }
        if (position.x < 0 || position.x >= image[0].length) {
            return ".";
        }
        String pixel = image[position.y][position.x];
        return pixel == null ? "." : pixel;
    }

    static class Algorithm {
        String[] pixels;

        public Algorithm(String source) {
            pixels = source.split("");
        }

        String get(String binaryIndex) {
            return pixels[Integer.parseInt(binaryIndex, 2)];
        }
    }
}