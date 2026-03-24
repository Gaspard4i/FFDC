package dev.gaspard.ffdc.input;

import com.badlogic.gdx.InputAdapter;
import dev.gaspard.ffdc.core.event.EventBus;
import dev.gaspard.ffdc.entity.Direction;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Processes keyboard input and translates key codes to {@link InputAction}s.
 * Maintains a set of currently held actions for polling by game systems.
 */
public final class GameInputProcessor extends InputAdapter {

    private final KeyBindings bindings;
    private final EventBus eventBus;
    private final Set<InputAction> heldActions;

    public GameInputProcessor(KeyBindings bindings, EventBus eventBus) {
        this.bindings = Objects.requireNonNull(bindings);
        this.eventBus = Objects.requireNonNull(eventBus);
        this.heldActions = EnumSet.noneOf(InputAction.class);
    }

    @Override
    public boolean keyDown(int keycode) {
        bindings.resolve(keycode).ifPresent(action -> {
            heldActions.add(action);
        });
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        bindings.resolve(keycode).ifPresent(action -> {
            heldActions.remove(action);
        });
        return true;
    }

    /** Returns true if the given action is currently held. */
    public boolean isHeld(InputAction action) {
        return heldActions.contains(action);
    }

    /** Returns true if any movement action is currently held. */
    public boolean isMoving() {
        return heldActions.contains(InputAction.MOVE_UP)
                || heldActions.contains(InputAction.MOVE_DOWN)
                || heldActions.contains(InputAction.MOVE_LEFT)
                || heldActions.contains(InputAction.MOVE_RIGHT);
    }

    /**
     * Returns the current movement direction based on held actions.
     * Prioritizes vertical over horizontal when both are held.
     *
     * @return current direction, or IDLE if no movement keys held
     */
    public Direction getDirection() {
        if (heldActions.contains(InputAction.MOVE_UP)) {
            return Direction.UP;
        }
        if (heldActions.contains(InputAction.MOVE_DOWN)) {
            return Direction.DOWN;
        }
        if (heldActions.contains(InputAction.MOVE_LEFT)) {
            return Direction.LEFT;
        }
        if (heldActions.contains(InputAction.MOVE_RIGHT)) {
            return Direction.RIGHT;
        }
        return Direction.IDLE;
    }

    /** Clears all held actions. Call on screen transitions. */
    public void reset() {
        heldActions.clear();
    }
}
