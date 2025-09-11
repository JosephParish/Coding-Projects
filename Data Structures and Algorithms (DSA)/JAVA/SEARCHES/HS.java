import java.util.*;

public class HS <T> {
  
  // hill climbing search
  public static int hillSearch(Function<T, T> fitness, Function<T> get_neighbors, T start) {
	  T x = start; 
	  while (true) {
		List<T> neighbors = get_neighbors(x);
		T best_neighbor = Collections.max(neighbors,Comparator.comparingInt(fitness::apply)); 
		if (fitness.apply(best_neighbor) <= fitness.apply(x)) {
			return x;
      		}
		x = best_neighbor;
	  }
  }
}
