import java.util.ArrayList;

// a Stack (LIFO last in first out)

public class STACK<T> {
    private ArrayList<T> stack = new ArrayList<T>();
    private int pointer = 0;

    public push(T t) {
      if(!stack.isEmpty()){
        pointer++;
      }
      stack.add(t)
    }

    public T pop() {
      if(!stack.isEmpty()){
        T t = stack.get(pointer);
        stack.remove(pointer);
        pointer--;
        return t;
      }
      return null;
    }

    public T peek() {
      return stack.get(pointer)
    }

    @Override
    public String toString() {
      String returnString = "";
      for (T t : stack) {
        returnString += t.toString();
      }
        return returnString;
    }
}
