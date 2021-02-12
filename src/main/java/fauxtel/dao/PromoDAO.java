/*
 * Name: PromoDAO.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fauxtel.model.Promo;
import fauxtel.model.Room;

/**
 * The Promo DAO.
 *
 * @author Adrian Francisco
 */
public class PromoDAO {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(PromoDAO.class);

    /** The session factory. */
    private SessionFactory sessionFactory = DAOUtil.getSessionfactory();

    /**
     * Creates a promo.
     *
     * @param promo the promo
     */
    public void create(Promo promo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(promo);
            tx.commit();
        }
        catch (PersistenceException e) {
            LOG.error("failed to create promo", e);
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Creates promos for the given rooms between the range of dates (inclusive) for the price provided.
     *
     * @param rooms the rooms
     * @param from the from date
     * @param to the to date
     * @param price the price
     */
    public void create(Collection<Room> rooms, Date from, Date to, double price) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);

        while (cal.getTime().before(to) || cal.getTime().equals(to)) {
            rooms.stream().forEach(r -> {
                Promo promo = new Promo();
                promo.setRoom(r);
                promo.setDate(cal.getTime());
                promo.setDiscountPrice(price);

                create(promo);
            });
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * Read all Promos.
     *
     * @return the collection of Promos
     */
    public Collection<Promo> read() {
        try (Session session = sessionFactory.openSession()) {
            Query<Promo> query = session.createQuery("FROM Promo ORDER BY room, date", Promo.class);
            return query.list();
        }
    }

    /**
     * Read a Promo.
     *
     * @param id the id
     * @return the Promo
     */
    public Promo read(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Promo.class, id);
        }
    }

    /**
     * Update a Promo.
     *
     * @param promo the Promo
     */
    public void update(Promo promo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(promo);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Delete a Promo.
     *
     * @param promo the Promo
     */
    public void delete(Promo promo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(promo);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
