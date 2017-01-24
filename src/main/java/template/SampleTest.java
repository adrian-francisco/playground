/*
 * Name   : SampleTest.java
 * Author : Adrian Francisco
 * Created: 2015-02-25
 */
package template;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.TimeZone;


/**
 * Unit tests for the SampleTest.
 *
 * @author  Adrian Francisco
 */
public class SampleTest {

    /*
     **************************************** STATIC BLOCKS ************************************************************
     */

    /**
     * set the test properties
     */
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.setProperty("component.configuration", "application.properties");
        System.setProperty("log4j.configuration", "log4j.properties");
    }

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** the logging */
    private static final Log LOG = LogFactory.getLog(SampleTest.class);

    /*
     **************************************** FIXTURES *****************************************************************
     */

    /**
     * Set up before class.
     *
     * @throws  Exception  on any exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Tear down after class.
     *
     * @throws  Exception  on any exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Set up before test.
     *
     * @throws  Exception  on any exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Tear down after test.
     *
     * @throws  Exception  on any exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /*
     **************************************** TEST METHODS *************************************************************
     */

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  on any exception
     */
    @Test
    public final void test() throws Exception {
        LOG.info("test");

        Thread.sleep(6000);

        assertTrue(true);
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
