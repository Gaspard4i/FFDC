package dev.gaspard.ffdc.entity;

/**
 * Movement direction with unit vector components.
 */
public enum Direction {

    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    IDLE(0, 0);

    /** Horizontal component (-1, 0, or 1). */
    public final int dx;

    /** Vertical component (-1, 0, or 1). */
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /** Returns the opposite direction, or IDLE if already IDLE. */
    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case IDLE -> IDLE;
        };
    }

    /** Returns true if this direction is horizontal (LEFT or RIGHT). */
    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    /** Returns true if this direction is vertical (UP or DOWN). */
    public boolean isVertical() {
        return this == UP || this == DOWN;
    }
}
