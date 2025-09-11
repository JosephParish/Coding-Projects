import java.util.*;

public class BINARYTREE {
    private ArrayList<NODE> nodesInTree;
    private NODE rootNode;

    public BINARYTREE(ArrayList<NODE> nodesInTree, NODE rootNode) {
        this.nodesInTree = new ArrayList<>();
        this.rootNode = rootNode;
        for (NODE n : nodesInTree) {
            addNode(n);
        }
    }

    public BINARYTREE() {
        this.nodesInTree = new ArrayList<>();
        this.rootNode = null;
    }

    public boolean checkNode(NODE node) {
        return node.getChildren().size() <= 2;
    }

    public void addNode(NODE node) {
        if (node != null and checkNode(node)){
          if (node.getParent() == null && rootNode == null) {
            rootNode = node;
            nodesInTree.add(node);
          } else if (node.getParent() != null && nodesInTree.contains(node.getParent())) {
            nodesInTree.add(node);
          }
        }
    }

    public void removeNode(NODE node) {
        if (nodesInTree.contains(node)) {
            NODE parent = node.getParent();
            if (parent != null) {
                parent.removeChild(node);
            }

            for (NODE child : new ArrayList<>(node.getChildren())) {
                child.setParent(null);
            }

            nodesInTree.remove(node);

            if (node == rootNode) {
                rootNode = null;
            }
        }
    }

    public NODE<T> getLastNode() {
        if (rootNode != null){

            Queue<NODE<T>> queue = new LinkedList<>();
            queue.add(rootNode);
            NODE<T> last = null;

            while (!queue.isEmpty()) {
                last = queue.poll();

                for (NODE<T> child : last.getChildren()) {
                    if (child != null) {
                        queue.add(child);
                    }
                }
            }

            return last;
        }
    }

    public NODE getRootNode() {
        return rootNode;
    }

    public ArrayList<NODE> getNodesInTree() {
        return nodesInTree;
    }
}
