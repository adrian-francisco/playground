package merger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import merger.RecordMerger;

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

        args.add("data/StationCfg_CA.csv");
        args.add("data/StationCfg_RA.csv");
        args.add("data/JICC.csv");
        args.add("data/SAP.csv");
        args.add("data/SIS.csv");

        RecordMerger.main(args.toArray(new String[0]));
    }
}
