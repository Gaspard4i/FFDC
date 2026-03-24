package dev.gaspard.ffdc.core.event;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.Objects;

/**
 * Synchronous event bus for decoupled communication between game systems.
 * Uses LibGDX collections to avoid GC pressure in the game loop.
 */
public final class EventBus {

    private final ObjectMap<Class<?>, Array<GameEventListener<?>>> listeners;

    public EventBus() {
        this.listeners = new ObjectMap<>();
    }

    /**
     * Subscribes a listener to events of the given type.
     *
     * @param type     the event class to listen for
     * @param listener the listener to invoke when the event is published
     * @param <T>      the event type
     */
    public <T extends GameEvent> void subscribe(
            Class<T> type, GameEventListener<T> listener) {
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(listener, "listener must not be null");
        Array<GameEventListener<?>> list = listeners.get(type);
        if (list == null) {
            list = new Array<>();
            listeners.put(type, list);
        }
        list.add(listener);
    }

    /**
     * Publishes an event to all registered listeners of its type.
     *
     * @param event the event to publish
     * @param <T>   the event type
     */
    @SuppressWarnings("unchecked")
    public <T extends GameEvent> void publish(T event) {
        Objects.requireNonNull(event, "event must not be null");
        Array<GameEventListener<?>> list = listeners.get(event.getClass());
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size; i++) {
            ((GameEventListener<T>) list.get(i)).handle(event);
        }
    }

    /**
     * Removes a listener for the given event type.
     *
     * @param type     the event class
     * @param listener the listener to remove
     * @param <T>      the event type
     */
    public <T extends GameEvent> void unsubscribe(
            Class<T> type, GameEventListener<T> listener) {
        Array<GameEventListener<?>> list = listeners.get(type);
        if (list != null) {
            list.removeValue(listener, true);
        }
    }

    /** Removes all listeners. Useful for cleanup between screens. */
    public void clear() {
        listeners.clear();
    }

    /**
     * Returns the number of listeners registered for the given event type.
     * Visible for testing.
     *
     * @param type the event class
     * @return listener count
     */
    int listenerCount(Class<? extends GameEvent> type) {
        Array<GameEventListener<?>> list = listeners.get(type);
        return list == null ? 0 : list.size;
    }
}
