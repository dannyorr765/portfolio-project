import components.standard.Standard;

/**
 * Kernel interface for the Neuron component, creating the accessors to it's
 * data fields and Synapse set.
 *
 * @author Danny Orr
 */
public interface NeuronKernel extends Standard<Neuron> {

    /**
     * Adds synapse {@code s} to this neuron's synapse set.
     *
     * @param s
     *            the synapse to add
     * @updates this.synapses
     * @ensures this.synapses = #this.synapses union {s}
     */
    void addSynapse(Synapse s);

    /**
     * Removes synapse {@code s} from this neuron's synapse set.
     *
     * @param s
     *            the synapse to remove
     * @updates this.synapses
     * @requires s is in this.synapses
     * @ensures this.synapses = #this.synapses \ {s}
     */
    void removeSynapse(Synapse s);

    /**
     * Removes and returns an arbitrary synapse from this.synapses.
     *
     * @return an arbitrary synapse from this.synapses
     * @updates this.synapses
     * @requires this.synapses /= {}
     * @ensures removeAny is in #this.synapses and this.synapses =
     *          #this.synapses \ {removeAny}
     */
    Synapse removeAny();

    /**
     * Returns the number of synapses in this neuron's synapse set.
     *
     * @return |this.synapses|
     */
    int synapseCount();

    /**
     * Returns this neuron's membrane potential.
     *
     * @return this.potential
     */
    int getPotential();

    /**
     * Sets this neuron's membrane potential to {@code p}.
     *
     * @param p
     *            the new potential value
     * @updates this.potential
     * @requires 0 <= p <= 255
     * @ensures this.potential = p
     */
    void setPotential(int p);

    /**
     * Returns this neuron's firing threshold.
     *
     * @return this.threshold
     */
    int getThreshold();

    /**
     * Sets this neuron's firing threshold to {@code t}.
     *
     * @param t
     *            the new threshold value
     * @updates this.threshold
     * @requires 0 <= t <= 255
     * @ensures this.threshold = t
     */
    void setThreshold(int t);

    /**
     * Returns this neuron's decay rate.
     *
     * @return this.decay
     */
    int getDecay();

    /**
     * Sets this neuron's decay rate to {@code d}.
     *
     * @param d
     *            the new decay value
     * @updates this.decay
     * @requires 0 <= d <= 255
     * @ensures this.decay = d
     */
    void setDecay(int d);

    /**
     * Returns whether this neuron fired on the last update step.
     *
     * @return this.fired
     */
    boolean getFired();

    /**
     * Sets this neuron's fired flag to {@code f}.
     *
     * @param f
     *            the new fired value
     * @updates this.fired
     * @ensures this.fired = f
     */
    void setFired(boolean f);

    /**
     * Returns this neuron's activity level.
     *
     * @return this.activity
     */
    int getActivity();

    /**
     * Sets this neuron's activity level to {@code a}.
     *
     * @param a
     *            the new activity value
     * @updates this.activity
     * @requires 0 <= a <= 7
     * @ensures this.activity = a
     */
    void setActivity(int a);

}
