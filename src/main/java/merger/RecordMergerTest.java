package merger;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests the RecordMerger.
 */
public class RecordMergerTest {

    /**
     * Test main.
     *
     * @throws Exception on any exception
     */
    @Test
    public final void testMain() throws Exception {
        List<String> args = new ArrayList<>();

        args.add("src/test/resources/merger/first.html");
        args.add("src/test/resources/merger/second.csv");

        RecordMerger.main(args.toArray(new String[0]));
    }
}
