import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>This Frog class moves towards the player using A* algorithm and kills on touch</p>
 *
 * @author Joseph Parish.
 * @version 1.0.5
 * Last Changed: 30/11/24
 */
public class Frog extends PathfindingEnemy {

    private static final String FILE_PATH = "resources/assets/frog.png";

    /**
     * Frog Constructor
     */
    public Frog() {
        super(FILE_PATH, new GridPosition(0, 0));
        this.updateRate = 5;
        this.type = "enemy";
    }

    /**
     * Update Implemented
     */
    @Override
    public void update(Map map) {
        move(map, Direction.UP);
    }

    /**
     * Default implementation of collisionCheck
     * @param map
     * @param dir
     * @return boolean
     */
    @Override
    public boolean collisionCheck(Map map, Direction dir) {
        return false;
    }

    /**
     * Checks for potential collisions at a given position.
     * This method checks if the frog collides with the player or an impassable tile.
     * @param map
     * @param position
     * @return boolean
     */
    public boolean collisionCheck(Map map, GridPosition position) {
        GameObject objectAt = map.getObjectAt(position);
        if (objectAt == null) {
            return true;
        } else if (position == map.getPlayerObjectReference().getPosition()) {
            map.getPlayerObjectReference().die();
            return true;
        } else if (objectAt.isWalkable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Moves the frog based on pathfinding logic. This method uses an algorithm called A* to find
     * a path towards the player and update the frog's position accordingly.
     * @param map
     * @param dir
     */
    public void move(Map map, final Direction dir) {
        ArrayList<int[]> path = AStarAlgorithm(map, this.getPosition(), map.getPlayerObjectReference().getPosition());
        if (path != null) {
            if (collisionCheck(map, new GridPosition(path.get(1)[0], path.get(1)[1]))) {
                this.setPosition(new GridPosition(path.get(1)[0], path.get(1)[1]));
            } else {
                map.getPlayerObjectReference().die();
                this.setPosition(new GridPosition(path.get(1)[0], path.get(1)[1]));
            }
        } else {
            Random rand = new Random();
            List<GameObject> neighbours = new ArrayList<>();

            if (map.getNeighbourOf(this, Direction.UP) != null) {
                neighbours.add(map.getNeighbourOf(this, Direction.UP));
            }
            if (map.getNeighbourOf(this, Direction.LEFT) != null) {
                neighbours.add(map.getNeighbourOf(this, Direction.LEFT));
            }
            if (map.getNeighbourOf(this, Direction.RIGHT) != null) {
                neighbours.add(map.getNeighbourOf(this, Direction.RIGHT));
            }
            if (map.getNeighbourOf(this, Direction.DOWN) != null) {
                neighbours.add(map.getNeighbourOf(this, Direction.DOWN));
            }

            List<GameObject> pathNeighbours = new ArrayList<>();
            for (GameObject neighbour : neighbours) {
                if (neighbour instanceof Path) {
                    pathNeighbours.add(neighbour);
                }
            }
            if (!pathNeighbours.isEmpty()) {
                int randindex = rand.nextInt(pathNeighbours.size());
                this.setPosition(pathNeighbours.get(randindex).getPosition());
            }
        }
    }
}

