/*
 * Name: TrainDAO.java
 * Author: Adrian Francisco
 * Created: Oct 11, 2017
 */
package playground.schedule.transit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.PersistenceException;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 * The TrainDAO.
 *
 * @author Adrian Francisco
 */
public class TrainDAO {

    private Server server;

    private SessionFactory sessionFactory;

    public void init() throws Exception {
        server = createServer();

        Collection<Class> annotatedClasses = new ArrayList<>();
        annotatedClasses.add(Train.class);

        sessionFactory = createSessionFactory(annotatedClasses);
    }

    public void destroy() throws Exception {

    }

    public void create(Train train) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(train);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public Collection<Train> read() {
        try (Session session = sessionFactory.openSession()) {
            Query<Train> query = session.createQuery("FROM Train", Train.class);
            return query.list();
        }
    }

    public Train read(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Train.class, id);
        }
    }

    public void update(Train train) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(train);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public void delete(Train train) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(train);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Creates and starts a new tcp server instance on a random unused port.
     *
     * @return a new Server
     * @throws SQLException on sql exception
     */
    public static Server createServer() throws SQLException {
        Server server = Server.createTcpServer().start();
        System.out.println("Server started: " + server.getURL());
        return server;
    }

    /**
     * Creates a session factory for the persistence dao.
     *
     * @param annotatedClasses a collection of annotated classes.
     * @return a session factory
     */
    public static SessionFactory createSessionFactory(Collection<Class> annotatedClasses) {
        Configuration configuration = new Configuration();

        for (Class clazz : annotatedClasses) {
            configuration.addAnnotatedClass(clazz);
        }

        // change to true if you want to see all the SQL output
        String debugSQL = Boolean.FALSE.toString();

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:transit;MVCC=false");
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
