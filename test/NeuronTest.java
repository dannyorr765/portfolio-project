/**
 * Test cases for NeuronSecondary methods.
 *
 * @author Danny Orr
 */
public class NeuronTest {

    /**
     * Maximum activity level.
     */
    private static final int MAX_ACTIVITY = 7;

    /**
     * Maximum int8 value.
     */
    private static final int MAX_INT8 = 255;

    /**
     * A mid-range potential value for testing.
     */
    private static final int MID_POTENTIAL = 100;

    /**
     * A mid-range decay value for testing.
     */
    private static final int MID_DECAY = 50;

    /**
     * A mid-range activity value for testing.
     */
    private static final int MID_ACTIVITY = 4;

    /**
     * A small decay value for testing.
     */
    private static final int SMALL_DECAY = 30;

    /*
     * fire() tests -----------------------------------------------------------
     */

    /**
     * Tests that fire sets fired to true.
     */
    @Test
    public void testFireSetsFired() {
        Neuron n = new Neuron1();
        n.fire();
        assertEquals(true, n.getFired());
    }

    /**
     * Tests that fire increments activity by 1.
     */
    @Test
    public void testFireIncrementsActivity() {
        Neuron n = new Neuron1();
        n.fire();
        assertEquals(1, n.getActivity());
    }

    /**
     * Tests that fire clamps activity at max.
     */
    @Test
    public void testFireClampsActivity() {
        Neuron n = new Neuron1();
        n.setActivity(MAX_ACTIVITY);
        n.fire();
        assertEquals(MAX_ACTIVITY, n.getActivity());
    }

    /**
     * Tests that fire does not affect potential.
     */
    @Test
    public void testFireDoesNotAffectPotential() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.fire();
        assertEquals(MID_POTENTIAL, n.getPotential());
    }

    /*
     * applyDecay() tests -----------------------------------------------------
     */

    /**
     * Tests that applyDecay reduces potential correctly.
     */
    @Test
    public void testApplyDecayNormal() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.applyDecay(MID_DECAY);
        assertEquals(MID_POTENTIAL - MID_DECAY, n.getPotential());
    }

    /**
     * Tests that applyDecay clamps potential to 0.
     */
    @Test
    public void testApplyDecayClampsToZero() {
        Neuron n = new Neuron1();
        n.setPotential(MID_DECAY);
        n.applyDecay(MID_POTENTIAL);
        assertEquals(0, n.getPotential());
    }

    /**
     * Tests that applyDecay with 0 does not change potential.
     */
    @Test
    public void testApplyDecayZero() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.applyDecay(0);
        assertEquals(MID_POTENTIAL, n.getPotential());
    }

    /**
     * Tests that applyDecay does not affect fired.
     */
    @Test
    public void testApplyDecayDoesNotAffectFired() {
        Neuron n = new Neuron1();
        n.setFired(true);
        n.applyDecay(MID_DECAY);
        assertEquals(true, n.getFired());
    }

    /*
     * reset() tests ----------------------------------------------------------
     */

    /**
     * Tests that reset sets fired to false.
     */
    @Test
    public void testResetSetsFiredFalse() {
        Neuron n = new Neuron1();
        n.setFired(true);
        n.reset();
        assertEquals(false, n.getFired());
    }

    /**
     * Tests that reset sets potential to 0.
     */
    @Test
    public void testResetSetsPotentialZero() {
        Neuron n = new Neuron1();
        n.setPotential(MID_POTENTIAL);
        n.reset();
        assertEquals(0, n.getPotential());
    }

    /**
     * Tests that reset does not affect activity.
     */
    @Test
    public void testResetDoesNotAffectActivity() {
        Neuron n = new Neuron1();
        n.setActivity(MID_ACTIVITY);
        n.reset();
        assertEquals(MID_ACTIVITY, n.getActivity());
    }

    /**
     * Tests that reset does not affect synapse count.
     */
    @Test
    public void testResetDoesNotAffectSynapses() {
        Neuron n = new Neuron1();
        n.addSynapse(new Synapse1(1));
        n.reset();
        assertEquals(1, n.synapseCount());
    }

    /*
     * bumpActivity() tests ---------------------------------------------------
     */

    /**
     * Tests that bumpActivity increases activity correctly.
     */
    @Test
    public void testBumpActivityNormal() {
        Neuron n = new Neuron1();
        n.setActivity(MID_ACTIVITY);
        n.bumpActivity(1);
        assertEquals(MID_ACTIVITY + 1, n.getActivity());
    }

    /**
     * Tests that bumpActivity clamps at max.
     */
    @Test
    public void testBumpActivityClampsAtMax() {
        Neuron n = new Neuron1();
        n.setActivity(MAX_ACTIVITY);
        n.bumpActivity(MAX_ACTIVITY);
        assertEquals(MAX_ACTIVITY, n.getActivity());
    }

    /**
     * Tests that bumpActivity by 0 does not change activity.
     */
    @Test
    public void testBumpActivityZero() {
        Neuron n = new Neuron1();
        n.setActivity(MID_ACTIVITY);
        n.bumpActivity(0);
        assertEquals(MID_ACTIVITY, n.getActivity());
    }

    /*
     * decayActivity() tests --------------------------------------------------
     */

    /**
     * Tests that decayActivity decreases activity correctly.
     */
    @Test
    public void testDecayActivityNormal() {
        Neuron n = new Neuron1();
        n.setActivity(MID_ACTIVITY);
        n.decayActivity(1);
        assertEquals(MID_ACTIVITY - 1, n.getActivity());
    }

    /**
     * Tests that decayActivity clamps at 0.
     */
    @Test
    public void testDecayActivityClampsAtZero() {
        Neuron n = new Neuron1();
        n.setActivity(1);
        n.decayActivity(MAX_ACTIVITY);
        assertEquals(0, n.getActivity());
    }

    /**
     * Tests that decayActivity by 0 does not change activity.
     */
    @Test
    public void testDecayActivityZero() {
        Neuron n = new Neuron1();
        n.setActivity(MID_ACTIVITY);
        n.decayActivity(0);
        assertEquals(MID_ACTIVITY, n.getActivity());
    }

    /*
     * toString() tests -------------------------------------------------------
     */

    /**
     * Tests that toString returns expected format for default neuron.
     */
    @Test
    public void testToStringDefault() {
        Neuron n = new Neuron1();
        String expected = "Neuron{potential=0, threshold=15, decay=" + MAX_INT8
                + ", fired=false, activity=0, synapses=0}";
        assertEquals(expected, n.toString());
    }

    /*
     * equals() tests ---------------------------------------------------------
     */

    /**
     * Tests that two default neurons are equal.
     */
    @Test
    public void testEqualsDefault() {
        Neuron n1 = new Neuron1();
        Neuron n2 = new Neuron1();
        assertEquals(true, n1.equals(n2));
    }

    /**
     * Tests that two neurons with different potential are not equal.
     */
    @Test
    public void testEqualsDifferentPotential() {
        Neuron n1 = new Neuron1();
        Neuron n2 = new Neuron1();
        n1.setPotential(MID_POTENTIAL);
        assertEquals(false, n1.equals(n2));
    }

    /**
     * Tests that a neuron equals itself.
     */
    @Test
    public void testEqualsSameReference() {
        Neuron n = new Neuron1();
        assertEquals(true, n.equals(n));
    }
}
