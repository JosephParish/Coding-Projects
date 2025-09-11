public class BFS <T> {

  public static <T> ArrayList<T> bfs(ArrayList<ArrayList<T>> adj, T source, T target) {
        ArrayList<T> traversal = new ArrayList<>();
        Queue<T> queue = new LinkedList<>();
        boolean[] visited = new boolean[adj.length()];
        
        visited[adj.indexOf(source)] = true;
        queue.add(source);
        
        while (!queue.isEmpty()) {
            T currentNode = queue.poll();
            int currentIndex = adj.indexOf(currentNode);
            traversal.add(currentNode);
            
            if (currentNode.equals(target)) {
                return traversal;
            }
          
            for (T neighbor : adj.get(currentIndex)) {
                if (!visited[neighbor] && currentNode != target) {
                    visited.set(currentIndex, true);
                    queue.add(neighbor);
                } else {
                  return traversal;
                }
            }
        }
    
        return traversal;
  }
}
