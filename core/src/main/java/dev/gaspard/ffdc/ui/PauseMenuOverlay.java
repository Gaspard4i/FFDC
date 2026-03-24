package dev.gaspard.ffdc.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.gaspard.ffdc.GameConstants;

/**
 * A semi-transparent overlay that covers the play screen when the game is paused.
 *
 * <p>The overlay has its own {@link Stage} and {@link FitViewport} so it is
 * independent of the play camera. Call {@link #show()} / {@link #hide()} to
 * toggle visibility, {@link #render(float)} every frame, and
 * {@link #dispose()} when the owning screen is destroyed.
 *
 * <p>Callbacks for "Resume" and "Main Menu" are supplied as {@link Runnable}s
 * so the overlay stays decoupled from the screen it lives inside.
 */
public final class PauseMenuOverlay implements Disposable {

    /** Opacity of the dark backdrop (0 = fully transparent, 1 = opaque). */
    private static final float BACKDROP_ALPHA = 0.6f;

    /** Preferred button width (pixels in viewport space). */
    private static final float BUTTON_WIDTH = 240f;

    /** Preferred button height (pixels in viewport space). */
    private static final float BUTTON_HEIGHT = 48f;

    /** Vertical padding between buttons. */
    private static final float BUTTON_PAD = 14f;

    /** Extra space between the title and the first button. */
    private static final float TITLE_BOTTOM_PAD = 36f;

    private final Runnable onResume;
    private final Runnable onMainMenu;

    private final Stage stage;
    private final Skin skin;
    private final ShapeRenderer shapeRenderer;

    private boolean visible;

    /**
     * Constructs the pause overlay.
     *
     * @param onResume   callback invoked when the player clicks "Resume"
     * @param onMainMenu callback invoked when the player clicks "Main Menu"
     */
    public PauseMenuOverlay(Runnable onResume, Runnable onMainMenu) {
        this.onResume   = onResume;
        this.onMainMenu = onMainMenu;

        FitViewport viewport = new FitViewport(
            GameConstants.VIEWPORT_WIDTH,
            GameConstants.VIEWPORT_HEIGHT
        );
        stage = new Stage(viewport);
        skin  = buildMinimalSkin();
        shapeRenderer = new ShapeRenderer();

        buildLayout();
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Makes the overlay visible and claims keyboard / mouse input.
     * The previous input processor is discarded; callers are responsible for
     * restoring it when they call {@link #hide()}.
     */
    public void show() {
        visible = true;
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Hides the overlay. Input is NOT automatically restored — the caller
     * should set its own input processor after this call.
     */
    public void hide() {
        visible = false;
    }

    /** Returns {@code true} if the overlay is currently visible. */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Renders the overlay if visible.
     * Must be called every frame from the owning screen's {@code render()} method,
     * <em>after</em> the play-world batch has ended.
     *
     * @param delta seconds elapsed since the last frame
     */
    public void render(float delta) {
        if (!visible) {
            return;
        }

        // Draw semi-transparent backdrop using ShapeRenderer (filled rect)
        stage.getViewport().apply();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, BACKDROP_ALPHA);
        shapeRenderer.rect(0, 0, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
        shapeRenderer.end();

        // Draw Scene2D widgets on top
        stage.act(delta);
        stage.draw();
    }

    /**
     * Updates the stage viewport. Call from the owning screen's
     * {@code resize()} method.
     *
     * @param width  new window width in pixels
     * @param height new window height in pixels
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Releases all native resources owned by the overlay.
     * Must be called from the owning screen's {@code dispose()} method.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
    }

    // -------------------------------------------------------------------------
    // Layout
    // -------------------------------------------------------------------------

    /** Builds and populates the root {@link Table} inside the stage. */
    private void buildLayout() {
        Label.LabelStyle titleStyle = new Label.LabelStyle(
            skin.get("title", BitmapFont.class), Color.WHITE);

        Label pausedLabel = new Label("PAUSED", titleStyle);
        pausedLabel.setAlignment(Align.center);

        TextButton resumeBtn   = new TextButton("Resume",    skin);
        TextButton mainMenuBtn = new TextButton("Main Menu", skin);

        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onResume.run();
            }
        });
        mainMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMainMenu.run();
            }
        });

        Table root = new Table();
        root.setFillParent(true);
        root.center();

        root.add(pausedLabel).expandX().fillX().padBottom(TITLE_BOTTOM_PAD).row();
        root.add(resumeBtn).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_PAD).row();
        root.add(mainMenuBtn).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).row();

        stage.addActor(root);
    }

    // -------------------------------------------------------------------------
    // Skin
    // -------------------------------------------------------------------------

    /**
     * Builds a minimal {@link Skin} programmatically — no skin file required.
     *
     * @return a newly created skin owned by this overlay
     */
    private static Skin buildMinimalSkin() {
        Skin s = new Skin();

        BitmapFont defaultFont = new BitmapFont();
        defaultFont.getData().setScale(1.4f);
        s.add("default", defaultFont, BitmapFont.class);

        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(2.2f);
        s.add("title", titleFont, BitmapFont.class);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = defaultFont;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.CYAN;
        buttonStyle.downFontColor = Color.YELLOW;
        s.add("default", buttonStyle, TextButton.TextButtonStyle.class);

        return s;
    }
}
