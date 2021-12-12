import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Part two of
 * https://adventofcode.com/2021/day/12
 *
 * @author Nerijus
 */
public class Day12_2 extends Day12_1 {
    public static void main(String[] args) {
        System.out.println("Paths through this cave system: " + new Day12_2().getResult());
    }

    private int getResult() {
        List<Link> links = Inputs.readStrings("Day12")
                .stream()
                .map(Link::new)
                .collect(Collectors.toList());

        return getSmallCaves(links)
                .stream()
                .flatMap(sc -> new PathFinder(links, sc).findAll().stream())
                .collect(Collectors.toSet())
                .size();
    }

    private Set<String> getSmallCaves(List<Link> links) {
        return links
                .stream()
                .flatMap(l -> Stream.of(l.from, l.to))
                .filter(cave -> isSmall(cave) && !isStart(cave) && !isEnd(cave))
                .collect(Collectors.toSet());
    }
}