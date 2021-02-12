/*
 * Name: ReservationDAO.java
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

import fauxtel.model.Reservation;
import fauxtel.model.Room;

/**
 * The Reservation DAO.
 *
 * @author Adrian Francisco
 */
public class ReservationDAO {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(ReservationDAO.class);

    /** The session factory. */
    private SessionFactory sessionFactory = DAOUtil.getSessionfactory();

    /**
     * Creates a Reservation.
     *
     * @param reservation the reservation
     */
    public void create(Reservation reservation) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(reservation);
            tx.commit();
        }
        catch (PersistenceException e) {
            LOG.error("failed to create reservation", e);
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Creates a Reservation given the date range (inclusive).
     *
     * @param reservation the reservation
     * @param from from date
     * @param to to date
     */
    public void create(Reservation reservation, Date from, Date to) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);

        while (cal.getTime().before(to) || cal.getTime().equals(to)) {
            reservation.setDate(cal.getTime());
            create(reservation);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * Read all Reservations.
     *
     * @return the collection of Reservations
     */
    public Collection<Reservation> read() {
        try (Session session = sessionFactory.openSession()) {
            Query<Reservation> query = session.createQuery("FROM Reservation ORDER BY name, date", Reservation.class);
            return query.list();
        }
    }

    /**
     * Read a Reservation.
     *
     * @param id the id
     * @return the Reservation
     */
    public Reservation read(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reservation.class, id);
        }
    }

    /**
     * Read all Reservations for a room type at a given date.
     *
     * @param type the room type
     * @param date the date
     * @return the reservation; null if not found
     */
    public Collection<Reservation> read(Room.Type type, Date date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Reservation> query = session
                    .createQuery("FROM Reservation WHERE ?0 IN ELEMENTS(room.types) AND date=?1", Reservation.class);
            query.setParameter(0, type);
            query.setParameter(1, date);
            return query.list();
        }
    }

    /**
     * Update a Reservation.
     *
     * @param reservation the reservation
     */
    public void update(Reservation reservation) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(reservation);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Delete a Reservation.
     *
     * @param reservation the reservation
     */
    public void delete(Reservation reservation) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(reservation);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
