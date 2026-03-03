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
 *
 *         Please ignore the Checkstyle errors. I'm not manually defining 10
 *         variables just to use each of them once
 */
public class Synapse {

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
     * @param weight
     *            the initial weight of the synapse.
     */
    public Synapse(int targetNeuronID, int weight) {
        this.targetNeuronID = targetNeuronID;
        this.weight = weight;
        this.enabled = true;
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
    public final void setWeight(int w) {
        this.weight = clamp(w);
    }

    /**
     * Returns the weight of this synapse.
     *
     * @return this.weight
     */
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
    public final void setEnabled(boolean e) {
        this.enabled = e;
    }

    /**
     * Returns whether this synapse is enabled.
     *
     * @return this.enabled
     */
    public final boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Returns the ID of the target neuron.
     *
     * @return this.targetNeuronID
     */
    public final int getTargetID() {
        return this.targetNeuronID;
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
    public final void removeWeight(int w) {
        this.setWeight(this.getWeight() - w);
    }

    /*
     * Main method -------------------------------------------------------------
     */

    /**
     * Demonstrates the Synapse component proof-of-concept.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // Create Synapse object. Point this synapse to neuron 5 (pretend it
        // exists) with a neutral weight of 128

        Synapse s = new Synapse(5, 128); // mAgIcNuMbEr shut up

        // I'm going to use System.out here since I don't have OSU components
        // with this project
        System.out.println("Target neuron ID: " + s.getTargetID());
        System.out.println("Initial weight: " + s.getWeight());
        System.out.println("Enabled: " + s.isEnabled());

        // Testing applyWeight (signal should pass through unchanged)
        System.out.println("Sending signal of 100... " + s.applyWeight(100));
        // Try adding weight
        s.addWeight(64);
        System.out.println("Adding 64 to the weight... " + s.getWeight());

        // Try subtracting >256 from the weight (testing clamp)
        s.removeWeight(999);
        System.out.println("Subtracting >256 weight... " + s.getWeight());

        // Now see if signal is zero
        System.out.println("Sending signal of 100... " + s.applyWeight(100));

        // Set weight to 255 (should almost double signal)
        s.setWeight(255);
        // Check if signals double
        System.out.println("Double signal of 100... " + s.applyWeight(100));

        // Disable the synapse
        s.setEnabled(false);
        System.out.println(
                "Signal of 100 when disabled... " + s.applyWeight(100));

        // Reenable and send signal
        s.setEnabled(true);
        System.out.println(
                "Signal of 100 when reenabled... " + s.applyWeight(100));
    }
}
