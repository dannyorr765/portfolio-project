/**
 * Abstract class for the Synapse component, providing implementations of
 * secondary methods using only kernel and Standard methods.
 *
 * @author Danny Orr
 */
public abstract class SynapseSecondary implements Synapse {

    /**
     * How far the weight is offset (This will make sense later).
     */
    private static final int WEIGHT_SHIFT = 7; // 2^7 = 128

    /**
     * int8 maximum value (used for clamping).
     */
    private static final int MAX_INT8 = 255;

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
     * Common methods ---------------------------------------------------------
     */

    @Override
    public final String toString() {
        return "Synapse{target=" + this.getTargetID() + ", weight="
                + this.getWeight() + ", enabled=" + this.isEnabled()
                + ", eligibility=" + this.getEligibility() + "}";
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Synapse)) {
            return false;
        }
        Synapse other = (Synapse) obj;
        return this.getTargetID() == other.getTargetID()
                && this.getWeight() == other.getWeight()
                && this.isEnabled() == other.isEnabled()
                && this.getEligibility() == other.getEligibility();
    }

    @Override
    public final int hashCode() {
        return this.getTargetID() + this.getWeight() + this.getEligibility();
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
