import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Part one of
 * https://adventofcode.com/2021/day/12
 *
 * @author Nerijus
 */
public class Day12_1 {
    public static void main(String[] args) {
        System.out.println("Paths that go through this cave system while visiting small caves at most once: "
                + new Day12_1().getResult());
    }

    private int getResult() {
        List<Link> links = Inputs.readStrings("Day12")
                .stream()
                .map(Link::new)
                .collect(Collectors.toList());
        return new PathFinder(links)
                .findAll()
                .size();
    }

    static class PathFinder {
        List<Link> links;
        Set<String> caves;
        List<Path> allPaths = new ArrayList<>();
        String canVisitTwice;

        public PathFinder(List<Link> links) {
            this(links, null);
        }

        public PathFinder(List<Link> links, String canVisitTwice) {
            this.links = links;
            this.caves = getCaves(links);
            this.canVisitTwice = canVisitTwice;
        }

        List<Path> findAll() {
            trace("start", new Path());
            return allPaths;
        }

        private Set<String> getCaves(List<Link> links) {
            return links
                    .stream()
                    .flatMap(l -> Stream.of(l.from, l.to))
                    .collect(Collectors.toSet());
        }

        protected void trace(String nextCave, Path currentPath) {
            if (isEnd(nextCave)) {
                currentPath.add(nextCave);
                allPaths.add(currentPath);
                currentPath.print();
                return;
            }
            boolean visited = currentPath.isVisited(nextCave);
            if (isSmall(nextCave) && visited) {
                if (canVisitTwice == null || !canVisitTwice.equals(nextCave)) {
                    // cannot go back to small cave
                    return;
                }
                if (currentPath.visitedTwice != null) {
                    // already visited this once
                    return;
                }

                // allow visit twice
                currentPath.visitedTwice = nextCave;
            }

            currentPath.add(nextCave);
            getNextFor(nextCave).forEach(next -> trace(next, new Path(currentPath)));
        }

        private List<String> getNextFor(String cave) {
            return links.stream()
                    .filter(link -> link.from.equals(cave) || link.to.equals(cave))
                    .map(link -> link.from.equals(cave) ? link.to : link.from)
                    .collect(Collectors.toList());
        }
    }

    static boolean isSmall(String cave) {
        return !cave.toUpperCase().equals(cave);
    }

    static boolean isEnd(String cave) {
        return "end".equals(cave);
    }

    static boolean isStart(String cave) {
        return "start".equals(cave);
    }

    static class Path {
        List<String> path;
        String visitedTwice;

        public Path() {
            path = new ArrayList<>();
        }

        public Path(Path from) {
            this.path = new ArrayList<>(from.path);
            this.visitedTwice = from.visitedTwice;
        }

        void add(String cave) {
            path.add(cave);
        }

        private boolean isVisited(String cave) {
            return path.contains(cave);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path1 = (Path) o;
            return path.equals(path1.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path);
        }

        void print() {
            System.out.println(String.join(",", path));
        }
    }

    static class Link {
        String from;
        String to;

        public Link(String source) {
            this.from = source.split("-")[0];
            this.to = source.split("-")[1];
        }
    }
}