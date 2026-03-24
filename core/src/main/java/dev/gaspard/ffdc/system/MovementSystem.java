package dev.gaspard.ffdc.system;

import dev.gaspard.ffdc.entity.Direction;
import dev.gaspard.ffdc.entity.Entity;
import dev.gaspard.ffdc.input.GameInputProcessor;

/**
 * Stateless system that moves entities based on player input and collision.
 *
 * <p>Each call to {@link #update} reads the current held direction from the
 * input processor, computes the candidate new position, verifies it against
 * the collision system, and applies the move only when it is safe.
 */
public final class MovementSystem {

    /**
     * Updates the position and direction of an entity for one frame.
     *
     * <p>The entity direction is always updated (even when blocked) so that
     * the animation system can display the correct facing sprite.
     *
     * @param entity    the entity to move
     * @param input     the input processor polled for the current direction
     * @param collision the collision system used to validate the new position
     * @param delta     time elapsed since the last frame (seconds)
     */
    public void update(Entity entity, GameInputProcessor input,
                       CollisionSystem collision, float delta) {
        Direction dir = input.getDirection();
        entity.direction = dir;

        if (dir == Direction.IDLE) {
            return;
        }

        float newX = entity.worldX + entity.speed * dir.dx * delta;
        float newY = entity.worldY + entity.speed * dir.dy * delta;

        if (!collision.checkTile(entity, newX, newY)) {
            entity.worldX = newX;
            entity.worldY = newY;
        }
    }
}
