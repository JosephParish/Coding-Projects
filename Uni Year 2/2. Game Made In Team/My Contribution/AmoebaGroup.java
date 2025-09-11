/**
 * Manages a group of {@code Amoeba} objects, handling their growth, grouping, and interactions.
 * The group can find available space to grow and add new Amoebas. Although it is not a game object,
 * individual Amoebas within the group are treated as entities for collision detection and other logic.
 *
 * @author Oscar
 * @version 1.0.1
 * Last Changed: 08/12/24
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AmoebaGroup extends Entity {

    /**
     * List of {@code Amoeba} objects that are part of this group.
     */
    private List<Amoeba> amoebas;

    /**
     * The maximum number of Amoebas that this group can grow to.
     */
    private int amoebaGrowthLimit = 20; // Temporary variable; growth limit is specified in the level file.

    /**
     * The file path to the image asset for the group.
     */
    private static final String FILE_PATH = "resources/assets/amoeba.png";

    /**
     * The first Amoeba in the group.
     */
    private Amoeba firstAmoeba;

    /**
     * Constructs a new {@code AmoebaGroup} with a default update rate and initializes the first Amoeba.
     */
    public AmoebaGroup() {
        super(FILE_PATH, new GridPosition(0, 0));
        this.updateRate = 15; // Growth rate specified in the level file.
        this.amoebas = new ArrayList<>();
        this.firstAmoeba = new Amoeba();
        this.addAmoeba(firstAmoeba);
    }

    /**
     * Initializes the position of the first Amoeba in the group.
     */
    public void initFirstAmoeba() {
        firstAmoeba.setPosition(this.getPosition());
    }

    /**
     * Updates the group's state in the game loop. Handles initialization, growth, or death.
     *
     * @param map the level map
     */
    @Override
    public void update(Map map) {
        initFirstAmoeba();
        if (this.isLimitReached(amoebaGrowthLimit)) {
            this.die(map, true);
        } else {
            this.findSpace(map);
        }
    }

    /**
     * Adds a new {@code Amoeba} to the group.
     *
     * @param amoeba the Amoeba to be added
     */
    public void addAmoeba(Amoeba amoeba) {
        amoebas.add(amoeba);
    }

    /**
     * Identifies potential spaces for the group to grow into by checking neighboring positions.
     * <p>
     * Positions with multiple Amoeba neighbors are more likely to be selected for growth,
     * simulating a natural spread.
     * </p>
     *
     * @param map the level map
     */
    public void findSpace(Map map) {
        List<GridPosition> freeSpaces = new ArrayList<>();
        for (Amoeba amoeba : amoebas) {
            List<GameObject> neighbours = new ArrayList<>();
            neighbours.add(map.getNeighbourOf(amoeba, Direction.UP));
            neighbours.add(map.getNeighbourOf(amoeba, Direction.LEFT));
            neighbours.add(map.getNeighbourOf(amoeba, Direction.RIGHT));
            neighbours.add(map.getNeighbourOf(amoeba, Direction.DOWN));
            for (GameObject neighbour : neighbours) {
                if (neighbour instanceof Path || neighbour instanceof Dirt) {
                    freeSpaces.add(neighbour.getPosition());
                }
            }
        }
        if (!freeSpaces.isEmpty()) {
            this.grow(map, freeSpaces);
        } else {
            this.die(map, false);
        }
    }

    /**
     * Randomly selects a free space and grows a new Amoeba there.
     *
     * @param map the level map
     * @param freeSpaces the list of available positions for growth
     */
    public void grow(Map map, List<GridPosition> freeSpaces) {
        Random random = new Random();
        int randomIndex = random.nextInt(freeSpaces.size());
        Amoeba newAmoeba = new Amoeba();
        GridPosition amoebaTile = freeSpaces.get(randomIndex);
        newAmoeba.setPosition(amoebaTile);
        this.addAmoeba(newAmoeba);

        GameObject grownOver = map.getObjectAt(amoebaTile);
        map.getPendingRemovals().add(grownOver);
        map.getPendingAdditions().add(newAmoeba);
    }

    /**
     * Terminates all Amoebas in the group, replacing them with either {@code Boulder} or {@code Gem}
     * objects based on whether the growth limit was reached.
     *
     * @param map the level map
     * @param limitReached {@code true} if the growth limit was reached; {@code false} otherwise
     */
    public void die(Map map, boolean limitReached) {
        for (Amoeba amoeba : amoebas) {
            GridPosition amoebaTile = amoeba.getPosition();
            if (limitReached) {
                map.getPendingRemovals().add(amoeba);
                Boulder boulder = new Boulder();
                boulder.setPosition(amoebaTile);
                map.getPendingAdditions().add(boulder);
            } else {
                map.getPendingRemovals().add(amoeba);
                Gem diamond = new Gem();
                diamond.setPosition(amoebaTile);
                map.getPendingAdditions().add(diamond);
            }
        }
        map.getPendingRemovals().add(this);
    }

    /**
     * Checks if the group's size has reached the specified growth limit.
     *
     * @param amoebaGrowthLimit the growth limit
     * @return {@code true} if the limit is reached; {@code false} otherwise
     */
    public boolean isLimitReached(int amoebaGrowthLimit) {
        return amoebas.size() >= amoebaGrowthLimit;
    }

    @Override
    public String toString() {
        return amoebas.toString();
    }

    @Override
    public void move(Map map, Direction dir) {
        // No movement logic for the group itself
    }

    @Override
    public boolean collisionCheck(Map map, GridPosition position) {
        return false; // No collision logic for the group itself
    }

    @Override
    public boolean collisionCheck(Map map, Direction dir) {
        return false; // No collision logic for the group itself
    }

    /**
     * Sets the growth limit for the group.
     *
     * @param limit the new growth limit
     */
    public void setGrowthLimit(int limit) {
        this.amoebaGrowthLimit = limit;
    }

    /**
     * Sets the growth rate for the group.
     *
     * @param rate the new growth rate
     */
    public void setGrowthRate(int rate) {
        this.updateRate = rate;
    }

    /**
     * Gets the current growth limit for the group.
     *
     * @return the growth limit
     */
    public int getAmoebaGrowthLimit() {
        return amoebaGrowthLimit;
    }
}
