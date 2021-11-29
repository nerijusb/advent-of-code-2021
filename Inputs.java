import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

/**
 * Helps reading inputs from files
 *
 * @author Nerijus
 */
final class Inputs {
    static String readString(String fileName) {
        try (InputStream input = Inputs.class.getResourceAsStream("inputs/" + fileName)) {
            if (input == null) {
                throw new IllegalStateException("Input not found: " + "inputs/" + fileName);
            }
            return new Scanner(input, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    static List<String> readStrings(String fileName) {
        return Arrays.stream(readString(fileName).split("\r\n"))
                .collect(toList());
    }

    static List<Integer> readInts(String fileName) {
        return Arrays.stream(readString(fileName).split("\r\n"))
                .map(Integer::valueOf)
                .collect(toList());
    }
}
