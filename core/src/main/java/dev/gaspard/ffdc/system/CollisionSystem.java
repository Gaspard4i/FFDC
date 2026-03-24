package dev.gaspard.ffdc.system;

import dev.gaspard.ffdc.entity.Entity;

/**
 * Checks tile-based collision for entities moving through the world.
 *
 * <p>The system tests the four corners of an entity's solid area against
 * the map data. If any corner falls on a solid tile the move is rejected.
 */
public final class CollisionSystem {

    private final int[][] mapData;
    private final int tileSize;

    /**
     * Constructs a CollisionSystem for the given map.
     *
     * @param mapData  2-D array of tile IDs indexed as {@code [col][row]}
     * @param tileSize size of one tile in world pixels
     */
    public CollisionSystem(int[][] mapData, int tileSize) {
        this.mapData = mapData;
        this.tileSize = tileSize;
    }

    /**
     * Returns {@code true} when moving an entity to (newX, newY) would place
     * any corner of its solid area on a solid tile.
     *
     * @param entity the entity being tested
     * @param newX   candidate world X position
     * @param newY   candidate world Y position
     * @return {@code true} if the move is blocked
     */
    public boolean checkTile(Entity entity, float newX, float newY) {
        float left   = newX + entity.solidArea.x;
        float right  = left + entity.solidArea.width - 1;
        float bottom = newY + entity.solidArea.y;
        float top    = bottom + entity.solidArea.height - 1;

        int colLeft   = (int) (left   / tileSize);
        int colRight  = (int) (right  / tileSize);
        int rowBottom = (int) (bottom / tileSize);
        int rowTop    = (int) (top    / tileSize);

        if (colLeft < 0 || rowBottom < 0
                || colRight >= mapData.length
                || rowTop >= mapData[0].length) {
            return true; // out of bounds is solid
        }

        return isSolidTile(mapData[colLeft][rowBottom])
            || isSolidTile(mapData[colRight][rowBottom])
            || isSolidTile(mapData[colLeft][rowTop])
            || isSolidTile(mapData[colRight][rowTop]);
    }

    /**
     * Returns {@code true} when the tile with the given ID is solid.
     *
     * <ul>
     *   <li>17–22 : trees</li>
     *   <li>23–50 : water</li>
     *   <li>51–58 : walls</li>
     * </ul>
     *
     * @param tileId the tile identifier
     * @return {@code true} if entities cannot walk through this tile
     */
    public boolean isSolidTile(int tileId) {
        return (tileId >= 17 && tileId <= 22)
            || (tileId >= 23 && tileId <= 50)
            || (tileId >= 51 && tileId <= 58);
    }
}
