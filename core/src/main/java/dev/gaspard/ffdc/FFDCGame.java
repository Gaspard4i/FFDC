package dev.gaspard.ffdc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FFDCGame extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Gdx.app.log("FFDC", "Finn Found Da Chest - 2026 Edition");
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}
