/*
 * Name: TrainServletTest.java
 * Author: Adrian Francisco
 * Created: Oct 11, 2017
 */
package playground.schedule.transit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Tests the TrainServlet.
 *
 * @author Adrian Francisco
 */
public class TrainServletTest {

    /** The name. */
    @Rule
    public TestName name = new TestName();

    /**
     * Before.
     *
     * @throws Exception the exception
     */
    @Before
    public void before() throws Exception {
        System.out.println(name.getMethodName());
    }

    /**
     * Test servlet.
     *
     * @throws Exception the exception
     */
    @Test
    @Ignore
    public final void testServletAll() throws Exception {
        TrainServlet servlet = new TrainServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/train");

        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(printWriter);

        servlet.doGet(req, resp);

        printWriter.flush();

        System.out.println(stringWriter.toString());
    }

    /**
     * Test servlet.
     *
     * @throws Exception the exception
     */
    @Test
    @Ignore
    public final void testServletOne() throws Exception {
        TrainServlet servlet = new TrainServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/train/1");

        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(printWriter);

        servlet.doGet(req, resp);

        printWriter.flush();

        System.out.println(stringWriter.toString());
    }

    /**
     * Test servlet.
     *
     * @throws Exception the exception
     */
    @Test
    @Ignore
    public final void testServletNot() throws Exception {
        TrainServlet servlet = new TrainServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/train/99");

        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(printWriter);

        servlet.doGet(req, resp);

        printWriter.flush();

        System.out.println(stringWriter.toString());
    }
}
