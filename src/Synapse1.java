/**
 * Represents a directed synaptic connection from one neuron to another,
 * carrying a weighted signal.
 *
 * @convention this.weight is the signal multiplier for this synapse
 * @correspondence this = (targetNeuronID, weight, enabled)
 *
 * @author Danny Orr
 *
 *         Something I should mention. I have already partially made this
 *         project in C++. The reasons that I am choosing to do the same thing
 *         for this portfolio project is so I can formalize what I have already
 *         done (without the vibe coding), but translating from C++ has
 *         introduced its own issues. The main one is that Java does not
 *         natively support int8's, which is how I was able to compress every
 *         synapse into 4 bytes in my C++ version, and why I chose to use
 *         integers rather than floats (more detail about that below) for most
 *         of the data values in this project. In this translation, I will
 *         continue to use integers, and keep in mind that most integer values
 *         (excluding targetNeuronID) must stay between 0-255. To ensure that
 *         values are kept between 0-255, I have implemented a helper clamp
 *         method and used it throughout the project. I figured this would be
 *         fitting given, in the translation back to C++, I will have to do this
 *         anyway.
 */
public class Synapse1 extends SynapseSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * The weight of this synapse, used to scale signals passed through it.
     */
    private int weight;

    /**
     * Whether this synapse is currently active and able to pass signals.
     */
    private boolean enabled;

    /**
     * The ID of the neuron this synapse points to.
     */
    private int targetNeuronID;

    /**
     * The eligibility of this synapse, used determine whether the Synapse's
     * weight should increase or decrease after a reward.
     */
    private int eligibility;

    /**
     * int8 maximum value (used for clamping).
     */
    private static final int MAX_INT8 = 255;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        final int defaultWeight = 128;
        this.weight = defaultWeight;
        this.enabled = true;
        this.targetNeuronID = 0;
        this.eligibility = 0;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Constructor for Synapse.
     *
     * @param targetNeuronID
     *            the ID of the target neuron.
     */
    public Synapse1(int targetNeuronID) {
        this.createNewRep();
        this.targetNeuronID = targetNeuronID;
    }

    /*
     * Standard Methods -------------------------------------------------------
     */

    @Override
    public final Synapse newInstance() {
        return new Synapse1(0);
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Synapse source) {
        Synapse1 localSource = (Synapse1) source;
        this.weight = localSource.weight;
        this.enabled = localSource.enabled;
        this.targetNeuronID = localSource.targetNeuronID;
        this.eligibility = localSource.eligibility;
        localSource.createNewRep();
    }

    /*
     * Helper methods ----------------------------------------------------------
     */

    /**
     * Clamps value to the range [0, 255] to simulate int8 behavior.
     *
     * @param value
     *            value to clamp
     * @return value clamped to [0, 255]
     */
    private static int clamp(int value) {
        return Math.max(0, Math.min(MAX_INT8, value));
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    /**
     * Sets the weight of this synapse to w.
     *
     * @param w
     *            the new weight value
     * @updates this.weight
     * @ensures this.weight = w
     */
    @Override
    public final void setWeight(int w) {
        this.weight = clamp(w);
    }

    /**
     * Returns the weight of this synapse.
     *
     * @return this.weight
     */
    @Override
    public final int getWeight() {
        return this.weight;
    }

    /**
     * Sets the enabled flag of this synapse to e.
     *
     * @param e
     *            the new enabled value
     * @updates this.enabled
     * @ensures this.enabled = e
     */
    @Override
    public final void setEnabled(boolean e) {
        this.enabled = e;
    }

    /**
     * Returns whether this synapse is enabled.
     *
     * @return this.enabled
     */
    @Override
    public final boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Returns the ID of the target neuron.
     *
     * @return this.targetNeuronID
     */
    @Override
    public final int getTargetID() {
        return this.targetNeuronID;
    }

    /**
     * Sets the eligibility value of this synapse to e.
     *
     * @param e
     *            the new eligibility value
     * @updates this.eligiblity
     * @ensures this.eligibility = e
     */
    @Override
    public final void setEligibility(int e) {
        this.eligibility = e;
    }

    /**
     * Returns the eligibility of this synapse.
     *
     * @return this.eligibility
     */
    @Override
    public final int getEligibility() {
        return this.eligibility;
    }
}
