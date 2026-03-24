package dev.gaspard.ffdc.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.gaspard.ffdc.GameConstants;
import dev.gaspard.ffdc.system.AnimationSystem;

/**
 * The player-controlled entity.
 *
 * <p>Sprites are loaded from
 * {@code sprites/player/walking_sprites/boy/boy_<dir>_<1..6>.png}.
 * The idle (stay) animation uses the {@code boy_stay_*} set and is shown
 * whenever the player is not moving.
 */
public final class Player extends Entity {

    private static final String TAG = "Player";
    private static final String SPRITE_BASE =
            "sprites/player/walking_sprites/boy/boy_";

    /** Animation frame arrays, one per direction. */
    private final Texture[] upFrames    = new Texture[GameConstants.ANIMATION_FRAMES];
    private final Texture[] downFrames  = new Texture[GameConstants.ANIMATION_FRAMES];
    private final Texture[] leftFrames  = new Texture[GameConstants.ANIMATION_FRAMES];
    private final Texture[] rightFrames = new Texture[GameConstants.ANIMATION_FRAMES];
    private final Texture[] idleFrames  = new Texture[GameConstants.ANIMATION_FRAMES];

    /** Accumulated time used by the animation system. */
    public float stateTime;

    /**
     * Creates the player at the default spawn tile defined in {@link GameConstants}.
     */
    public Player() {
        super(
            GameConstants.PLAYER_START_TILE_X * (float) GameConstants.TILE_SIZE,
            GameConstants.PLAYER_START_TILE_Y * (float) GameConstants.TILE_SIZE,
            GameConstants.PLAYER_SPEED
        );
        loadSprites();
        Gdx.app.log(TAG, "Player created at tile ("
            + GameConstants.PLAYER_START_TILE_X + ", "
            + GameConstants.PLAYER_START_TILE_Y + ")");
    }

    /** Loads all 30 walking sprites from the asset folder. */
    private void loadSprites() {
        for (int i = 0; i < GameConstants.ANIMATION_FRAMES; i++) {
            int n = i + 1;
            upFrames[i]    = new Texture(Gdx.files.internal(SPRITE_BASE + "up_"    + n + ".png"));
            downFrames[i]  = new Texture(Gdx.files.internal(SPRITE_BASE + "down_"  + n + ".png"));
            leftFrames[i]  = new Texture(Gdx.files.internal(SPRITE_BASE + "left_"  + n + ".png"));
            rightFrames[i] = new Texture(Gdx.files.internal(SPRITE_BASE + "right_" + n + ".png"));
            idleFrames[i]  = new Texture(Gdx.files.internal(SPRITE_BASE + "stay_"  + n + ".png"));
        }
    }

    /**
     * Returns the {@link TextureRegion} that should be rendered for the current
     * animation frame, determined by direction and elapsed state time.
     *
     * @param stateTime accumulated time in the current animation (seconds)
     * @return the texture region to draw this frame
     */
    public TextureRegion getKeyFrame(float stateTime) {
        Texture[] frames = framesForDirection(direction);
        int index = AnimationSystem.getCurrentFrame(
            stateTime,
            GameConstants.ANIMATION_FRAMES,
            GameConstants.ANIMATION_FRAME_DURATION
        );
        return new TextureRegion(frames[index]);
    }

    /** Selects the correct frame array for the current {@link Direction}. */
    private Texture[] framesForDirection(Direction dir) {
        return switch (dir) {
            case UP    -> upFrames;
            case DOWN  -> downFrames;
            case LEFT  -> leftFrames;
            case RIGHT -> rightFrames;
            case IDLE  -> idleFrames;
        };
    }

    /**
     * Releases all textures held by this player.
     * Must be called before the player object is discarded.
     */
    public void dispose() {
        disposeArray(upFrames);
        disposeArray(downFrames);
        disposeArray(leftFrames);
        disposeArray(rightFrames);
        disposeArray(idleFrames);
        Gdx.app.log(TAG, "Player textures disposed");
    }

    /** Disposes every non-null texture in the array. */
    private void disposeArray(Texture[] arr) {
        for (Texture t : arr) {
            if (t != null) t.dispose();
        }
    }
}
