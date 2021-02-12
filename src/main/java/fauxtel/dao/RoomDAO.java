/*
 * Name: RoomDAO.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
 * The Room DAO.
 *
 * @author Adrian Francisco
 */
public class RoomDAO {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(RoomDAO.class);

    /** The session factory. */
    private SessionFactory sessionFactory = DAOUtil.getSessionfactory();

    /**
     * Creates a room.
     *
     * @param room the room
     */
    public void create(Room room) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(room);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Read all rooms.
     *
     * @return the collection of rooms
     */
    public Collection<Room> read() {
        try (Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery("FROM Room ORDER BY name", Room.class);
            return query.list();
        }
    }

    /**
     * Read a room.
     *
     * @param id the id
     * @return the room
     */
    public Room read(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Room.class, id);
        }
    }

    /**
     * Read a room given the name.
     *
     * @param name the name
     * @return the room
     */
    public Room read(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery("FROM Room WHERE name=?0", Room.class);
            query.setParameter(0, name);
            List<Room> result = query.list();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
            return null;
        }
    }

    /**
     * Read all rooms of a given type.
     *
     * @param type the type
     * @return the collection
     */
    public Collection<Room> read(Room.Type type) {
        try (Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery("FROM Room WHERE ?0 IN ELEMENTS(types)", Room.class);
            query.setParameter(0, type);
            return query.list();
        }
    }

    /**
     * Read all rooms of a given type that are available on the given date.
     *
     * @param type the type
     * @param date the date
     * @return the collection
     */
    public Collection<Room> read(Room.Type type, Date date) {
        Collection<Room> availableRooms = new ArrayList<>();
        Collection<Room> rooms = read(type);

        try (Session session = sessionFactory.openSession()) {
            Query<Reservation> query = session
                    .createQuery("FROM Reservation WHERE ?0 IN ELEMENTS(room.types) AND date=?1", Reservation.class);
            query.setParameter(0, type);
            query.setParameter(1, date);

            List<Reservation> reservations = query.list();

            for (Room room : rooms) {
                boolean available = true;

                for (Reservation reservation : reservations) {
                    if (reservation.getRoom().getId() == room.getId()) {
                        available = false;
                    }
                }

                if (available) {
                    availableRooms.add(room);
                }
            }
        }

        return availableRooms;
    }

    /**
     * Update a room.
     *
     * @param room the room
     */
    public void update(Room room) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(room);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    /**
     * Update the collection of rooms with the given price.
     *
     * @param rooms the rooms
     * @param price the price
     */
    public void update(Collection<Room> rooms, double price) {
        rooms.stream().forEach(r -> {
            r.setPrice(price);
            update(r);
        });
    }

    /**
     * Delete a room.
     *
     * @param room the room
     */
    public void delete(Room room) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(room);
            tx.commit();
        }
        catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
