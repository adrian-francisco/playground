/*
 * Name: Promo.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
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
 * Represents a Room Promotion (or Special Offer).
 *
 * @author Adrian Francisco
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "date"}))
public class Promo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5725096382195263854L;

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(Promo.class);

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

    /** The discount price. */
    @Column
    private double discountPrice;

    /**
     * Creates a new instance of Promo.
     */
    public Promo() {
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
     * Gets the discountPrice.
     *
     * @return the discountPrice
     */
    public double getDiscountPrice() {
        return discountPrice;
    }

    /**
     * Sets the discountPrice.
     *
     * @param discountPrice the discountPrice to set
     */
    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
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
     * Prints the collection of promos (including headers).
     *
     * @param promos the promos
     */
    public static void print(Collection<Promo> promos) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        String[][] table = new String[promos.size() + 1][4];

        table[0][0] = "Room";
        table[0][1] = "Date";
        table[0][2] = "Discount";
        table[0][3] = "Regular";

        int i = 1;

        for (Promo promo : promos) {
            table[i][0] = promo.getRoom().getName();
            table[i][1] = df.format(promo.getDate());
            table[i][2] = nf.format(promo.getDiscountPrice());
            table[i][3] = nf.format(promo.getRoom().getPrice());
            i++;
        }

        System.out.println("Special Offers:");
        System.out.println(ModelUtil.print(table));
    }
}
