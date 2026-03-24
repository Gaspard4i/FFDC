package dev.gaspard.ffdc.input;

import com.badlogic.gdx.Input.Keys;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Maps physical keyboard keys to abstract {@link InputAction}s.
 * Supports ZQSD (AZERTY), WASD (QWERTY), and arrow keys simultaneously.
 */
public final class KeyBindings {

    private final Map<Integer, InputAction> bindings;

    public KeyBindings() {
        this.bindings = new HashMap<>();
        loadDefaults();
    }

    /** Loads default key bindings for all three input schemes. */
    private void loadDefaults() {
        // ZQSD (French AZERTY)
        bind(Keys.Z, InputAction.MOVE_UP);
        bind(Keys.S, InputAction.MOVE_DOWN);
        bind(Keys.Q, InputAction.MOVE_LEFT);
        bind(Keys.D, InputAction.MOVE_RIGHT);

        // WASD (QWERTY)
        bind(Keys.W, InputAction.MOVE_UP);
        bind(Keys.A, InputAction.MOVE_LEFT);

        // Arrow keys
        bind(Keys.UP, InputAction.MOVE_UP);
        bind(Keys.DOWN, InputAction.MOVE_DOWN);
        bind(Keys.LEFT, InputAction.MOVE_LEFT);
        bind(Keys.RIGHT, InputAction.MOVE_RIGHT);

        // Actions
        bind(Keys.P, InputAction.PAUSE);
        bind(Keys.ESCAPE, InputAction.PAUSE);
        bind(Keys.M, InputAction.TOGGLE_MUSIC);
        bind(Keys.E, InputAction.INTERACT);
        bind(Keys.ENTER, InputAction.INTERACT);
        bind(Keys.T, InputAction.TOGGLE_DEBUG);
        bind(Keys.SHIFT_LEFT, InputAction.SPRINT);
        bind(Keys.SHIFT_RIGHT, InputAction.SPRINT);
    }

    /**
     * Binds a key code to an input action.
     *
     * @param keyCode the LibGDX key code
     * @param action  the action to map to
     */
    public void bind(int keyCode, InputAction action) {
        bindings.put(keyCode, action);
    }

    /**
     * Resolves a key code to its bound action.
     *
     * @param keyCode the LibGDX key code
     * @return the bound action, or empty if unbound
     */
    public Optional<InputAction> resolve(int keyCode) {
        return Optional.ofNullable(bindings.get(keyCode));
    }

    /**
     * Returns the number of registered bindings. Visible for testing.
     *
     * @return binding count
     */
    public int bindingCount() {
        return bindings.size();
    }
}
