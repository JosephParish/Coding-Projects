import java.util.ArrayList;

public class DFS<T> {

    public static <T> ArrayList<T> dfs(ArrayList<ArrayList<T>> adj, T source, T target) {
        
        ArrayList<Boolean> visited = new ArrayList<>(adj.size());
        visited.fill(false);
        ArrayList<T> traversal = new ArrayList<>();
        
        dfsUtil(adj, visited, source, traversal, target);
        return traversal;
    }

    private static <T> boolean dfsUtil(ArrayList<ArrayList<T>> adj, ArrayList<Boolean> visited, T currentNode, ArrayList<T> traversal, T target) {
        
        int currentIndex = adj.indexOf(currentNode);
        if (currentIndex == -1) return false;
        
        visited.set(currentIndex, true);
        traversal.add(currentNode);
        
        if (currentNode.equals(target)) {
            return true;
        }

        for (T neighbor : adj.get(currentIndex)) {
            int neighborIndex = adj.indexOf(neighbor);
            if (!visited.get(neighborIndex)) {
                dfsUtil(adj, visited, neighbor, traversal, target);
            }
        }

        return false;
    }
}
