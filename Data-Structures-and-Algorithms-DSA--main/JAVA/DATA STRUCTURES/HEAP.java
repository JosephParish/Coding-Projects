import java.util.*;

public class HEAP<T extends Comparable<T>> {
    private BINARYTREE<T> binaryTree;
    private boolean isMinHeap;
    private Comparator<T> comparator;

    public HEAP(BINARYTREE<T> binaryTree, boolean isMin) {
        this.binaryTree = binaryTree;
        this.isMinHeap = isMin;
        this.comparator = isMin ? Comparator.naturalOrder() : Comparator.reverseOrder();
    }

    private void heapifyDown(NODE<T> node) {
        while (node != null) {
          List<NODE<T>> children = node.getChildren();
          NODE<T> selected = node;

          for (NODE<T> child : children) {
              if (child != null && comparator.compare(child.getValue(), selected.getValue()) < 0) {
                  selected = child;
              }
          }

          if (selected == node) break;

          T temp = node.getValue();
          node.setValue(selected.getValue());
          selected.setValue(temp);

          node = selected;
      }
  }

    private void sortHeap() {
        List<NODE<T>> nodes = binaryTree.getNodesInLevelOrder();
        for (int i = nodes.size() - 1; i >= 0; i--) {
            heapifyDown(nodes.get(i));
        }
    }

    public void add(T value) {
        NODE<T> newNode = new NODE<>(value);
        binaryTree.addNode(newNode);
    }

    public void remove(NODE<T> node) {
        NODE<T> last = binaryTree.getLastNode();

        if (last == null || node == null) return;

        if (node == last) {
            binaryTree.removeNode(last);
            return;
        }

        node.setValue(last.getValue());
        binaryTree.removeNode(last);
        heapifyDown(node);
    }

    public NODE<T> getRoot() {
        return binaryTree.getRootNode();
    }

    public boolean getIsMin() {
        return isMinHeap;
    }

    public BINARYTREE<T> getHeap() {
        return binaryTree;
    }
}
