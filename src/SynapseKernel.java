import components.standard.Standard;

/**
 * Kernel interface for the Synapse component, creating the accesstors to
 * Synapse objects.
 */
public interface SynapseKernel extends Standard<Synapse> {

    /**
     * Sets the weight of this synapse to w.
     *
     * @param w
     *            the new weight value
     * @updates this.weight
     * @ensures this.weight = clamp(w)
     */
    void setWeight(int w);

    /**
     * Returns the weight of this synapse.
     *
     * @return this.weight
     */
    int getWeight();

    /**
     * Sets the enabled flag of this synapse to e.
     *
     * @param e
     *            the new enabled value
     * @updates this.enabled
     * @ensures this.enabled = e
     */
    void setEnabled(boolean e);

    /**
     * Returns whether this synapse is enabled.
     *
     * @return this.enabled
     */
    boolean isEnabled();

    /**
     * Returns the ID of the target neuron.
     *
     * @return this.targetNeuronID
     */
    int getTargetID();
}
