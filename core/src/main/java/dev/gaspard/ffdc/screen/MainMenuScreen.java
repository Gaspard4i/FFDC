package dev.gaspard.ffdc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.gaspard.ffdc.FFDCGame;
import dev.gaspard.ffdc.GameConstants;
import dev.gaspard.ffdc.core.GameContext;

/**
 * The main menu screen, displayed when the game starts.
 *
 * <p>Presents the title, subtitle and four buttons (three game-mode selectors
 * plus Quit). All three mode buttons currently navigate to {@link PlayScreen};
 * keyboard navigation with UP / DOWN arrows and ENTER is also supported.
 */
public final class MainMenuScreen implements Screen {

    private static final String TAG = "MainMenuScreen";

    /** Vertical padding between buttons (pixels in viewport space). */
    private static final float BUTTON_PAD = 12f;

    /** Preferred button width (pixels in viewport space). */
    private static final float BUTTON_WIDTH = 280f;

    /** Preferred button height (pixels in viewport space). */
    private static final float BUTTON_HEIGHT = 48f;

    /** Extra space between the subtitle and the first button. */
    private static final float TITLE_BOTTOM_PAD = 40f;

    private final FFDCGame game;
    private final GameContext context;

    private Stage stage;
    private Skin skin;

    /** All focusable buttons in navigation order. */
    private TextButton[] navButtons;

    /** Index of the currently keyboard-focused button. */
    private int focusIndex;

    /**
     * Constructs the main menu screen.
     *
     * @param game    the main game instance used for screen transitions
     * @param context the shared dependency container
     */
    public MainMenuScreen(FFDCGame game, GameContext context) {
        this.game = game;
        this.context = context;
    }

    @Override
    public void show() {
        FitViewport viewport = new FitViewport(
            GameConstants.VIEWPORT_WIDTH,
            GameConstants.VIEWPORT_HEIGHT
        );
        stage = new Stage(viewport);

        skin = buildMinimalSkin();

        buildLayout();
        focusIndex = 0;
        updateButtonFocus();

        // Combine stage input with keyboard nav adapter
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new MenuKeyboardAdapter());
        Gdx.input.setInputProcessor(multiplexer);

        Gdx.app.log(TAG, "MainMenuScreen shown");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { /* no-op — desktop only */ }

    @Override
    public void resume() { /* no-op — desktop only */ }

    @Override
    public void hide() {
        // Release input so the next screen can claim it
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        Gdx.app.log(TAG, "MainMenuScreen disposed");
    }

    // -------------------------------------------------------------------------
    // Layout
    // -------------------------------------------------------------------------

    /** Builds and populates the root {@link Table} inside the stage. */
    private void buildLayout() {
        // ---- Labels ---------------------------------------------------------
        Label.LabelStyle titleStyle = new Label.LabelStyle(
            skin.get("title", BitmapFont.class), Color.GOLD);
        Label.LabelStyle subtitleStyle = new Label.LabelStyle(
            skin.get("default", BitmapFont.class), Color.LIGHT_GRAY);

        Label titleLabel = new Label("FINN FOUND DA CHEST", titleStyle);
        titleLabel.setAlignment(Align.center);

        Label subtitleLabel = new Label("The Land of Ooo Chronicles", subtitleStyle);
        subtitleLabel.setAlignment(Align.center);

        // ---- Buttons --------------------------------------------------------
        TextButton speedrunBtn  = new TextButton("Speedrun",    skin);
        TextButton storyBtn     = new TextButton("Story",       skin);
        TextButton explorationBtn = new TextButton("Exploration", skin);
        TextButton quitBtn      = new TextButton("Quit",        skin);

        navButtons = new TextButton[]{ speedrunBtn, storyBtn, explorationBtn, quitBtn };

        speedrunBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame();
            }
        });
        storyBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame();
            }
        });
        explorationBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame();
            }
        });
        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // ---- Layout ---------------------------------------------------------
        Table root = new Table();
        root.setFillParent(true);
        root.center();

        root.add(titleLabel).expandX().fillX().padBottom(8f).row();
        root.add(subtitleLabel).expandX().fillX().padBottom(TITLE_BOTTOM_PAD).row();

        root.add(speedrunBtn).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_PAD).row();
        root.add(storyBtn).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_PAD).row();
        root.add(explorationBtn).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_PAD * 2).row();
        root.add(quitBtn).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).row();

        stage.addActor(root);
    }

    // -------------------------------------------------------------------------
    // Skin
    // -------------------------------------------------------------------------

    /**
     * Builds a minimal {@link Skin} programmatically so we can display
     * Scene2D widgets without loading a skin file from disk.
     *
     * <p>Two fonts are registered:
     * <ul>
     *   <li>{@code "default"} — standard size, used for buttons and subtitle</li>
     *   <li>{@code "title"}   — scaled-up font for the main title</li>
     * </ul>
     *
     * @return a newly created skin owned by this screen
     */
    private static Skin buildMinimalSkin() {
        Skin s = new Skin();

        // Default (small) font
        BitmapFont defaultFont = new BitmapFont();
        defaultFont.getData().setScale(1.4f);
        s.add("default", defaultFont, BitmapFont.class);

        // Title font — same source, different scale
        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(2.5f);
        s.add("title", titleFont, BitmapFont.class);

        // Colors
        s.add("default", Color.WHITE, Color.class);
        s.add("focused", Color.CYAN, Color.class);

        // TextButton style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = defaultFont;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.CYAN;
        buttonStyle.downFontColor = Color.YELLOW;
        s.add("default", buttonStyle, TextButton.TextButtonStyle.class);

        // Focused button style (keyboard nav)
        TextButton.TextButtonStyle focusedStyle = new TextButton.TextButtonStyle();
        focusedStyle.font = defaultFont;
        focusedStyle.fontColor = Color.CYAN;
        focusedStyle.overFontColor = Color.CYAN;
        focusedStyle.downFontColor = Color.YELLOW;
        s.add("focused", focusedStyle, TextButton.TextButtonStyle.class);

        return s;
    }

    // -------------------------------------------------------------------------
    // Keyboard navigation
    // -------------------------------------------------------------------------

    /** Applies the correct style to every button to reflect current focus. */
    private void updateButtonFocus() {
        for (int i = 0; i < navButtons.length; i++) {
            navButtons[i].setStyle(
                i == focusIndex
                    ? skin.get("focused", TextButton.TextButtonStyle.class)
                    : skin.get("default", TextButton.TextButtonStyle.class)
            );
        }
    }

    /** Triggers the action of the currently focused button. */
    private void activateFocused() {
        TextButton btn = navButtons[focusIndex];
        btn.toggle(); // fires ChangeListener
    }

    /** Transitions to the play screen. */
    private void startGame() {
        game.setScreen(new PlayScreen(context));
    }

    // -------------------------------------------------------------------------
    // Inner class — keyboard adapter
    // -------------------------------------------------------------------------

    /**
     * Handles arrow-key navigation and ENTER to activate the focused button.
     * This adapter is layered behind the Stage so Scene2D still receives all
     * other events first.
     */
    private final class MenuKeyboardAdapter extends InputAdapter {

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    focusIndex = (focusIndex - 1 + navButtons.length) % navButtons.length;
                    updateButtonFocus();
                    return true;
                case Input.Keys.DOWN:
                    focusIndex = (focusIndex + 1) % navButtons.length;
                    updateButtonFocus();
                    return true;
                case Input.Keys.ENTER:
                case Input.Keys.NUMPAD_ENTER:
                    activateFocused();
                    return true;
                default:
                    return false;
            }
        }
    }
}
