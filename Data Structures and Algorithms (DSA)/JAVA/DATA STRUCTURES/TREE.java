import java.util.*;

public class TREE {
  private ArrayList<NODE> nodesInTree;
  private NODE rootNode;

  public TREE(ArrayList<NODE> nodesInTree, NODE rootNode) {
    this.nodesInTree = nodesInTree;
    this.rootNode = rootNode;
  }

  public void addNode(NODE node){
    nodesInTree.add(node);
  }
  public NODE getRootNode(){
    return rootNode;
  }
  public ArrayList<NODE> getNodesInTree(){
    return nodesInTree;
  }
}
