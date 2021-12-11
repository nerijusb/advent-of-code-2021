import common.Coordinates;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Part two of
 * https://adventofcode.com/2021/day/9
 *
 * @author Nerijus
 */
public class Day09_2 extends Day09_1 {
    public static void main(String[] args) {
        System.out.println("Multiplication of three largest basins: " + new Day09_2().getResult());
    }

    private long getResult() {
        List<String> rows = Inputs.readStrings("Day09");
        Location[][] map = new Location[rows.size()][rows.get(0).length()];
        for (int y = 0; y < rows.size(); y++) {
            String[] points = rows.get(y).split("");
            for (int x = 0; x < points.length; x++) {
                map[y][x] = new Location(new Coordinates(x, y), Integer.parseInt(points[x]));
            }
        }
        return calculate(map);
    }

    private long calculate(Location[][] map) {
        List<Long> basins = getLowPoints(map)
                .stream()
                .map(location -> basinSize(location, map))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        return basins.get(0) * basins.get(1) * basins.get(2);
    }

    private long basinSize(Location startLocation, Location[][] map) {
        Set<Location> basin = new HashSet<>();
        basin.add(startLocation);
        List<Location> newAdjacent = new ArrayList<>(List.of(startLocation));
        while (newAdjacent.size() > 0) {
            newAdjacent = newAdjacent
                    .stream()
                    .map(na -> na.coordinates)
                    .flatMap(c -> c.allValidAdjacent(map[0].length, map.length).stream())
                    .map(a -> map[a.y][a.x])
                    .filter(adjacentLocation -> adjacentLocation.value < 9)
                    .filter(location -> !basin.contains(location))
                    .collect(Collectors.toList());
            basin.addAll(newAdjacent);
        }
        return basin.size();
    }

    private Set<Location> getLowPoints(Location[][] map) {
        Set<Location> lowPoints = new HashSet<>();
        for (int y = 0; y < map.length; y++) {
            Location[] row = map[y];
            for (int x = 0; x < row.length; x++) {
                Location loc = row[x];
                List<Coordinates> adjacent = new Coordinates(x, y).allAdjacent();
                if (adjacent.stream()
                        .filter(a -> a.x >= 0 && a.x < row.length && a.y >= 0 && a.y < map.length)
                        .allMatch(ap -> map[ap.y][ap.x].value > loc.value)) {
                    lowPoints.add(loc);
                }
            }
        }
        return lowPoints;
    }

    static class Location {
        Coordinates coordinates;
        int value;

        Location(Coordinates coordinates, int value) {
            this.coordinates = coordinates;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Location location = (Location) o;
            return coordinates.equals(location.coordinates);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordinates);
        }

        @Override
        public String toString() {
            return coordinates.toString() + ": " + value;
        }
    }
}