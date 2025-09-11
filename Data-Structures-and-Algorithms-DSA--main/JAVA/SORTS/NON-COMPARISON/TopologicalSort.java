//must be for a dag (directed acyclic graph)
public class TopologicalSort {
  
  public static int[] topologicalSort(int V, int[][] edges) {

    Stack<Integer> stack = new Stack<>();
    boolean[] visited = new boolean[V];


    List<Integer>[] adjacencyList = new ArrayList<>[];
    for (int i = 0; i < V; i++) {
      adj[i] = new ArrayList<>();
    } for (int[] edge : edges) {
      adj[edge[0]].add(edge[1]);
    }

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        recursiveSortUtil(i, adj, visited, stack);
      }
    }

    int index = 0;
    int[] output = new int[V];
    
    while (!stack.isEmpty()) {
      output[index++] = stack.pop();
    }
    return output;
  }
  
  private recursiveSortUtil(int V, List<Integer>[] adj, boolean[] visited, Stack<Integer> stack) {

    visited[v] = true;

    for (int i : adj[v]) {
      if (!visited[i]) {
        topologicalSortUtil(i, adj, visited, stack);
      }
    }
    stack.push(v);
  }
}
