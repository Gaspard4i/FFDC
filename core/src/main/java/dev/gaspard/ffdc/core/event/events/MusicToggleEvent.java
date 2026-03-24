package dev.gaspard.ffdc.core.event.events;

import dev.gaspard.ffdc.core.event.GameEvent;

/**
 * Event fired when the player requests a music toggle (play / pause).
 * Subscribers can react to music state changes without coupling to the audio system.
 */
public record MusicToggleEvent() implements GameEvent {
}
