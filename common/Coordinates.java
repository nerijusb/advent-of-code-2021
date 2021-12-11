package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isSame(Coordinates coordinates) {
        return isSameY(coordinates) && isSameX(coordinates);
    }

    public boolean isSameY(Coordinates coordinates) {
        return Integer.valueOf(coordinates.y).equals(y);
    }

    public boolean isSameX(Coordinates coordinates) {
        return Integer.valueOf(coordinates.x).equals(x);
    }

    public Coordinates adjacent(Direction direction) {
        return switch (direction) {
            case UP -> new Coordinates(x, y + 1);
            case DOWN -> new Coordinates(x, y - 1);
            case LEFT -> new Coordinates(x - 1, y);
            case RIGHT -> new Coordinates(x + 1, y);
        };
    }

    public List<Coordinates> allAdjacent() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            coordinates.add(adjacent(direction));
        }
        return coordinates;
    }

    public List<Coordinates> allValidAdjacentAndDiagonal() {
        List<Coordinates> coordinates = allAdjacent();
        // top left
        coordinates.add(new Coordinates(x - 1, y + 1));
        // top right
        coordinates.add(new Coordinates(x + 1, y + 1));
        // bottom left
        coordinates.add(new Coordinates(x - 1, y - 1));
        // bottom right
        coordinates.add(new Coordinates(x + 1, y - 1));
        return coordinates;
    }

    public List<Coordinates> allValidAdjacentAndDiagonal(int maxX, int maxY) {
        return allValidAdjacentAndDiagonal()
                .stream()
                .filter(c -> c.x >= 0 && c.y >= 0 && c.x < maxX && c.y < maxY)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ')';
    }
}
