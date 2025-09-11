/**
 * Represents a water tile in the application. This class extends the Tile class
 * and simulates the behavior of water spreading or flowing into adjacent paths over time.
 *
 * @author: Oscar Baggs, Spas Dikov
 */

public class Water extends Tile {

    private static final String FILE_PATH = "resources/assets/water.png";

    /**
     * Creates a new Water object. Initialises the tile with its specific
     * image, default GridPosition at (0, 0), and specific attributes:
     * Walkable: true, Destroyable: false, Update rate: 5.
     */
    public Water() {
        super(FILE_PATH, new GridPosition(0, 0));
        this.walkable = true;
        this.destroyable = false;
        this.updateRate = 5;
    }

    /**
     * Updates the state of the water tile by checking adjacent tiles.
     * If the tile below, to the left, or to the right is a Path, the water flows into it.
     *
     * @param map the Map object that manages the game world and tile relationships.
     */
    @Override
    public void update(Map map) {
        GameObject downNeighbour = map.getTileNeighbourOf(this, Direction.DOWN);
        GameObject leftNeighbour = map.getTileNeighbourOf(this, Direction.LEFT);
        GameObject rightNeighbour = map.getTileNeighbourOf(this, Direction.RIGHT);

        if (downNeighbour instanceof Path) {
            this.flow(map, downNeighbour);
        }
        if (leftNeighbour instanceof Path) {
            this.flow(map, leftNeighbour);
        }
        if (rightNeighbour instanceof Path) {
            this.flow(map, rightNeighbour);
        }
    }

    /**
     * Causes the water tile to flow into a given adjacent tile if it is a Path.
     * A new water tile is created and added to pending additions of the map.
     *
     * @param map  the Map object that manages the game world.
     * @param tile the GameObject representing the target tile for water flow.
     */
    public void flow(Map map, GameObject tile) {
        GridPosition newWaterPos = tile.getPosition();
        Water water = new Water();
        water.setPosition(newWaterPos);
        map.getPendingAdditions().add(water);
    }
}