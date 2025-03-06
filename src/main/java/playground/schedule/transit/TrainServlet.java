/*
 * Name: TrainServlet.java
 * Author: Adrian Francisco
 * Created: Oct 11, 2017
 */
package playground.schedule.transit;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

/**
 * The Train Servlet.
 *
 * @author Adrian Francisco
 */
@WebServlet("/train/*")
public class TrainServlet extends HttpServlet {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -1606744957123164993L;

    /**
     * The test map of trains.
     */
    private Map<Integer, Train> trains = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws ServletException {
        super.init();

        trains.put(0, new Train(0, "Adrian"));
        trains.put(1, new Train(1, "Nico"));
        trains.put(2, new Train(2, "Char"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = cleanURI(req.getRequestURI());
        resp.setContentType(MediaType.APPLICATION_JSON);

        if (uri == null || uri.isEmpty()) {
            resp.sendError(HttpURLConnection.HTTP_NOT_FOUND);
            return;
        }

        try (PrintWriter writer = resp.getWriter()) {
            if ("/train".equals(uri)) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                for (Train train : getTrains()) {
                    builder.add(train.toJson());
                }

                JsonObjectBuilder trains = Json.createObjectBuilder();
                trains.add("trains", builder.build());

                writer.write(trains.build().toString());
            }
            else {
                int id = Integer.parseInt(uri.split("/")[2]);
                Optional<Train> train = getTrains().stream().filter(t -> t.getId() == id).findFirst();
                writer.write(train.orElseThrow(() -> new NoSuchElementException()).toJson().toString());
            }
        }
        catch (NoSuchElementException | NumberFormatException e) {
            resp.sendError(HttpURLConnection.HTTP_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        InputStream content = req.getInputStream();

        Train train = new Train(content);
        trains.put(trains.size(), train);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = cleanURI(req.getRequestURI());

        int id = Integer.parseInt(uri.split("/")[2]);
        req.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        InputStream content = req.getInputStream();

        Train train = new Train(content);
        trains.put(id, train);
    }

    /**
     * Perform some minor clean-up of the URI.
     *
     * @param uri a dirty uri
     * @return a clean uri
     */
    public static String cleanURI(String uri) {
        uri = uri.toLowerCase().trim();

        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }

        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }

        return uri;
    }

    /**
     * Creates the trains.
     * // TODO: make this a dao call instead.
     *
     * @return the collection
     */
    private Collection<Train> getTrains() {
        return trains.values();
    }
}
