package playground;

import java.util.ArrayList;
import java.util.Collection;

public class OOMPlay {

    /**
     * Make sure to add -XX:+HeapDumpOnOutOfMemoryError to VM args prior to running.
     * Heap dump can be found in the root directory of the project and can be opened by MAT.
     * Ensure to have a lot of memory for MAT to be able to open the heap dump.
     * @param args
     */
    public static void main(String[] args) {
        Collection<String> large = new ArrayList<>();
        while (true) {
            large.add("OOM Soonish...");
        }
    }
}
