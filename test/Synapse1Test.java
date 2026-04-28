/**
 * Test cases for Synapse1 kernel and Standard methods.
 *
 * @author Danny Orr
 */
public class Synapse1Test {

    /**
     * Maximum int8 value.
     */
    private static final int MAX_INT8 = 255;

    /**
     * Default weight value.
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
     * A target ID for the first test synapse.
     */
    private static final int TARGET_ID_ONE = 1;

    /**
     * A target ID for the second test synapse.
     */
    private static final int TARGET_ID_TWO = 2;

    /*
     * Constructor / default state tests --------------------------------------
     */

    /**
     * Tests that a new Synapse1 has the correct default weight.
     */
    @Test
    public void testDefaultWeight() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        assertEquals(DEFAULT_WEIGHT, s.getWeight());
    }

    /**
     * Tests that a new Synapse1 is enabled by default.
     */
    @Test
    public void testDefaultEnabled() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        assertEquals(true, s.isEnabled());
    }

    /**
     * Tests that a new Synapse1 has the correct target ID.
     */
    @Test
    public void testDefaultTargetID() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        assertEquals(TARGET_ID_ONE, s.getTargetID());
    }

    /**
     * Tests that a new Synapse1 has the correct default eligibility.
     */
    @Test
    public void testDefaultEligibility() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        assertEquals(0, s.getEligibility());
    }

    /*
     * Weight tests -----------------------------------------------------------
     */

    /**
     * Tests setting weight to a normal value.
     */
    @Test
    public void testSetWeightNormal() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(MID_WEIGHT);
        assertEquals(MID_WEIGHT, s.getWeight());
    }

    /**
     * Tests setting weight to the maximum value.
     */
    @Test
    public void testSetWeightMax() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(MAX_INT8);
        assertEquals(MAX_INT8, s.getWeight());
    }

    /**
     * Tests setting weight to zero.
     */
    @Test
    public void testSetWeightZero() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(0);
        assertEquals(0, s.getWeight());
    }

    /**
     * Tests that setWeight clamps values above 255.
     */
    @Test
    public void testSetWeightClampsAboveMax() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(MAX_INT8 + 1);
        assertEquals(MAX_INT8, s.getWeight());
    }

    /**
     * Tests that setWeight clamps negative values to 0.
     */
    @Test
    public void testSetWeightClampsNegative() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(-1);
        assertEquals(0, s.getWeight());
    }

    /*
     * Enabled tests ----------------------------------------------------------
     */

    /**
     * Tests setting enabled to false.
     */
    @Test
    public void testSetEnabledFalse() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setEnabled(false);
        assertEquals(false, s.isEnabled());
    }

    /**
     * Tests setting enabled back to true.
     */
    @Test
    public void testSetEnabledTrue() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setEnabled(false);
        s.setEnabled(true);
        assertEquals(true, s.isEnabled());
    }

    /*
     * Eligibility tests ------------------------------------------------------
     */

    /**
     * Tests setting eligibility to a normal value.
     */
    @Test
    public void testSetEligibilityNormal() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setEligibility(MID_ELIGIBILITY);
        assertEquals(MID_ELIGIBILITY, s.getEligibility());
    }

    /**
     * Tests setting eligibility to zero.
     */
    @Test
    public void testSetEligibilityZero() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setEligibility(MID_ELIGIBILITY);
        s.setEligibility(0);
        assertEquals(0, s.getEligibility());
    }

    /*
     * Standard method tests --------------------------------------------------
     */

    /**
     * Tests that newInstance returns a fresh Synapse with default state.
     */
    @Test
    public void testNewInstance() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(MID_WEIGHT);
        s.setEnabled(false);
        Synapse fresh = s.newInstance();
        assertEquals(DEFAULT_WEIGHT, fresh.getWeight());
        assertEquals(true, fresh.isEnabled());
        assertEquals(0, fresh.getEligibility());
    }

    /**
     * Tests that clear resets the Synapse to default state.
     */
    @Test
    public void testClear() {
        Synapse s = new Synapse1(TARGET_ID_ONE);
        s.setWeight(MID_WEIGHT);
        s.setEnabled(false);
        s.setEligibility(MID_ELIGIBILITY);
        s.clear();
        assertEquals(DEFAULT_WEIGHT, s.getWeight());
        assertEquals(true, s.isEnabled());
        assertEquals(0, s.getEligibility());
    }

    /**
     * Tests that transferFrom moves state and clears the source.
     */
    @Test
    public void testTransferFrom() {
        Synapse source = new Synapse1(TARGET_ID_ONE);
        source.setWeight(MID_WEIGHT);
        source.setEnabled(false);
        source.setEligibility(MID_ELIGIBILITY);

        Synapse dest = new Synapse1(TARGET_ID_TWO);
        dest.transferFrom(source);

        assertEquals(MID_WEIGHT, dest.getWeight());
        assertEquals(false, dest.isEnabled());
        assertEquals(MID_ELIGIBILITY, dest.getEligibility());
        assertEquals(TARGET_ID_ONE, dest.getTargetID());

        // Source should be reset
        assertEquals(DEFAULT_WEIGHT, source.getWeight());
        assertEquals(true, source.isEnabled());
        assertEquals(0, source.getEligibility());
    }
}
