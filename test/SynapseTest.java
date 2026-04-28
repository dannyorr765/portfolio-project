/**
 * Test cases for SynapseSecondary methods.
 *
 * @author Danny Orr
 */
public class SynapseTest {

    /**
     * Maximum int8 value.
     */
    private static final int MAX_INT8 = 255;

    /**
     * Default weight value (neutral, passes signal unchanged).
     */
    private static final int DEFAULT_WEIGHT = 128;

    /**
     * A mid-range weight value for testing.
     */
    private static final int MID_WEIGHT = 100;

    /**
     * A mid-range eligibility value for testing.
     */
    private static final int MID_ELIGIBILITY = 50;

    /**
     * A small eligibility value for testing.
     */
    private static final int SMALL_ELIGIBILITY = 10;

    /**
     * A mid-range signal value for testing.
     */
    private static final int MID_SIGNAL = 100;

    /**
     * A target ID for test synapses.
     */
    private static final int TARGET_ID = 1;

    /**
     * Weight shift used in applyWeight (2^7 = 128).
     */
    private static final int WEIGHT_SHIFT = 7;

    /*
     * applyWeight() tests ----------------------------------------------------
     */

    /**
     * Tests that applyWeight with default weight passes signal unchanged.
     */
    @Test
    public void testApplyWeightDefault() {
        Synapse s = new Synapse1(TARGET_ID);
        int result = s.applyWeight(MID_SIGNAL);
        assertEquals((MID_SIGNAL * DEFAULT_WEIGHT) >> WEIGHT_SHIFT, result);
    }

    /**
     * Tests that applyWeight with zero weight returns zero.
     */
    @Test
    public void testApplyWeightZeroWeight() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(0);
        assertEquals(0, s.applyWeight(MID_SIGNAL));
    }

    /**
     * Tests that applyWeight on disabled synapse returns zero.
     */
    @Test
    public void testApplyWeightDisabled() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setEnabled(false);
        assertEquals(0, s.applyWeight(MID_SIGNAL));
    }

    /**
     * Tests that applyWeight clamps result to 255.
     */
    @Test
    public void testApplyWeightClampsToMax() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(MAX_INT8);
        int result = s.applyWeight(MAX_INT8);
        assertEquals(MAX_INT8, result);
    }

    /**
     * Tests that applyWeight with zero signal returns zero.
     */
    @Test
    public void testApplyWeightZeroSignal() {
        Synapse s = new Synapse1(TARGET_ID);
        assertEquals(0, s.applyWeight(0));
    }

    /*
     * addWeight() tests ------------------------------------------------------
     */

    /**
     * Tests that addWeight increases weight correctly.
     */
    @Test
    public void testAddWeightNormal() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(MID_WEIGHT);
        s.addWeight(SMALL_ELIGIBILITY);
        assertEquals(MID_WEIGHT + SMALL_ELIGIBILITY, s.getWeight());
    }

    /**
     * Tests that addWeight clamps at max.
     */
    @Test
    public void testAddWeightClampsAtMax() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(MAX_INT8);
        s.addWeight(SMALL_ELIGIBILITY);
        assertEquals(MAX_INT8, s.getWeight());
    }

    /**
     * Tests that addWeight by zero does not change weight.
     */
    @Test
    public void testAddWeightZero() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(MID_WEIGHT);
        s.addWeight(0);
        assertEquals(MID_WEIGHT, s.getWeight());
    }

    /*
     * removeWeight() tests ---------------------------------------------------
     */

    /**
     * Tests that removeWeight decreases weight correctly.
     */
    @Test
    public void testRemoveWeightNormal() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(MID_WEIGHT);
        s.removeWeight(SMALL_ELIGIBILITY);
        assertEquals(MID_WEIGHT - SMALL_ELIGIBILITY, s.getWeight());
    }

    /**
     * Tests that removeWeight clamps at zero.
     */
    @Test
    public void testRemoveWeightClampsAtZero() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(0);
        s.removeWeight(SMALL_ELIGIBILITY);
        assertEquals(0, s.getWeight());
    }

    /**
     * Tests that removeWeight by zero does not change weight.
     */
    @Test
    public void testRemoveWeightZero() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setWeight(MID_WEIGHT);
        s.removeWeight(0);
        assertEquals(MID_WEIGHT, s.getWeight());
    }

    /*
     * addEligibility() tests -------------------------------------------------
     */

    /**
     * Tests that addEligibility increases eligibility correctly.
     */
    @Test
    public void testAddEligibilityNormal() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setEligibility(MID_ELIGIBILITY);
        s.addEligibility(SMALL_ELIGIBILITY);
        assertEquals(MID_ELIGIBILITY + SMALL_ELIGIBILITY, s.getEligibility());
    }

    /**
     * Tests that addEligibility by zero does not change eligibility.
     */
    @Test
    public void testAddEligibilityZero() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setEligibility(MID_ELIGIBILITY);
        s.addEligibility(0);
        assertEquals(MID_ELIGIBILITY, s.getEligibility());
    }

    /*
     * removeEligibility() tests ----------------------------------------------
     */

    /**
     * Tests that removeEligibility decreases eligibility correctly.
     */
    @Test
    public void testRemoveEligibilityNormal() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setEligibility(MID_ELIGIBILITY);
        s.removeEligibility(SMALL_ELIGIBILITY);
        assertEquals(MID_ELIGIBILITY - SMALL_ELIGIBILITY, s.getEligibility());
    }

    /**
     * Tests that removeEligibility by zero does not change eligibility.
     */
    @Test
    public void testRemoveEligibilityZero() {
        Synapse s = new Synapse1(TARGET_ID);
        s.setEligibility(MID_ELIGIBILITY);
        s.removeEligibility(0);
        assertEquals(MID_ELIGIBILITY, s.getEligibility());
    }

    /*
     * toString() tests -------------------------------------------------------
     */

    /**
     * Tests that toString returns expected format for default synapse.
     */
    @Test
    public void testToStringDefault() {
        Synapse s = new Synapse1(TARGET_ID);
        String expected = "Synapse{target=" + TARGET_ID + ", weight="
                + DEFAULT_WEIGHT + ", enabled=true, eligibility=0}";
        assertEquals(expected, s.toString());
    }

    /*
     * equals() tests ---------------------------------------------------------
     */

    /**
     * Tests that two synapses with the same state are equal.
     */
    @Test
    public void testEqualsDefault() {
        Synapse s1 = new Synapse1(TARGET_ID);
        Synapse s2 = new Synapse1(TARGET_ID);
        assertEquals(true, s1.equals(s2));
    }

    /**
     * Tests that two synapses with different weights are not equal.
     */
    @Test
    public void testEqualsDifferentWeight() {
        Synapse s1 = new Synapse1(TARGET_ID);
        Synapse s2 = new Synapse1(TARGET_ID);
        s1.setWeight(MID_WEIGHT);
        assertEquals(false, s1.equals(s2));
    }

    /**
     * Tests that a synapse equals itself.
     */
    @Test
    public void testEqualsSameReference() {
        Synapse s = new Synapse1(TARGET_ID);
        assertEquals(true, s.equals(s));
    }
}
