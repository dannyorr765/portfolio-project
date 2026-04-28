import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Demonstrates basic signal propagation through a simple neuron-synapse-neuron
 * chain. A signal is applied to a source neuron, passed through a synapse, and
 * received by a target neuron.
 *
 * @author Danny Orr
 */
public final class NeuronDemo {

    /**
     * The ID of the target neuron. I should mention that, as I stated in
     * 05-component-kernel-implementation.md, this ID method will most likely
     * not be utilized in the C++ retranslation, instead opting for every
     * synapse simply holding it's target neuron's object within it instead.
     */
    private static final int TARGET_ID = 1;

    /**
     * The initial signal strength applied to the source neuron.
     */
    private static final int INITIAL_SIGNAL = 50;

    /**
     * The firing threshold for the source neuron.
     */
    private static final int FIRING_THRESHOLD = 30;

    /**
     * The synapse weight for the connection. This is, by default, set to be
     * much higher than it would be in the wild. This is to ensure that the
     * target neuron will fire.
     */
    private static final int SYNAPSE_WEIGHT = 200;

    /**
     * Private constructor to prevent instantiation.
     */
    private NeuronDemo() {
    }

    /**
     * Main method.
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        // Create source and target neurons
        Neuron source = new Neuron1();
        Neuron target = new Neuron1();

        // Create a synapse pointing from source to target
        Synapse connection = new Synapse1(TARGET_ID);
        connection.setWeight(SYNAPSE_WEIGHT);
        source.addSynapse(connection);

        // Set a low threshold so the source fires easily
        source.setThreshold(FIRING_THRESHOLD);

        out.println("Initial state:");
        out.println("Source: " + source);
        out.println("Target: " + target);

        // Apply a signal to the source neuron
        source.setPotential(INITIAL_SIGNAL);
        out.println(
                "\nApplied signal of " + INITIAL_SIGNAL + " to source neuron.");

        // Check if source should fire
        if (source.getPotential() >= source.getThreshold()) {
            source.fire();
            out.println("Source neuron fired!");

            // Pass signal through each synapse to target
            Neuron1 tempSource = new Neuron1();
            tempSource.transferFrom(source);
            while (tempSource.synapseCount() > 0) {
                Synapse s = tempSource.removeAny();
                int outgoingSignal = s.applyWeight(source.getPotential());
                out.println("Signal after synapse weight applied: "
                        + outgoingSignal);
                target.setPotential(target.getPotential() + outgoingSignal);
                source.addSynapse(s);
            }
            source.transferFrom(tempSource);
        }

        // Apply decay to source after firing
        source.applyDecay(source.getDecay());

        out.println("\nFinal state:");
        out.println("Source: " + source);
        out.println("Target: " + target);

        out.close();
    }
}
