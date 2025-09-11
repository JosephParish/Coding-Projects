/**
 * <p> Super for Butterfly and Firefly,
 * uses Movement priority to find the next path to move to.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.1
 * Last Changed: 6/12/24
 */
public class PatrollingEnemy extends Enemy {
    protected boolean isleftMoving;
    private Direction lastMovement = Direction.UP;

    /**
     * PatrollingEnemy Constructor
     * @param pathToSprite
     * @param position
     */
    public PatrollingEnemy(String pathToSprite, GridPosition position) {
        super(pathToSprite, position);
    }

    /**
     * Update implemented
     * @param map
     */
    @Override
    public void update(Map map) {
        this.calculateNextMove(map);
    }

    /**
     * returns the relevant list of directions in priority order
     * @return Direction[]
     */
    public Direction[] calculateMovePriority() {
        if (this.isleftMoving) {
            return new Direction[]{
                    lastMovement.antiClockwiseRotation(),
                    lastMovement,
                    lastMovement.clockwiseRotation(),
                    lastMovement.clockwiseRotation().clockwiseRotation()
            };
        } else {
            return new Direction[]{
                    lastMovement.clockwiseRotation(),
                    lastMovement,
                    lastMovement.antiClockwiseRotation(),
                    lastMovement.clockwiseRotation().clockwiseRotation()
            };
        }
    }

    /**
     * Finds if a move direction is available to move to,
     * if not then it trys the next.
     * @param map the current level map.
     */
    public void calculateNextMove(Map map) {
        for (Direction direction : this.calculateMovePriority()) {
            GameObject neighbour = map.getNeighbourOf(this, direction);
            if (neighbour instanceof Path) {
                this.move(map, direction);
                lastMovement = direction;
                return;
            } else if (neighbour instanceof Player) {
                map.getPlayerObjectReference().die();
                this.move(map, direction);
                lastMovement = direction;
                return;
            }
        }
    }


    /**
     * Moves the butterfly in the specified direction by updating its position on the map.
     * This method is called by {@code calculateNextMove}.
     *
     * @param map the current level map.
     * @param dir the direction in which the butterfly is moving.
     */
    public void move(Map map, Direction dir) {
        if (dir == Direction.UP) {
            this.setPosition(new GridPosition(
                    this.getPosition().getX(),
                    this.getPosition().getY() - 1
            ));
        } else if (dir == Direction.DOWN) {
            this.setPosition(new GridPosition(
                    this.getPosition().getX(),
                    this.getPosition().getY() + 1
            ));
        } else if (dir == Direction.LEFT) {
            this.setPosition(new GridPosition(
                    this.getPosition().getX() - 1,
                    this.getPosition().getY()
            ));
        } else if (dir == Direction.RIGHT) {
            this.setPosition(new GridPosition(
                    this.getPosition().getX() + 1,
                    this.getPosition().getY()
            ));
        }
    }

    /**
     * Default collisionCheck
     * @param map
     * @param position
     */
    @Override
    public boolean collisionCheck(Map map, GridPosition position) {
        return false;
    }

    /**
     * Default collisionCheck
     * @param map
     * @param dir
     */
    @Override
    public boolean collisionCheck(Map map, Direction dir) {
        return false;
    }
}
