package dev.gaspard.ffdc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.gaspard.ffdc.GameConstants;
import dev.gaspard.ffdc.audio.AudioManager;
import dev.gaspard.ffdc.core.GameContext;
import dev.gaspard.ffdc.entity.Player;
import dev.gaspard.ffdc.input.InputAction;
import dev.gaspard.ffdc.system.AnimationSystem;
import dev.gaspard.ffdc.system.CollisionSystem;
import dev.gaspard.ffdc.system.MovementSystem;
import dev.gaspard.ffdc.world.GameMap;

/**
 * The main game play screen.
 *
 * <p>Orchestrates all game systems (movement, animation, collision, audio) and
 * renders the world centered on the player. Input is handled by the
 * {@link dev.gaspard.ffdc.input.GameInputProcessor} registered in the
 * provided {@link GameContext}.
 */
public final class PlayScreen implements Screen {

    private static final String TAG = "PlayScreen";
    private static final String MAP_FILE  = "maps/WorldMapAllTree.txt";
    private static final String MUSIC_FILE = "audio/music/BlueBoyAdventure.wav";

    private final GameContext context;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    private final AudioManager    audioManager;
    private final GameMap         gameMap;
    private final Player          player;
    private final CollisionSystem collisionSystem;
    private final MovementSystem  movementSystem;

    /** Whether the game is currently paused. */
    private boolean paused;

    /** Tracks the PAUSE key state across frames to detect press edges. */
    private boolean pauseKeyWasHeld;

    /** Tracks the TOGGLE_MUSIC key state across frames to detect press edges. */
    private boolean musicKeyWasHeld;

    /**
     * Constructs the play screen using the given shared context.
     *
     * @param context the central dependency container
     */
    public PlayScreen(GameContext context) {
        this.context = context;

        batch  = new SpriteBatch();
        camera = new OrthographicCamera(
            GameConstants.VIEWPORT_WIDTH,
            GameConstants.VIEWPORT_HEIGHT
        );

        audioManager = new AudioManager();
        gameMap      = new GameMap(MAP_FILE);
        player       = new Player();
        collisionSystem = new CollisionSystem(
            gameMap.getMapData(), GameConstants.TILE_SIZE
        );
        movementSystem = new MovementSystem();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(context.inputProcessor);
        audioManager.playMusic(MUSIC_FILE);
        Gdx.app.log(TAG, "PlayScreen shown");
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(Color.BLACK);

        // Centre camera on player (clamped to world bounds)
        float camX = clamp(
            player.worldX + GameConstants.TILE_SIZE / 2f,
            GameConstants.VIEWPORT_WIDTH  / 2f,
            GameConstants.WORLD_COLS * GameConstants.TILE_SIZE - GameConstants.VIEWPORT_WIDTH  / 2f
        );
        float camY = clamp(
            player.worldY + GameConstants.TILE_SIZE / 2f,
            GameConstants.VIEWPORT_HEIGHT / 2f,
            GameConstants.WORLD_ROWS * GameConstants.TILE_SIZE - GameConstants.VIEWPORT_HEIGHT / 2f
        );
        camera.position.set(camX, camY, 0);
        camera.update();

        // Camera bottom-left in world space (used for tile culling)
        float worldLeft   = camera.position.x - GameConstants.VIEWPORT_WIDTH  / 2f;
        float worldBottom = camera.position.y - GameConstants.VIEWPORT_HEIGHT / 2f;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        gameMap.render(batch, worldLeft, worldBottom,
            GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);

        renderPlayer();

        renderHud();

        batch.end();
    }

    /** Advances all game logic by one frame. */
    private void update(float delta) {
        handlePauseInput();
        handleMusicInput();

        if (paused) {
            return;
        }

        movementSystem.update(player, context.inputProcessor, collisionSystem, delta);

        if (player.direction != dev.gaspard.ffdc.entity.Direction.IDLE) {
            player.stateTime += delta;
        } else {
            player.stateTime = 0f;
        }
    }

    /** Detects a fresh PAUSE key press (rising edge) and toggles pause state. */
    private void handlePauseInput() {
        boolean held = context.inputProcessor.isHeld(InputAction.PAUSE);
        if (held && !pauseKeyWasHeld) {
            paused = !paused;
            if (paused) {
                audioManager.pauseMusic();
            } else {
                audioManager.resumeMusic();
            }
            Gdx.app.log(TAG, "Paused: " + paused);
        }
        pauseKeyWasHeld = held;
    }

    /** Detects a fresh TOGGLE_MUSIC key press (rising edge) and toggles music. */
    private void handleMusicInput() {
        boolean held = context.inputProcessor.isHeld(InputAction.TOGGLE_MUSIC);
        if (held && !musicKeyWasHeld) {
            audioManager.toggleMusic();
        }
        musicKeyWasHeld = held;
    }

    /** Draws the player sprite at its current world position. */
    private void renderPlayer() {
        TextureRegion frame = player.getKeyFrame(player.stateTime);
        batch.draw(frame,
            player.worldX, player.worldY,
            GameConstants.TILE_SIZE, GameConstants.TILE_SIZE
        );
    }

    /**
     * Placeholder HUD rendering.
     * Will be replaced by a proper UI layer in a later sprint.
     */
    private void renderHud() {
        // HUD placeholder — no-op until UI sprint
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth  = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
        paused = true;
        audioManager.pauseMusic();
    }

    @Override
    public void resume() {
        // Let the player manually un-pause
    }

    @Override
    public void hide() {
        context.inputProcessor.reset();
        audioManager.pauseMusic();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        gameMap.dispose();
        audioManager.dispose();
        Gdx.app.log(TAG, "PlayScreen disposed");
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Clamps {@code value} to [{@code min}, {@code max}]. */
    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
