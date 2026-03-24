package dev.gaspard.ffdc.core.event.events;

import dev.gaspard.ffdc.core.event.GameEvent;

/**
 * Event fired when the player requests a pause or un-pause.
 * Subscribers (e.g. the pause overlay) can react without coupling to the play screen.
 */
public record PauseRequestedEvent() implements GameEvent {
}
