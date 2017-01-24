package merger;
import com.google.common.collect.Table;

/**
 * The Handler interface.
 */
public interface Handler {

    /**
     * Handle the input file returning a Table.
     *
     * @return the table
     */
    Table<String, String, String> handle();
}
