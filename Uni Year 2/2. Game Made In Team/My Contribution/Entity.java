/**
 * Abstract Entity class derived from abstract GameObject class. Includes new abstract move method.
 *
 * @author Oscar, Joseph and Ahmed.
 * @version 1.0.2
 * Last changed: 22/11/2024
 */

public abstract class Entity extends GameObject {

    /**
     * Creates a new entity.
     *
     * @param pathToSprite path to the sprite
     * @param position     initial position of object
     */
    public Entity(String pathToSprite, GridPosition position) {
        super(pathToSprite, position);
    }

    /**
     * Gets map to destroy an Entity
     * @param map
     */
    public void delete(Map map){
        map.destroyEntity(this);
    }

    /**
     * Changes the entity's position.
     * @param map
     * @param dir
     */
    public abstract void move(Map map, Direction dir);

    /**
     * Checks if there is a collision with an object in a position/offset of a position
     * @param map
     * @param position
     */
    public abstract boolean collisionCheck(Map map, GridPosition position);

    /**
     * Checks if there is a collision with an object in a direction
     * @param map
     * @param dir
     */
    public abstract boolean collisionCheck(Map map, Direction dir);
}
