import java.util.ArrayList;

// a Queue (FIFO first in first out)

public class QUEUE<T> {
    private ArrayList<T> queue = new ArrayList<T>();

    public enqueue(T t) {
      queue.add(t)
    }

    public T dequeue() {
        if (isEmpty()) {
            return null
        }
        return queue.remove(0);
    }
    
    public T peek() {
        if (isEmpty()) {
            return null
        }
        return queue.get(0);
    }

    @Override
    public String toString() {
      String returnString = "";
      for (T t : queue) {
        returnString += t.toString();
      }
        return returnString;
    }
}
