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
public class Synapse1 implements Synapse {

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
     * How far the weight is offset (This will make sense later).
     */
    private static final int WEIGHT_SHIFT = 7; // 2^7 = 128

    /**
     * int8 maximum value (used for clamping).
     */
    private static final int MAX_INT8 = 255;

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
        this.targetNeuronID = targetNeuronID;
        final int defaultWeight = 128;
        this.weight = defaultWeight;
        this.enabled = true;
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
        final int defaultWeight = 128;
        this.weight = defaultWeight;
        this.enabled = true;
        this.targetNeuronID = 0;
    }

    @Override
    public final void transferFrom(Synapse source) {
        this.weight = source.getWeight();
        this.enabled = source.isEnabled();
        this.targetNeuronID = source.getTargetID();
        source.clear();
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

    /*
     * Secondary methods ------------------------------------------------------
     */

    /**
     * Paragraph time! This is where I'm going to explain why the heck I'm using
     * ints for everything rather than floats like a sane person. So floats are
     * typically 4 bytes, right? And int8's are one byte. When you're working
     * with networks that contain billions of neurons and billions of synapses,
     * memory bandwidth is a huge constricting factor. This, on top of the fact
     * that biological neurons/synapses are fairly imprecise and require noise,
     * makes imprecise ints a better fit than floats, or at least I theorize
     * they will. Plus, integer math (especially bit shifting like below) is
     * extremely quick. With a fixed weight of 128, any signal passes through
     * unchanged. Drop the weight to 64 and every signal gets halved. Bump it to
     * 256 and every signal doubles. This way, instead of multiplying two floats
     * together, it's one integer multiplication plus a cheap bitshift. Will it
     * work? No clue. Do I want to try and regret it when it fails? Heck yeah!
     *
     * @param signal
     *            incoming signal
     * @return updated signal
     */
    @Override
    public final int applyWeight(int signal) {
        if (!this.isEnabled()) {
            return 0;
        }
        return clamp((signal * this.getWeight()) >> WEIGHT_SHIFT);
    }

    /**
     * Adds value w to this.weight.
     *
     * @param w
     *            weight to add
     * @updates this.weight
     */
    @Override
    public final void addWeight(int w) {
        this.setWeight(this.getWeight() + w);
    }

    /**
     * Subtracts value w from this.weight.
     *
     * @param w
     *            weight to subtract
     * @updates this.weight
     */
    @Override
    public final void removeWeight(int w) {
        this.setWeight(this.getWeight() - w);
    }

    /**
     * Adds e to this synapse's eligibility.
     *
     * @param e
     *            the value to add
     * @updates this.eligibility
     * @ensures this.eligibility = clamp(#this.eligibility + e)
     */
    @Override
    public final void addEligibility(int e) {
        this.setEligibility(this.getEligibility() + e);
    }

    /**
     *
     * Removes e from this synapse's eligibility.
     *
     * @param e
     *            the value to remove
     * @updates this.eligibility
     * @ensures this.eligibility = clamp(#this.eligibility - e)
     */
    @Override
    public final void removeEligibility(int e) {
        this.setEligibility(this.getEligibility() - e);
    }
}
