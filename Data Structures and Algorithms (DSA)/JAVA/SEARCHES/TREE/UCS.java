import java.util.*;

public class UniformCostSearch<T> {

    public List<Integer> uniformCostSearch(List<T> goals, T start, Map<T, List<T>> graph, Map<List<T>, Integer> costMap) {
        HashMap<T, Integer> answer = new HashMap<>();
        for (T goal : goals) {
            answer.put(goal, Integer.MAX_VALUE);
        }

        PriorityQueue<Tuple<Integer, T>> queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.x));
        queue.add(new Tuple<>(0, start));

        HashSet<T> visited = new HashSet<>();
        int goalsFound = 0;

        while (!queue.isEmpty()) {
            Tuple<Integer, T> current = queue.poll();

            if (visited.contains(current.y)) continue;
            visited.add(current.y);

            if (goals.contains(current.y)) {
                if (answer.get(current.y) == Integer.MAX_VALUE) {
                    goalsFound++;
                }
                if (current.x < answer.get(current.y)) {
                    answer.put(current.y, current.x);
                }
                if (goalsFound == goals.size()) {
                    break;
                }
            }

            List<T> neighbors = graph.getOrDefault(current.y, new ArrayList<>());
            for (T neighbor : neighbors) {
                int edgeCost = costMap.getOrDefault(Arrays.asList(current.y, neighbor), 0);
                queue.add(new Tuple<>(current.x + edgeCost, neighbor));
            }
        }

        List<Integer> result = new ArrayList<>();
        for (T goal : goals) {
            result.add(answer.get(goal));
        }

        return result;
    }
  
  private static class Tuple<X extends Comparable<X>, Y> implements Comparable<Tuple<X, Y>> {
      public final X x; // Cost
      public final Y y; // Node

      public Tuple(X x, Y y) {
          this.x = x;
          this.y = y;
      }

      @Override
      public int compareTo(Tuple<X, Y> other) {
          return this.x.compareTo(other.x);
      }
    }
}
