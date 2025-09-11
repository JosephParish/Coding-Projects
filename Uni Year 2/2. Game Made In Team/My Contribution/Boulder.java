/**
 * Represents a Boulder item in the game. The Boulder can fall, roll, and interact with
 * various map elements such as Water and MagicWalls. It can also be pushed in different directions.
 *
 * @author Oscas Baggs
 * @version 1.0.2
 */
public class Boulder extends Item {

    /**
     * The file path to the image asset for the Boulder.
     */
    private static final String FILE_PATH = "resources/assets/boulder.png";

    /**
     * Flag to control if water should skip this tile when flowing
     */
    private boolean waterSkip = true;

    /**
     * Constructs a new Boulder at the default position (0, 0) with the "boulder" type.
     */
    public Boulder() {
        super(FILE_PATH, new GridPosition(0, 0));
        this.type = "boulder";
        this.updateRate = 1;
    }

    /**
     * Updates the Boulder’s position and handles interactions with the map elements.
     * If the Boulder encounters a Water tile, it will skip the update half the time (to slow fall rate)
     * If the Boulder falls onto a MagicWall it is swapped out with a Gem below the MagicWall.
     *
     * @param map the game map where the Boulder is located
     */
    public void update(Map map) {
        if (map.getTileAt(this.getPosition()) instanceof Water && !waterSkip) {
            waterSkip = true;
        } else if (map.getTileAt(this.getPosition()) instanceof Water && waterSkip) {
            waterSkip = false;
            return;
        }
        GameObject downNeighbour = map.getNeighbourOf(this, Direction.DOWN);

        if (!this.fall(map)) {
            this.roll(map);
        }
        if (downNeighbour instanceof MagicWall) {
            map.getPendingRemovals().add(this);
            GameObject belowMagicWall = map.getNeighbourOf(downNeighbour, Direction.DOWN);
            Gem diamond = new Gem();
            diamond.setPosition(belowMagicWall.getPosition());
            map.getPendingAdditions().add(diamond);
        }
    }

    /**
     * Pushes the Boulder in the specified direction (left or right).
     *
     * @param map the game map where the Boulder is located
     * @param direction the direction to push the Boulder (left or right)
     */
    public void push(Map map, Direction direction) {
        if (direction == Direction.RIGHT) {
            this.move(map, Direction.RIGHT);
        } else if (direction == Direction.LEFT) {
            this.move(map, Direction.LEFT);
        }
    }

    /**
     * Deletes the Boulder.
     */
    public void delete() {
        // play transformation sound
    }
}
