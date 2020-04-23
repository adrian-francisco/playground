package cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CassandraMain {

    public static void main(String... args) {
        try (CqlSession session = CqlSession.builder().build()) {
            session.execute("use customer");

            ResultSet rs = session.execute("select * from messages");

            for (Row row : rs.all()) {
                long id = row.getLong("id");
                String body = row.getString("body");
                String from = row.getString("from");
                String subject = row.getString("subject");

                System.out.println(String.format("The Row: %s, %s, %s, %s", id, from, subject, body));
            }
        }
    }

}