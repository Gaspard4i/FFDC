package dev.gaspard.ffdc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.gaspard.ffdc.core.GameContext;
import dev.gaspard.ffdc.screen.MainMenuScreen;

/**
 * Application entry point.
 *
 * <p>Creates the shared {@link SpriteBatch} and {@link GameContext} once, then
 * sets the initial screen to {@link MainMenuScreen}. All subsequent screen
 * transitions are performed by individual screens via the {@code game} reference
 * they receive at construction time.
 */
public class FFDCGame extends Game {

    /** Shared sprite batch. Screens must NOT dispose this — only {@link #dispose()} does. */
    public SpriteBatch batch;

    /** Central dependency container shared across all screens. */
    private GameContext context;

    @Override
    public void create() {
        batch   = new SpriteBatch();
        context = GameContext.createDefault();

        Gdx.app.log("FFDC", "Finn Found Da Chest - 2026 Edition");
        setScreen(new MainMenuScreen(this, context));
    }

    /**
     * Returns the shared {@link GameContext}.
     *
     * @return the context created during {@link #create()}
     */
    public GameContext getContext() {
        return context;
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}
