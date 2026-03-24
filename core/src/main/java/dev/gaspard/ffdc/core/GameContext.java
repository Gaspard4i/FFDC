package dev.gaspard.ffdc.core;

import dev.gaspard.ffdc.core.event.EventBus;
import dev.gaspard.ffdc.input.GameInputProcessor;
import dev.gaspard.ffdc.input.KeyBindings;
import java.util.Objects;

/**
 * Central dependency container replacing the God Object pattern.
 * Constructed once in {@link dev.gaspard.ffdc.FFDCGame} and passed to all screens.
 */
public final class GameContext {

    public final EventBus eventBus;
    public final KeyBindings keyBindings;
    public final GameInputProcessor inputProcessor;

    public GameContext(
            EventBus eventBus,
            KeyBindings keyBindings,
            GameInputProcessor inputProcessor) {
        this.eventBus = Objects.requireNonNull(eventBus);
        this.keyBindings = Objects.requireNonNull(keyBindings);
        this.inputProcessor = Objects.requireNonNull(inputProcessor);
    }

    /**
     * Creates a GameContext with default components.
     *
     * @return a fully initialized context
     */
    public static GameContext createDefault() {
        EventBus eventBus = new EventBus();
        KeyBindings keyBindings = new KeyBindings();
        GameInputProcessor inputProcessor =
                new GameInputProcessor(keyBindings, eventBus);
        return new GameContext(eventBus, keyBindings, inputProcessor);
    }
}
