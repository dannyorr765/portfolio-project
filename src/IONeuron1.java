import javax.management.relation.Role;

// ^ ignore this, checkstyle refuses to let me delete this
/**
 * Concrete implementation of {@code IONeuron}, extending {@code Neuron1} with a
 * role and index to identify it as either an input or output neuron within a
 * {@code Brain}.
 *
 * @convention this.role = INPUT or this.role = OUTPUT
 * @convention this.ioIndex >= 0
 * @correspondence this = (potential, threshold, decay, fired, activity,
 *                 synapses, role, ioIndex)
 *
 * @author Danny Orr
 */
public class IONeuron1 extends Neuron1 implements IONeuron {

    /**
     * The role of this neuron, either INPUT or OUTPUT.
     */
    private Role role;

    /**
     * The index of this neuron within its role group in the {@code Brain}.
     */
    private int ioIndex;

    /**
     * No-argument constructor for IONeuron1. Defaults to INPUT role and index
     * 0.
     */
    public IONeuron1() {
        super();
        this.role = Role.INPUT;
        this.ioIndex = 0;
    }

    /**
     * Constructor for IONeuron1 with a specified role and index.
     *
     * @param r
     *            the role of this neuron
     * @param i
     *            the IO index of this neuron
     */
    public IONeuron1(Role r, int i) {
        super();
        this.role = r;
        this.ioIndex = i;
    }

    @Override
    public final Role getRole() {
        return this.role;
    }

    @Override
    public final void setRole(Role r) {
        this.role = r;
    }

    @Override
    public final int getIOIndex() {
        return this.ioIndex;
    }

    @Override
    public final void setIOIndex(int i) {
        this.ioIndex = i;
    }

}
