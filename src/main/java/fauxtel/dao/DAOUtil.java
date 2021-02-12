/*
 * Name: DAOUtil.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel.dao;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.tools.Server;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import fauxtel.model.Promo;
import fauxtel.model.Reservation;
import fauxtel.model.Room;

/**
 * A utility class for the DAOs. Used primarily to start a server and return the session factory.
 *
 * @author Adrian Francisco
 */
public class DAOUtil {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(DAOUtil.class);

    /** The Constant server (tcp or web). */
    private static final Server server = createServer("web");

    /** The Constant sessionFactory. */
    private static final SessionFactory sessionFactory = createSessionFactory();

    /**
     * Creates a new instance of DAOUtil.
     */
    public DAOUtil() {
    }

    /**
     * Gets the server.
     *
     * @return the server
     */
    public static Server getServer() {
        return server;
    }

    /**
     * Gets the sessionfactory.
     *
     * @return the sessionfactory
     */
    public static SessionFactory getSessionfactory() {
        return sessionFactory;
    }

    /**
     * Creates and starts a new server instance. If type is tcp, the server will be on a random unused port, if type is
     * web, the port will be 8082.
     *
     * @param type the server type
     * @return a new Server
     */
    private static Server createServer(String type) {
        Server server = null;

        try {
            if ("tcp".equals(type)) {
                server = Server.createTcpServer().start();
            }
            else if ("web".equals(type)) {
                server = Server.createWebServer().start();
            }
        }
        catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }

        System.out.println("Database server started:");
        System.out.println("\ttype=" + type);
        System.out.println("\turl=" + server.getURL());
        System.out.println("\tjdbc=jdbc:h2:mem:fauxtel");
        System.out.println("\tuser=admin");
        System.out.println("\tpass=admin");

        return server;
    }

    /**
     * Creates a session factory for the persistence dao.
     *
     * @return a session factory
     */
    private static SessionFactory createSessionFactory() {

        // set all annotated classes
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Room.class);
        configuration.addAnnotatedClass(Promo.class);
        configuration.addAnnotatedClass(Reservation.class);

        // change to true if you want to see all the SQL output
        String debugSQL = Boolean.FALSE.toString();

        // set hibernate properties
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:fauxtel;MVCC=false");
        configuration.setProperty("hibernate.connection.username", "admin");
        configuration.setProperty("hibernate.connection.password", "admin");
        configuration.setProperty("hibernate.connection.pool_size", "20");
        configuration.setProperty("hibernate.cache.use_second_level_cache", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.show_sql", debugSQL);
        configuration.setProperty("hibernate.format_sql", debugSQL);
        configuration.setProperty("hibernate.use_sql_comments", debugSQL);
        configuration.setProperty("hibernate.generate_statistics", debugSQL);

        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        ssrb.applySettings(configuration.getProperties());

        StandardServiceRegistry ssr = ssrb.build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(ssr);

        return sessionFactory;
    }
}
