import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * https://adventofcode.com/2021/day/6
 *
 * @author Nerijus
 */
public class Day06_1 {
    private static final int DAY_COUNT = 80;

    public static void main(String[] args) {
        System.out.println("Fish count: " + new Day06_1().getResult());
    }

    private int getResult() {
        List<Fish> allFish = Arrays.stream(Inputs.readString("Day06_demo").split(","))
                .map(source -> new Fish(Integer.parseInt(source)))
                .collect(Collectors.toList());

        for (int i = 0; i < DAY_COUNT; i++) {
            List<Fish> addedFish = new ArrayList<>();
            allFish.forEach(f -> {
                Fish newFish = f.tick();
                if (newFish != null) {
                    addedFish.add(newFish);
                }
            });
            allFish.addAll(addedFish);
        }

        return allFish.size();
    }

    static class Fish {
        int timer;

        public Fish(int timer) {
            this.timer = timer;
        }

        Fish tick() {
            if (timer == 0) {
                timer = 6;
                return new Fish(8);
            }
            timer = timer - 1;
            return null;
        }
    }
}