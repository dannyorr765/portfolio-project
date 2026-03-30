/**
 * Creates additional methods for the Synapse component layered on top of the
 * kernel.
 */
public interface Synapse extends SynapseKernel {

    /**
     * Applies this synapse's weight to the given signal.
     *
     * @param signal
     *            the incoming signal value
     * @return the weighted output signal, or 0 if this synapse is disabled
     * @ensures applyWeight = (signal * this.weight) >> 7 clamped to [0, 255],
     *          or 0 if not this.enabled
     */
    int applyWeight(int signal);

    /**
     * Adds w to this synapse's weight.
     *
     * @param w
     *            the value to add
     * @updates this.weight
     * @ensures this.weight = clamp(#this.weight + w)
     */
    void addWeight(int w);

    /**
     * Subtracts w from this synapse's weight.
     *
     * @param w
     *            the value to subtract
     * @updates this.weight
     * @ensures this.weight = clamp(#this.weight - w)
     */
    void removeWeight(int w);

    /**
     * Adds e to this synapse's eligibility.
     *
     * @param e
     *            the value to add
     * @updates this.eligibility
     * @ensures this.eligibility = clamp(#this.eligibility + e)
     */
    void addEligibility(int e);

    /**
     *
     * Removes e from this synapse's eligibility.
     *
     * @param e
     *            the value to remove
     * @updates this.eligibility
     * @ensures this.eligibility = clamp(#this.eligibility - e)
     */
    void removeEligibility(int e);
}
