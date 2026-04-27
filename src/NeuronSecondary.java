/**
 * Abstract class for the Neuron component, providing implementations of
 * secondary methods using only kernel and Standard methods.
 *
 * @author Danny Orr
 */
public abstract class NeuronSecondary implements Neuron {

    /*
     * Common methods ---------------------------------------------------------
     */

    @Override
    public final String toString() {
        return "Neuron{potential=" + this.getPotential() + ", threshold="
                + this.getThreshold() + ", decay=" + this.getDecay()
                + ", fired=" + this.getFired() + ", activity="
                + this.getActivity() + ", synapses=" + this.synapseCount()
                + "}";
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Neuron)) {
            return false;
        }
        Neuron other = (Neuron) obj;
        return this.getPotential() == other.getPotential()
                && this.getThreshold() == other.getThreshold()
                && this.getDecay() == other.getDecay()
                && this.getFired() == other.getFired()
                && this.getActivity() == other.getActivity()
                && this.synapseCount() == other.synapseCount();
    }

    @Override
    public final int hashCode() {
        return this.getPotential() + this.getThreshold() + this.getDecay()
                + this.getActivity() + this.synapseCount();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    /**
     * Fires this neuron, setting its fired flag and bumping its activity.
     *
     * @updates this.fired, this.activity
     * @ensures this.fired = true and this.activity = min(#this.activity + 1, 7)
     */
    @Override
    public void fire() {
        this.setFired(true);
        this.bumpActivity(1);
    }

    /**
     * Decreases this neuron's potential by {@code d}, clamped to 0.
     *
     * @param d
     *            the amount to decay the potential by
     * @updates this.potential
     * @requires 0 <= d <= 255
     * @ensures this.potential = max(#this.potential - d, 0)
     */
    @Override
    public void applyDecay(int d) {
        this.setPotential(Math.max(this.getPotential() - d, 0));
    }

    /**
     * Resets this neuron's fired flag and potential to their default values.
     *
     * @updates this.fired, this.potential
     * @ensures this.fired = false and this.potential = 0
     */
    @Override
    public void reset() {
        this.setFired(false);
        this.setPotential(0);
    }

    /**
     * Increases this neuron's activity level by {@code amount}, clamped to 7.
     *
     * @param amount
     *            the amount to increase activity by
     * @updates this.activity
     * @requires 0 <= amount <= 7
     * @ensures this.activity = min(#this.activity + amount, 7)
     */
    @Override
    public void bumpActivity(int amount) {
        final int magicNumberShutUp = 7;
        this.setActivity(
                Math.min(this.getActivity() + amount, magicNumberShutUp));
    }

    /**
     * Decreases this neuron's activity level by {@code amount}, clamped to 0.
     *
     * @param amount
     *            the amount to decrease activity by
     * @updates this.activity
     * @requires 0 <= amount <= 7
     * @ensures this.activity = max(#this.activity - amount, 0)
     */
    @Override
    public void decayActivity(int amount) {
        this.setActivity(Math.max(this.getActivity() - amount, 0));
    }

}
