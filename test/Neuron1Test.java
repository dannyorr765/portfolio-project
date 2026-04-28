/**
 * Test cases for Neuron1 kernel and Standard methods.
 *
 * @author Danny Orr
 */
public class Neuron1Test {

    /**
     * Maximum int8 value.
     */
    private static final int MAX_INT8 = 255;

    /**
     * Default threshold value.
     */
    private static final int DEFAULT_THRESHOLD = 15;

    /**
     * A mid-range potential value for testing.
     */
    private static final int MID_POTENTIAL = 100;

    /**
     * A mid-range activity value for testing.
     */
    private static final int MID_ACTIVITY = 5;

    /**
     * A synapse count for multi-synapse tests.
     */
    private static final int SYNAPSE_COUNT = 3;

    /**
     * Target ID for the first test synapse.
     */
    private static final int TARGET_ID_ONE = 1;

    /**
     * Target ID for the second test synapse.
     */
    private static final int TARGET_ID_TWO = 2;

    /**
     * Target ID for the third test synapse.
     */
    private static final int TARGET_ID_THREE = 3;

    /**
     * Maximum activity level.
     */
    private static final int MAX_ACTIVITY = 7;

    /*
     * Constructor / default state tests --------------------------------------
     */

    /**
     * Tests that a new Neuron1 has the correct default potential.
     */
    @Test
    public void testDefaultPotential() {
        Neuron n = new Neuron1();
        assertEquals(0, n.getPotential());
    }

    /**
     * Tests that a new Neuron1 has the correct default threshold.
     */
    @Test
    public void testDefaultThreshold() {
        Neuron n = new Neuron1();
        assertEquals(DEFAULT_THRESHOLD, n.getThreshold());
    }

    /**
     * Tests that a new Neuron1 has the correct default decay.
     */
    @Test
    public void testDefaultDecay() {
        Neuron n = new Neuron1();
        assertEquals(MAX_INT8, n.getDecay());
    }

    /**
     * Tests that a new Neuron1 has the correct default fired state.
     */
    @Test
    public void testDefaultFired() {
        Neuron n = new Neuron1();
        assertEquals(false, n.getFired());
    }

    /**
     * Tests that a new Neuron1 has the correct default activity.
     */
    @Test
    public void testDefaultActivity() {
        Neuron n = new Neuron1();
        assertEquals(0, n.getActivity());
    }

    /**
     * Tests that a new Neuron1 has no synapses.
     */
    @Test
    public void testDefaultSynapseCount() {
        Neuron n = new Neuron1();
        assertEquals(0, n.synapseCount());
    }

    /*
     * Potential tests --------------------------------------------------------
     */

    /**
     * Tests setting potential to a normal value.
     */
    @Test
    public void testSetPotentialNormal() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        assertEquals(MID_POTENTIAL, n.getPotential());
    }

    /**
     * Tests setting potential to the maximum value.
     */
    @Test
    public void testSetPotentialMax() {
        Neuron n = new Neuron1();
        n.setPotential(MAX_INT8);
        assertEquals(MAX_INT8, n.getPotential());
    }

    /**
     * Tests setting potential to zero.
     */
    @Test
    public void testSetPotentialZero() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.setPotential(0);
        assertEquals(0, n.getPotential());
    }

    /*
     * Threshold tests --------------------------------------------------------
     */

    /**
     * Tests setting threshold to a normal value.
     */
    @Test
    public void testSetThresholdNormal() {
        Neuron n = new Neuron1();
        n.setThreshold(MID_POTENTIAL);
        assertEquals(MID_POTENTIAL, n.getThreshold());
    }

    /**
     * Tests setting threshold to the maximum value.
     */
    @Test
    public void testSetThresholdMax() {
        Neuron n = new Neuron1();
        n.setThreshold(MAX_INT8);
        assertEquals(MAX_INT8, n.getThreshold());
    }

    /*
     * Decay tests ------------------------------------------------------------
     */

    /**
     * Tests setting decay to a normal value.
     */
    @Test
    public void testSetDecayNormal() {
        Neuron n = new Neuron1();
        n.setDecay(MID_POTENTIAL);
        assertEquals(MID_POTENTIAL, n.getDecay());
    }

    /**
     * Tests setting decay to zero.
     */
    @Test
    public void testSetDecayZero() {
        Neuron n = new Neuron1();
        n.setDecay(0);
        assertEquals(0, n.getDecay());
    }

    /*
     * Fired tests ------------------------------------------------------------
     */

    /**
     * Tests setting fired to true.
     */
    @Test
    public void testSetFiredTrue() {
        Neuron n = new Neuron1();
        n.setFired(true);
        assertEquals(true, n.getFired());
    }

    /**
     * Tests setting fired back to false.
     */
    @Test
    public void testSetFiredFalse() {
        Neuron n = new Neuron1();
        n.setFired(true);
        n.setFired(false);
        assertEquals(false, n.getFired());
    }

    /*
     * Activity tests ---------------------------------------------------------
     */

    /**
     * Tests setting activity to a normal value.
     */
    @Test
    public void testSetActivityNormal() {
        Neuron n = new Neuron1();
        n.setActivity(MID_ACTIVITY);
        assertEquals(MID_ACTIVITY, n.getActivity());
    }

    /**
     * Tests setting activity to the maximum value.
     */
    @Test
    public void testSetActivityMax() {
        Neuron n = new Neuron1();
        n.setActivity(MAX_ACTIVITY);
        assertEquals(MAX_ACTIVITY, n.getActivity());
    }

    /*
     * Synapse tests ----------------------------------------------------------
     */

    /**
     * Tests adding a single synapse increases count to 1.
     */
    @Test
    public void testAddSynapseOne() {
        Neuron n = new Neuron1();
        Synapse s = new Synapse1(TARGET_ID_ONE);
        n.addSynapse(s);
        assertEquals(1, n.synapseCount());
    }

    /**
     * Tests adding multiple synapses increases count correctly.
     */
    @Test
    public void testAddSynapseMultiple() {
        Neuron n = new Neuron1();
        n.addSynapse(new Synapse1(TARGET_ID_ONE));
        n.addSynapse(new Synapse1(TARGET_ID_TWO));
        n.addSynapse(new Synapse1(TARGET_ID_THREE));
        assertEquals(SYNAPSE_COUNT, n.synapseCount());
    }

    /**
     * Tests removing a synapse decreases count correctly.
     */
    @Test
    public void testRemoveSynapse() {
        Neuron n = new Neuron1();
        Synapse s = new Synapse1(TARGET_ID_ONE);
        n.addSynapse(s);
        n.removeSynapse(s);
        assertEquals(0, n.synapseCount());
    }

    /**
     * Tests removeAny returns a synapse and decreases count.
     */
    @Test
    public void testRemoveAny() {
        Neuron n = new Neuron1();
        Synapse s = new Synapse1(TARGET_ID_ONE);
        n.addSynapse(s);
        Synapse removed = n.removeAny();
        assertEquals(0, n.synapseCount());
        assertEquals(TARGET_ID_ONE, removed.getTargetID());
    }

    /*
     * Standard method tests --------------------------------------------------
     */

    /**
     * Tests that newInstance returns a fresh Neuron with default state.
     */
    @Test
    public void testNewInstance() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.setFired(true);
        Neuron fresh = n.newInstance();
        assertEquals(0, fresh.getPotential());
        assertEquals(false, fresh.getFired());
        assertEquals(0, fresh.synapseCount());
    }

    /**
     * Tests that clear resets the Neuron to default state.
     */
    @Test
    public void testClear() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.setFired(true);
        n.setActivity(MID_ACTIVITY);
        n.clear();
        assertEquals(0, n.getPotential());
        assertEquals(false, n.getFired());
        assertEquals(0, n.getActivity());
        assertEquals(0, n.synapseCount());
    }

    /**
     * Tests that transferFrom moves state and clears the source.
     */
    @Test
    public void testTransferFrom() {
        Neuron source = new Neuron1();
        source.setPotential(MID_POTENTIAL);
        source.setFired(true);
        source.setActivity(MID_ACTIVITY);
        source.addSynapse(new Synapse1(TARGET_ID_ONE));

        Neuron dest = new Neuron1();
        dest.transferFrom(source);

        assertEquals(MID_POTENTIAL, dest.getPotential());
        assertEquals(true, dest.getFired());
        assertEquals(MID_ACTIVITY, dest.getActivity());
        assertEquals(1, dest.synapseCount());

        assertEquals(0, source.getPotential());
        assertEquals(false, source.getFired());
        assertEquals(0, source.synapseCount());
    }
}
