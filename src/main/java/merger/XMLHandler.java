package merger;
import java.io.File;

import com.google.common.collect.Table;

/**
 * The XMLHandler.
 * <p>
 * TODO: to be completed.
 * </p>
 *
 * @author Adrian Francisco
 */
public class XMLHandler implements Handler {

    /** The input file. */
    private File file;

    /**
     * Creates a new instance of XMLHandler.
     */
    public XMLHandler(File file) {
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table<String, String, String> handle() {
        // TODO Auto-generated method stub
        return null;
    }
}
