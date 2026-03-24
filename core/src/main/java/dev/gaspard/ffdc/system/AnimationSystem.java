package dev.gaspard.ffdc.system;

/**
 * Stateless utility system that converts accumulated state time into a
 * sprite frame index.
 *
 * <p>All methods are pure functions — no state is held between calls.
 */
public final class AnimationSystem {

    /** Private constructor: utility class, not instantiable. */
    private AnimationSystem() {}

    /**
     * Returns the zero-based frame index for the current animation state time.
     *
     * <p>The animation loops: after the last frame the index wraps back to 0.
     *
     * @param stateTime     accumulated time spent in the current animation (seconds)
     * @param totalFrames   total number of frames in the animation strip
     * @param frameDuration how long each frame is displayed (seconds)
     * @return frame index in {@code [0, totalFrames)}
     */
    public static int getCurrentFrame(float stateTime, int totalFrames, float frameDuration) {
        return (int) (stateTime / frameDuration) % totalFrames;
    }
}
