import javax.management.relation.Role;

/**
 * Enhanced interface for an IO neuron, extending {@code Neuron} with a role and
 * index to identify it as either an input or output neuron within a
 * {@code Brain}.
 *
 * @author Danny Orr
 */
public interface IONeuron extends Neuron {

    /**
     * Enum representing the role of an IO neuron within a {@code Brain}.
     */
    enum Role {
        /**
         * Indicates this neuron receives external input into the {@code Brain}.
         */
        INPUT,

        /**
         * Indicates this neuron emits output from the {@code Brain}.
         */
        OUTPUT
    }

    /**
     * Returns the role of this neuron.
     *
     * @return this.role
     */
    Role getRole();

    /**
     * Sets the role of this neuron to {@code r}.
     *
     * @param r
     *            the new role
     * @updates this.role
     * @ensures this.role = r
     */
    void setRole(Role r);

    /**
     * Returns the IO index of this neuron.
     *
     * @return this.ioIndex
     */
    int getIOIndex();

    /**
     * Sets the IO index of this neuron to {@code i}.
     *
     * @param i
     *            the new IO index
     * @updates this.ioIndex
     * @ensures this.ioIndex = i
     */
    void setIOIndex(int i);

}
