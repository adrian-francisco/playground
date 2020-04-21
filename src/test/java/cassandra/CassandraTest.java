package cassandra;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CassandraTest {

    @Rule
    public CassandraCQLUnit cassandra = new CassandraCQLUnit(new ClassPathCQLDataSet("customer.cql"));

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        try (CqlSession session = cassandra.getSession()) {
            ResultSet rs = session.execute("select * from messages");

            for (Row row : rs.all()) {
                int id = row.getInt("id");
                String body = row.getString("body");
                String from = row.getString("from");
                String subject = row.getString("subject");

                System.out.println(String.format("The Row: %s, %s, %s, %s", id, body, from, subject));
            }
        }
    }
}
