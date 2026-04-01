import components.set.Set;
import components.set.Set1L;

/**
 * Represents a biological neuron. Fires when potential exceeds its threshold
 * and propagates signals through its stored set of {@code Synapse} objects.
 *
 * @convention 0 <= this.potential <= 255
 * @convention 0 <= this.threshold <= 255
 * @convention 0 <= this.decay <= 255
 * @convention 0 <= this.activity <= 7
 * @correspondence this = (potential, threshold, decay, fired, activity,
 *                 synapses)
 *
 * @author Danny Orr
 */
public class Neuron1 implements Neuron {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * This neuron's membrane potential. Increases when signals are received and
     * decreases each update step via decay.
     */
    private int potential;

    /**
     * The potential value this neuron must reach in order to fire.
     */
    private int threshold;

    /**
     * The rate at which this neuron's potential decreases each update step.
     */
    private int decay;

    /**
     * Whether this neuron fired on the last update step.
     */
    private boolean fired;

    /**
     * This neuron's recent activity level, in the range [0, 7]. Increases when
     * the neuron fires and decays passively over time.
     */
    private int activity;

    /**
     * The set of synapses through which this neuron propagates signals.
     */
    private Set<Synapse> synapses;

    /*
     * Constructors ------------------------------------------------------------
     */

    /**
     * No-argument constructor for Neuron.
     */
    public Neuron1() {
        this.potential = 0;
        final int defaultThreshold = 15;
        this.threshold = defaultThreshold;
        final int defaultDecay = 255;
        this.decay = defaultDecay;
        this.fired = false;
        this.activity = 0;
        this.synapses = new Set1L<>();
    }

    /*
     * Standard Methods --------------------------------------------------------
     */

    @Override
    public final Neuron newInstance() {
        return new Neuron1();
    }

    @Override
    public final void clear() {
        this.potential = 0;
        final int defaultThreshold = 15;
        this.threshold = defaultThreshold;
        final int defaultDecay = 255;
        this.decay = defaultDecay;
        this.fired = false;
        this.activity = 0;
        this.synapses.clear();
    }

    @Override
    public final void transferFrom(Neuron source) {
        this.potential = source.getPotential();
        this.threshold = source.getThreshold();
        this.decay = source.getDecay();
        this.fired = source.getFired();
        this.activity = source.getActivity();
        this.synapses.clear();
        int count = source.synapseCount();
        for (int i = 0; i < count; i++) {
            Synapse s = source.removeAny();
            this.synapses.add(s);
        }
    }

    /*
     * Kernel methods ----------------------------------------------------------
     */

    /**
     * Adds synapse {@code s} to this neuron's synapse set.
     *
     * @param s
     *            the synapse to add
     * @updates this.synapses
     * @ensures this.synapses = #this.synapses union {s}
     */
    @Override
    public final void addSynapse(Synapse s) {
        this.synapses.add(s);
    }

    /**
     * Removes synapse {@code s} from this neuron's synapse set.
     *
     * @param s
     *            the synapse to remove
     * @updates this.synapses
     * @requires s is in this.synapses
     * @ensures this.synapses = #this.synapses \ {s}
     */
    @Override
    public final void removeSynapse(Synapse s) {
        this.synapses.remove(s);
    }

    /**
     * Removes and returns an arbitrary synapse from this.synapses.
     *
     * @return an arbitrary synapse from this.synapses
     * @updates this.synapses
     * @requires this.synapses /= {}
     * @ensures removeAny is in #this.synapses and this.synapses =
     *          #this.synapses \ {removeAny}
     */
    @Override
    public final Synapse removeAny() {
        return this.synapses.removeAny();
    }

    /**
     * Returns the number of synapses in this neuron's synapse set.
     *
     * @return |this.synapses|
     */
    @Override
    public final int synapseCount() {
        return this.synapses.size();
    }

    /**
     * Returns this neuron's membrane potential.
     *
     * @return this.potential
     */
    @Override
    public final int getPotential() {
        return this.potential;
    }

    /**
     * Sets this neuron's membrane potential to {@code p}.
     *
     * @param p
     *            the new potential value
     * @updates this.potential
     * @requires 0 <= p <= 255
     * @ensures this.potential = p
     */
    @Override
    public final void setPotential(int p) {
        this.potential = p;
    }

    /**
     * Returns this neuron's firing threshold.
     *
     * @return this.threshold
     */
    @Override
    public final int getThreshold() {
        return this.threshold;
    }

    /**
     * Sets this neuron's firing threshold to {@code t}.
     *
     * @param t
     *            the new threshold value
     * @updates this.threshold
     * @requires 0 <= t <= 255
     * @ensures this.threshold = t
     */
    @Override
    public final void setThreshold(int t) {
        this.threshold = t;
    }

    /**
     * Returns this neuron's decay rate.
     *
     * @return this.decay
     */
    @Override
    public final int getDecay() {
        return this.decay;
    }

    /**
     * Sets this neuron's decay rate to {@code d}.
     *
     * @param d
     *            the new decay value
     * @updates this.decay
     * @requires 0 <= d <= 255
     * @ensures this.decay = d
     */
    @Override
    public final void setDecay(int d) {
        this.decay = d;
    }

    /**
     * Returns whether this neuron fired on the last update step.
     *
     * @return this.fired
     */
    @Override
    public final boolean getFired() {
        return this.fired;
    }

    /**
     * Sets this neuron's fired flag to {@code f}.
     *
     * @param f
     *            the new fired value
     * @updates this.fired
     * @ensures this.fired = f
     */
    @Override
    public final void setFired(boolean f) {
        this.fired = f;
    }

    /**
     * Returns this neuron's activity level.
     *
     * @return this.activity
     */
    @Override
    public final int getActivity() {
        return this.activity;
    }

    /**
     * Sets this neuron's activity level to {@code a}.
     *
     * @param a
     *            the new activity value
     * @updates this.activity
     * @requires 0 <= a <= 7
     * @ensures this.activity = a
     */
    @Override
    public final void setActivity(int a) {
        this.activity = a;
    }

    /*
     * Secondary methods -------------------------------------------------------
     */

    /**
     * Fires this neuron, setting its fired flag and bumping its activity.
     *
     * @updates this.fired, this.activity
     * @ensures this.fired = true and this.activity = min(#this.activity + 1, 7)
     */
    @Override
    public final void fire() {
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
    public final void applyDecay(int d) {
        this.setPotential(Math.max(this.getPotential() - d, 0));
    }

    /**
     * Resets this neuron's fired flag and potential to their default values.
     *
     * @updates this.fired, this.potential
     * @ensures this.fired = false and this.potential = 0
     */
    @Override
    public final void reset() {
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
    public final void bumpActivity(int amount) {
        final int maxActivity = 7;
        this.setActivity(Math.min(this.getActivity() + amount, maxActivity));
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
    public final void decayActivity(int amount) {
        this.setActivity(Math.max(this.getActivity() - amount, 0));
    }

}
