/*
 * Name: Train.java
 * Author: Adrian Francisco
 * Created: Oct 11, 2017
 */
package playground.schedule.transit;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A Train object.
 *
 * @author Adrian Francisco
 */
@Entity
public class Train {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /** The name. */
    @Column
    private String name;

    /**
     * Instantiates a new train.
     *
     * @param id the id
     * @param name the name
     */
    public Train(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new train.
     *
     * @param content the content
     */
    public Train(InputStream content) {
        try (JsonReader reader = Json.createReader(content)) {
            JsonObject json = reader.readObject();
            id = json.getInt("id");
            name = json.getString("name");
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * To json.
     *
     * @return the json object
     */
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", id);
        builder.add("name", name);
        return builder.build();
    }
}
