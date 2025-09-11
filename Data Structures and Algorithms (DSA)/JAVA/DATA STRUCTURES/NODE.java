import java.util.*;

public class NODE<T> {
  private NODE parent;
  private ArrayList<NODE> children;
  private T value;
  
  public NODE(NODE parent, ArrayList<NODE> children, T value) {
    this.parent = parent;
    this.children = children;
    this.value = value;
  }

 public NODE(T value) {
    this.value = value;
  }

  public NODE getParent(){
    return parent;
  }
  public ArrayList<NODE> getChildren(){
    return children;
  }
  public T getValue(){
    return value;
  }
  public NODE setParent(NODE parent){
    this.parent = parent;
  }
  public void addChild(NODE child) {
    children.add(child);
  }
  public void removeChild(NODE child) {
    children.remove(child);
  }
}
