package dev.gaspard.ffdc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Temporary splash screen to validate the rendering pipeline.
 * Will be replaced by MainMenuScreen in Phase 3.
 */
public class SplashScreen implements Screen {

    private final FFDCGame game;
    private Texture testTile;

    public SplashScreen(FFDCGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        testTile = new Texture(Gdx.files.internal("sprites/tiles/grass00.png"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        game.batch.begin();

        // Draw a grid of tiles to prove rendering works
        int tileSize = 48;
        for (int x = 0; x < Gdx.graphics.getWidth(); x += tileSize) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += tileSize) {
                game.batch.draw(testTile, x, y, tileSize, tileSize);
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (testTile != null) testTile.dispose();
    }
}
