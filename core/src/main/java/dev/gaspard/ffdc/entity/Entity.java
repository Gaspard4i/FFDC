package dev.gaspard.ffdc.entity;

import com.badlogic.gdx.math.Rectangle;

/**
 * Base class for all game entities (players, NPCs, objects).
 * Holds world position, movement speed, current direction, and a solid
 * collision rectangle relative to the entity's top-left corner.
 */
public abstract class Entity {

    /** World X position in pixels (left edge of the 48-px tile). */
    public float worldX;

    /** World Y position in pixels (bottom edge of the 48-px tile). */
    public float worldY;

    /** Movement speed in pixels per second. */
    public float speed;

    /** Current facing / movement direction. */
    public Direction direction;

    /**
     * Axis-aligned bounding box used for collision detection, expressed
     * in local (entity-relative) coordinates.
     * Defaults to {@code Rectangle(8, 16, 32, 32)} inside a 48-px tile.
     */
    public Rectangle solidArea;

    /** X offset of solidArea in its default (unchanged) state. */
    public int solidAreaDefaultX;

    /** Y offset of solidArea in its default (unchanged) state. */
    public int solidAreaDefaultY;

    /**
     * Constructs an entity at the given world position with the given speed.
     *
     * @param worldX initial X position in world pixels
     * @param worldY initial Y position in world pixels
     * @param speed  movement speed in pixels per second
     */
    protected Entity(float worldX, float worldY, float speed) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = Direction.IDLE;

        // Default solid area: 8 px inset on left/right, 16 px from top inside a 48-px tile
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, 32, 32);
    }
}
