package dev.gaspard.ffdc.core.event;

/**
 * Functional interface for handling game events.
 *
 * @param <T> the event type to handle
 */
@FunctionalInterface
public interface GameEventListener<T extends GameEvent> {

    /**
     * Handles the given event.
     *
     * @param event the event to handle
     */
    void handle(T event);
}
