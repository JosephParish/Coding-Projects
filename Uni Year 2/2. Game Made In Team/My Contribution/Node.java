/**
 * <p> Nodes for the A* pathfinding.</p>
 *
 * @author Joseph.
 * @version 1.0.1
 * Last Changed: 30/11/24
 */
public class Node {
    public int x;
    public int y;
    public int g;
    public int h;
    public int f;
    public Node parent;

    /**
     * Node Constructor
     * @param x
     * @param y
     * @param parent
     */
    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    /**
     * Node Constructor (with g and h )
     * @param x
     * @param y
     * @param g
     * @param h
     * @param parent
     */
    public Node(int x, int y, int g, int h, Node parent) {
        this(x, y, parent);
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

}
