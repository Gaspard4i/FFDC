package dev.gaspard.ffdc;

/**
 * All game constants. No magic numbers elsewhere in the codebase.
 */
public final class GameConstants {

    private GameConstants() {}

    /** Base tile size before scaling (pixels). */
    public static final int TILE_SIZE = 48;

    /** World dimensions in tiles. */
    public static final int WORLD_COLS = 50;
    public static final int WORLD_ROWS = 50;

    /** Viewport dimensions in pixels. */
    public static final int VIEWPORT_WIDTH = 768;
    public static final int VIEWPORT_HEIGHT = 576;

    /** Player defaults. */
    public static final float PLAYER_SPEED = 200f;
    public static final int PLAYER_START_TILE_X = 23;
    public static final int PLAYER_START_TILE_Y = 21;

    /** Animation. */
    public static final int ANIMATION_FRAMES = 6;
    public static final float ANIMATION_FRAME_DURATION = 0.12f;

    /** Target frame rate. */
    public static final int TARGET_FPS = 60;
}
