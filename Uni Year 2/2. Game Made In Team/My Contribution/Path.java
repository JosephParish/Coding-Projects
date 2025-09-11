/**
 * <p> Class for Path tile.</p>
 *
 * @author Ollie.
 * @version 1.0.1
 * Last Changed: 1/12/24
 */
public class Path extends Tile {

    private static final String FILE_PATH = "resources/assets/path.png";

    /**
     * Path Constructor
     */
    public Path() {
        super(FILE_PATH, new GridPosition(0, 0));
        walkable = true;
        destroyable = false;
    }

    /**
     * Path Constructor with position
     * @param position
     */
    public Path(GridPosition position) {
        super(FILE_PATH, position);
        walkable = true;
        destroyable = false;
    }

    /**
     * Update function implemented from GameObject
     */
    @Override
    public void update(Map map) {}
}
