/**
 * Interface for the Neuron component, layering secondary methods on top of the
 * kernel.
 *
 * @author Danny Orr
 */
public interface Neuron extends NeuronKernel {

    /**
     * Fires this neuron, setting its fired flag and bumping its activity.
     *
     * @updates this.fired, this.activity
     * @ensures this.fired = true and this.activity = min(#this.activity + 1, 7)
     */
    void fire();

    /**
     * Decreases this neuron's potential by {@code d}, clamped to 0.
     *
     * @param d
     *            the amount to decay the potential by
     * @updates this.potential
     * @requires 0 <= d <= 255
     * @ensures this.potential = max(#this.potential - d, 0)
     */
    void applyDecay(int d);

    /**
     * Resets this neuron's fired flag and potential to their default values.
     *
     * @updates this.fired, this.potential
     * @ensures this.fired = false and this.potential = 0
     */
    void reset();

    /**
     * Increases this neuron's activity level by {@code amount}, clamped to 7.
     *
     * @param amount
     *            the amount to increase activity by
     * @updates this.activity
     * @requires 0 <= amount <= 7
     * @ensures this.activity = min(#this.activity + amount, 7)
     */
    void bumpActivity(int amount);

    /**
     * Decreases this neuron's activity level by {@code amount}, clamped to 0.
     *
     * @param amount
     *            the amount to decrease activity by
     * @updates this.activity
     * @requires 0 <= amount <= 7
     * @ensures this.activity = max(#this.activity - amount, 0)
     */
    void decayActivity(int amount);

}
