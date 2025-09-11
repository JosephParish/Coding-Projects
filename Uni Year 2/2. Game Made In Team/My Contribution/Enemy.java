import java.util.ArrayList;
import java.util.List;

/**
 * <p>This Abstract Enemy class extends Entity
 * and is intended to hold an abstract functions to find its future moves.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.4
 * Last Changed: 30/11/24
 */
public abstract class Enemy extends Entity {

    /**
     * Enemy Constructor
     * @param pathToSprite
     * @param position
     */
    public Enemy(String pathToSprite, GridPosition position) {
        super(pathToSprite, position);
        this.type = "enemy";
    }

    /**
     * Destroys objects in a 9x9 grid
     * @param map
     */
    public void destroy(Map map) {
        for (GameObject object : get9x9Grid(map)) {
            if (!(object instanceof TitaniumWall)) {
                map.getPendingRemovals().add(object);
            }
        }
    }

    /**
     * gets objects in a 9x9 grid
     * @param map
     * @return destroyedObjects
     */
    public List<GameObject> get9x9Grid(Map map) {
        List<GameObject> destroyedObjects = new ArrayList<>();
        List<Direction> northSouthEastWest = new ArrayList<>();

        northSouthEastWest.add(Direction.UP);
        northSouthEastWest.add(Direction.DOWN);
        northSouthEastWest.add(Direction.LEFT);
        northSouthEastWest.add(Direction.RIGHT);

        destroyedObjects.add(this);
        for (Direction direction : northSouthEastWest) {
            GameObject orthogonalNeighbour = map.getTileNeighbourOf(this, direction);
            GameObject evilNonOrthogonalNeighbour = map.getTileNeighbourOf(orthogonalNeighbour, direction.clockwiseRotation());
            destroyedObjects.add(orthogonalNeighbour);
            destroyedObjects.add(evilNonOrthogonalNeighbour);
        }
        return destroyedObjects;
    }
}
