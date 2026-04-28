import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This is essentially an impromptu brain component. Not fully flushed out, but
 * works well enough to show the use cases for my Neuron and Synapse components.
 *
 * @author Danny Orr
 */
public final class NeuronCluster {

    /**
     * The neurons contained in this cluster.
     */
    private Neuron[] neurons;

    /**
     * The number of neurons in this cluster.
     */
    private int size;

    /**
     * The firing threshold applied to all neurons in this cluster.
     */
    private static final int CLUSTER_THRESHOLD = 20;

    /**
     * The decay rate applied to all neurons in this cluster each step.
     */
    private static final int CLUSTER_DECAY = 10;

    /**
     * The synapse weight used when connecting neurons in this cluster.
     */
    private static final int CONNECTION_WEIGHT = 150;

    /**
     * A signal strength used to stimulate the first neuron in this cluster.
     */
    private static final int STIMULUS = 30;

    /**
     * The number of neurons in the demo cluster.
     */
    private static final int CLUSTER_SIZE = 3;

    /**
     * The number of update steps to run in the demo.
     */
    private static final int UPDATE_STEPS = 3;

    /**
     * Constructs a NeuronCluster with {@code n} neurons, each connected to the
     * next in a chain via a single synapse.
     *
     * @param n
     *            the number of neurons in the cluster
     */
    public NeuronCluster(int n) {
        this.size = n;
        this.neurons = new Neuron[n];
        for (int i = 0; i < n; i++) {
            this.neurons[i] = new Neuron1();
            this.neurons[i].setThreshold(CLUSTER_THRESHOLD);
            this.neurons[i].setDecay(CLUSTER_DECAY);
            if (i > 0) {
                Synapse s = new Synapse1(i);
                s.setWeight(CONNECTION_WEIGHT);
                this.neurons[i - 1].addSynapse(s);
            }
        }
    }

    /**
     * Applies a stimulus to the first neuron in the cluster.
     *
     * @param signal
     *            the signal strength to apply
     */
    public void stimulate(int signal) {
        this.neurons[0].setPotential(this.neurons[0].getPotential() + signal);
    }

    /**
     * Applies one update step to the cluster. Each neuron that exceeds its
     * threshold fires and passes a signal forward through its synapse, then
     * decays.
     *
     * @param out
     *            output stream for logging
     */
    public void update(SimpleWriter out) {
        for (int i = 0; i < this.size; i++) {
            Neuron current = this.neurons[i];
            if (current.getPotential() >= current.getThreshold()) {
                current.fire();
                out.println("Neuron " + i + " fired! State: " + current);
                if (i + 1 < this.size) {
                    Synapse s = current.removeAny();
                    int outgoing = s.applyWeight(current.getPotential());
                    this.neurons[i + 1].setPotential(
                            this.neurons[i + 1].getPotential() + outgoing);
                    current.addSynapse(s);
                }
            }
            current.applyDecay(current.getDecay());
        }
    }

    /**
     * Prints the current state of all neurons in the cluster.
     *
     * @param out
     *            output stream for logging
     */
    public void printState(SimpleWriter out) {
        for (int i = 0; i < this.size; i++) {
            out.println("Neuron " + i + ": " + this.neurons[i]);
        }
    }

    /**
     * Main method demonstrating NeuronCluster usage.
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        NeuronCluster cluster = new NeuronCluster(CLUSTER_SIZE);

        out.println("Initial cluster state:");
        cluster.printState(out);

        cluster.stimulate(STIMULUS);
        out.println("\nApplied stimulus of " + STIMULUS + " to first neuron.");

        for (int step = 0; step < UPDATE_STEPS; step++) {
            out.println("\n--- Update step " + (step + 1) + " ---");
            cluster.update(out);
            cluster.printState(out);
        }

        out.close();
    }
}
