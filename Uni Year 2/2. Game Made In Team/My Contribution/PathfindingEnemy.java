import java.util.*;

/**
 * <p> Super for Frog and Goblin,
 * uses A* to find the fastest path to the player.</p>
 *
 * @author Joseph.
 * @version 1.0.3
 * Last Changed: 4/12/24
 */
public abstract class PathfindingEnemy extends Enemy {
    Node start, end;
    ArrayList<Node> walkableTiles = new ArrayList<>();
    ArrayList<Node> walkedTiles = new ArrayList<>();
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};

    /**
     * PathfindingEnemy Constructor
     * @param pathToSprite
     * @param position
     */
    public PathfindingEnemy(String pathToSprite, GridPosition position) {
        super(pathToSprite, position);
    }

    /**
     * AStarAlgorithm
     * @param map
     * @param enemyPos
     * @param playerPos
     * @return path an ArrayList of coordinates in form int[]
     */
    public ArrayList<int[]> AStarAlgorithm(Map map, GridPosition enemyPos, GridPosition playerPos) {
        walkableTiles.clear();
        walkedTiles.clear();

        start = new Node(enemyPos.getX(), enemyPos.getY(), null);
        end = new Node(playerPos.getX(), playerPos.getY(), null);

        start.g = 0;
        start.h = calculateHeuristic(start.x, start.y, end.x, end.y);
        start.f = start.g + start.h;

        walkableTiles.add(start);

        // iterate through adjacent tiles not walked
        while (!walkableTiles.isEmpty()) {
            Node current = walkableTiles.get(lowestF());
            walkableTiles.remove(current);

            // if found path to player then repeat the steps to the player using parents of nodes
            if (current.x == playerPos.getX() && current.y == playerPos.getY()) {
                ArrayList<int[]> path = new ArrayList<>();
                while (current != null) {
                    path.add(new int[]{current.x, current.y});
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            walkedTiles.add(current);

            // add walkable adjacent tiles to walkable list
            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (isValid(newX, newY, map) && !containsNode(walkedTiles, newX, newY)) {
                    int newG = current.g + 1;
                    int newH = calculateHeuristic(newX, newY, end.x, end.y);
                    Node neighbor = new Node(newX, newY, newG, newH, current);
                    neighbor.f = neighbor.g + neighbor.h;

                    if (!containsNode(walkableTiles, newX, newY)) {
                        walkableTiles.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Finds lowest f cost
     * @return minFIndex index of minimum F
     */
    private int lowestF() {
        int minFIndex = 0;
        for (int i = 1; i < walkableTiles.size(); i++) {
            if (walkableTiles.get(i).f < walkableTiles.get(minFIndex).f) {
                minFIndex = i;
            }
        }
        return minFIndex;
    }

    /**
     * is position walkable and in bounds
     * @param x
     * @param y
     * @param map
     * @return True/False
     */
    private boolean isValid(int x, int y, Map map) {
        if (x >= 0 && y >= 0 && x < map.getMapHeight() && y < map.getMapWidth()) {
            if (map.getObjectAt(new GridPosition(x, y)) == null) {
                return true;
            } else if (map.getObjectAt(new GridPosition(x, y)) == map.getPlayerObjectReference()) {
                return true;
            } else {
                return map.getObjectAt(new GridPosition(x, y)) instanceof Path ||
                        map.getObjectAt(new GridPosition(x, y)) instanceof Water;
            }
        }
        return false;
    }

    /**
     * Calculates H value
     * @param x
     * @param y
     * @param goalX
     * @param goalY
     * @return H
     */
    private int calculateHeuristic(int x, int y, int goalX, int goalY) {
        return Math.abs(x - goalX) + Math.abs(y - goalY);
    }

    /**
     * finds if a list contains a Node via coordinates
     * @param list
     * @param x
     * @param y
     * @return True/False
     */
    private boolean containsNode(List<Node> list, int x, int y) {
        for (Node node : list) {
            if (node.x == x && node.y == y) {
                return true;
            }
        }
        return false;
    }
}
