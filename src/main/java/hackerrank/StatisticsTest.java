/*
 * Name : StatisticsTest.java
 * Author : Adrian Francisco
 * Created: Feb 10, 2017
 */
package hackerrank;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for the Statistics class.
 *
 * @author Adrian Francisco
 */
public class StatisticsTest {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** the logging. */
    private static final Log LOG = LogFactory.getLog(StatisticsTest.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of StatisticsTest.
     */
    public StatisticsTest() {
    }

    /*
     **************************************** FIXTURES *****************************************************************
     */

    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Tear down after class.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /*
     **************************************** TEST METHODS *************************************************************
     */

    /**
     * Test round.
     */
    @Test
    public final void testRound() {
        assertEquals(1, Statistics.round(1, 1), 0);
        assertEquals(1.2, Statistics.round(1.234, 1), 0);
        assertEquals(1.33, Statistics.round(1.325, 2), 0);
    }

    /**
     * Test mean.
     */
    @Test
    public final void testMean() {
        assertEquals(43900.6,
                Statistics.mean(new double[] {64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120, 51135, 67060}), 0);
    }

    /**
     * Test median.
     */
    @Test
    public final void testMedian() {
        assertEquals(1, Statistics.median(new double[] {1}), 0);
        assertEquals(2.5, Statistics.median(new double[] {1, 2, 3, 4}), 0);
        assertEquals(2, Statistics.median(new double[] {1, 2, 3}), 0);
    }

    /**
     * Test mode.
     */
    @Test
    public final void testMode() {
        assertEquals(4978,
                Statistics.mode(new double[] {64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120, 51135, 67060}), 0);
    }

    /**
     * Test weighted mean.
     */
    @Test
    public final void testWeightedMean() {
        assertEquals(32, Statistics.weightedMean(new double[] {10, 40, 30, 50, 20}, new double[] {1, 2, 3, 4, 5}), 0);
    }

    /**
     * Test quartiles.
     */
    @Test
    public void testQuartiles() {
        double[] q = null;

        q = Statistics.quartiles(new double[] {3, 7, 8, 5, 12, 14, 21, 13, 18});
        assertEquals(3, q.length);
        assertEquals(6, q[0], 0);
        assertEquals(12, q[1], 0);
        assertEquals(16, q[2], 0);

        q = Statistics.quartiles(new double[] {3, 7, 8, 5, 12, 14, 21, 15, 18, 14});
        assertEquals(3, q.length);
        assertEquals(7, q[0], 0);
        assertEquals(13, q[1], 0);
        assertEquals(15, q[2], 0);
    }

    /**
     * Test interquartile range.
     */
    @Test
    public void testInterquartileRange() {
        assertEquals(9, Statistics.interquartileRange(
                new double[] {6, 6, 6, 6, 6, 8, 8, 8, 10, 10, 12, 12, 12, 12, 16, 16, 16, 16, 16, 20}), 0);
    }

    /**
     * Test standard deviation.
     */
    @Test
    public void testStandardDeviation() {
        assertEquals(14.1, Statistics.standardDeviation(new double[] {10, 40, 30, 50, 20}), 0.1);
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */}
