/*
 * Name: Room.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a Room.
 *
 * @author Adrian Francisco
 */
@Entity
public class Room implements Serializable {

    /** The serialVersionUID. */
    private static final long serialVersionUID = 1204551168709711282L;

    /** The LOG. */
    private static final Log LOG = LogFactory.getLog(Room.class);

    /** The id. */
    @Id
    @GeneratedValue
    private long id;

    /** The name. */
    @Column(unique = true)
    private String name;

    /** The types. */
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<Type> types;

    /** The regular price of the room (not including discounts). */
    @Column
    private double price;

    /**
     * Creates a new instance of Room.
     */
    public Room() {
        types = new ArrayList<>();
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
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the types.
     *
     * @return the types
     */
    public Collection<Type> getTypes() {
        return types;
    }

    /**
     * Sets the types.
     *
     * @param types the types to set
     */
    public void setTypes(Collection<Type> types) {
        this.types = types;
    }

    /**
     * Adds a type.
     *
     * @param type the type
     */
    public void addType(Type type) {
        types.add(type);
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Prints the given collection of rooms (including headers).
     *
     * @param rooms the rooms
     */
    public static void print(Collection<Room> rooms) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        String[][] table = new String[rooms.size() + 1][3];

        table[0][0] = "Name";
        table[0][1] = "Types";
        table[0][2] = "Price";

        int i = 1;

        for (Room room : rooms) {
            table[i][0] = room.getName();
            table[i][1] = room.getTypes().toString();
            table[i][2] = nf.format(room.getPrice());
            i++;
        }

        System.out.println("Rooms:");
        System.out.println(ModelUtil.print(table));
    }

    /**
     * The Room Type.
     */
    public enum Type {
        SINGLE_PERSON, DOUBLE_PERSON, TRIPLE_PERSON, QUAD_PERSON, TWIN_BED, DOUBLE_BED, QUEEN_BED, KING_BED, STUDIO,
        SUITE, APARTMENT;

        /**
         * Prints all types including ordinals.
         */
        public static void print() {
            System.out.println("Room types:");

            int i = 0;
            for (Type type : Type.values()) {
                System.out.println(i + ". " + type);
                i++;
            }
        }
    }
}
