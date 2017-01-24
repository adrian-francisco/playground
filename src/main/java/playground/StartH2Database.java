/*
 * Name   : StartH2Database.java
 * Author : AdrianF
 * Created: 2014-04-10
 */
package playground;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * DOCUMENT ME!
 *
 * @author  AdrianF
 */
public class StartH2Database {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** logging */
    private static final Log LOG = LogFactory.getLog(StartH2Database.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of StartH2Database.
     */
    public StartH2Database() {
    }

    /**
     * @param   args
     *
     * @throws  Exception  on any exception
     */
    public static void main(String[] args) throws Exception {
        Server tcpServer = null;
        Server webServer = null;

        try {
            System.out.println("starting database");

            tcpServer = Server.createTcpServer("-trace").start();
            webServer = Server.createWebServer("-trace").start();

            Connection conn = DriverManager.getConnection("jdbc:h2:mem:dms", "dms", "dms");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE test_table( test_val VARCHAR(10) )");
            stmt.executeUpdate("CREATE TABLE test_dms_table( test_dms_val VARCHAR(10) )");

            while (true) {
                Thread.sleep(300000);
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            System.out.println("stopping database");

            tcpServer.stop();
            webServer.stop();
        }
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
