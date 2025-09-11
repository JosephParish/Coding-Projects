/**
 * Represents an Amoeba entity in the game.
 * An Amoeba can create groups when a level is loaded and can also be assigned to groups.
 * <p>
 * The core group-related logic is handled in the {@code AmoebaGroup} class.
 * </p>
 *
 * @author Oscar Baggs
 * @version 1.0.3
 * Last Changed: 08/12/24
 */
public class Amoeba extends Entity {

    /**
     * The file path to the Amoeba's image asset.
     */
    private static final String FILE_PATH = "resources/assets/amoeba.png";

    /**
     * Constructs a new {@code Amoeba} entity at the default position (0, 0) with the "amoeba" type.
     */
    public Amoeba() {
        super(FILE_PATH, new GridPosition(0, 0));
        this.type = "amoeba";
    }

    /**
     * Updates the state of the Amoeba during the game loop.
     * @param map the game map containing the Amoeba and other entities
     */
    @Override
    public void update(Map map) {
    }

    /**
     * Moves the Amoeba in a specified direction on the map.
     * Empty as the amoeba doesnt move
     * @param map the game map
     * @param dir the direction in which to move the Amoeba
     */
    @Override
    public void move(Map map, Direction dir) {
    }

    /**
     * Checks for a collision at a specified grid position.
     *
     * @param map the game map
     * @param position the grid position to check for collisions
     * @return false as the default behavior. Collisions handled in player.
     */
    @Override
    public boolean collisionCheck(Map map, GridPosition position) {
        return false;
    }

    /**
     * Checks for a collision in a specified direction.
     *
     * @param map the game map
     * @param dir the direction to check for collisions
     * @return false as the default behavior. Collisions handled in player.
     */
    @Override
    public boolean collisionCheck(Map map, Direction dir) {
        return false;
    }
}
