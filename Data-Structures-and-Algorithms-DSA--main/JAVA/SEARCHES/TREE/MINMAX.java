import java.util.*;
import java.util.function.Function;

public class MINMAX <T> {

	private final Function<T, ArrayList<T>> getChildren;
	private final Function<T, Double> evaluate;

	public MINMAX(Function<T, ArrayList<T>> getChildren, Function<T, Double> evaluate) {
		this.getChildren = getChildren;
		this.evaluate = evaluate;
	}

    public T minimax(T node, int depth, double alpha, double beta, boolean isMax) {
    	if (depth == 0 || getChildren.apply(node).isEmpty()) {
        	return node;
    	}
    	return findBestMove(node, depth, alpha, beta, isMax);
	}
	
	private T findBestMove(T node, int depth, double alpha, double beta, boolean isMax) {
    	double bestValue = isMax ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    	T bestNode = null;

    	for (T child : getChildren.apply(node)) {
        	T result = minimax(child, depth - 1, alpha, beta, !isMax);
        	double eval = evaluate.apply(result);

        	boolean isBetter = isMax ? eval > bestValue : eval < bestValue;
        	if (isBetter) {
            	bestValue = eval;
            	bestNode = child;
        	}

        	if (isMax) {
            	alpha = Math.max(alpha, eval);
        	} else {
            	beta = Math.min(beta, eval);
        	}
			
        	if (beta <= alpha) {
            	break;
        	}
    	}
    	return bestNode;
	}
}
