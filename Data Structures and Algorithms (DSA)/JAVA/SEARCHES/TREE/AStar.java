import java.util.*;
import java.util.function.*;

public class AStar<T> {

    public List<T> findPath(T start, T goal, Function<T, List<T>> neighborsFunction, BiFunction<T, T, Double> costFunction, BiFunction<T, T, Double> heuristicFunction) {
      
        PriorityQueue<Node<T>> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        HashMap<T, T> traversal = new HashMap<>();
        HashMap<T, Double> gScore = new HashMap<>();
        HashMap<T, Double> fScore = new HashMap<>();
        HashSet<T> closedSet = new HashSet<>();

        gScore.put(start, 0.0);
        fScore.put(start, heuristicFunction.apply(start, goal));
        openSet.add(new Node<>(start, fScore.get(start)));

        while (!openSet.isEmpty()) {
            Node<T> currentNode = openSet.poll();
            T current = currentNode.state;

            if (current.equals(goal)) {
                return reconstructPath(traversal, current);
            }

            closedSet.add(current);

            for (T neighbor : neighborsFunction.apply(current)) {
                if (closedSet.contains(neighbor)) continue;

                double estimatedG = gScore.getOrDefault(current, Double.POSITIVE_INFINITY) + costFunction.apply(current, neighbor);

                if (estimatedG < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    traversal.put(neighbor, current);
                    gScore.put(neighbor, estimatedG);
                    double estimatedF = estimatedG + heuristicFunction.apply(neighbor, goal);
                    fScore.put(neighbor, estimatedF);
                    openSet.add(new Node<>(neighbor, estimatedF));
                }
            }
        }
        return Collections.emptyList();
    }

    private List<T> reconstructPath(HashMap<T, T> traversal, T current) {
        List<T> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = traversal.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node<T> {
        T state;
        double f;

        Node(T state, double f) {
            this.state = state;
            this.f = f;
        }
    }
}
