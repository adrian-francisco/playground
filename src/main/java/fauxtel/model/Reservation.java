/*
 * Name: Reservation.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a Reservation.
 *
 * @author Adrian Francisco
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "date"}))
public class Reservation implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6329714789799737702L;

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(Reservation.class);

    /** The id. */
    @Id
    @GeneratedValue
    private long id;

    /** The room. */
    @ManyToOne
    private Room room;

    /** The date. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /** The name. */
    @Column
    private String name;

    /**
     * Creates a new instance of Reservation.
     */
    public Reservation() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        this.date = cal.getTime();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room.
     *
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Prints the collection of reservations (including headers).
     *
     * @param reservations the reservations
     */
    public static void print(Collection<Reservation> reservations) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String[][] table = new String[reservations.size() + 1][3];

        table[0][0] = "Name";
        table[0][1] = "Date";
        table[0][2] = "Room";

        int i = 1;

        for (Reservation reservation : reservations) {
            table[i][0] = reservation.getName();
            table[i][1] = df.format(reservation.getDate());
            table[i][2] = reservation.getRoom().getName();
            i++;
        }

        System.out.println("Reservations:");
        System.out.println(ModelUtil.print(table));
    }
}
