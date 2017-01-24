/*
 * Name : CheckAccuRev.java
 * Author : Adrian Francisco
 * Created: 2013-08-30
 */
package playground;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Checks AccuRev for a bunch of stuff.
 *
 * @author Adrian Francisco
 */
public class CheckAccuRev {

    /** The accurev executable. */
    public static final String ACCUREV = "/opt/accurev/bin/accurev";

    /**
     * The case-insensitive list of developers to search for. If empty, search for all developers. Must match an AccuRev
     * username.
     * <p>
     * Example: DEVELOPERS = { "username1", "username2", "username3" };
     * </p>
     */
    public static final String[] DEVELOPERS = {"JasonL"};

    /**
     * Main.
     *
     * @param args no arguments
     * @throws Exception on any exception
     */
    public static void main(String[] args) throws Exception {

        //
        // check active session
        //

        boolean found = false;
        boolean first = true;

        for (String line : CommandExecutor.execute(ACCUREV + " show sessions")) {

            if (StringUtils.isEmpty(line) || line.startsWith("Username")) {
                continue;
            }

            String[] split = line.split("\\s+");
            String user = split[0];
            String host = split[1];
            String duration = split[2];

            if (duration.contains("timed")) {
                continue;
            }

            if (ArrayUtils.isEmpty(DEVELOPERS)) {

                if (first) {
                    System.out.println("\nActive Sessions");
                    first = false;
                    found = true;
                }

                System.out.println(user + " signed in for " + duration + " min on " + host);
            }
            else {

                for (String developer : DEVELOPERS) {

                    if (user.toLowerCase().contains(developer.toLowerCase())) {

                        if (first) {
                            System.out.println("\nActive Sessions");
                            first = false;
                            found = true;
                        }

                        System.out.println(user + " signed in for " + duration + " min on " + host);
                    }
                }
            }
        }

        if (!found) {

            if (ArrayUtils.isEmpty(DEVELOPERS)) {
                System.out.println("\nno active session found for any user");
            }
            else {
                System.out.println("\nno active session found for user(s): " + Arrays.asList(DEVELOPERS));
            }
        }

        //
        // check active wip for each development stream
        //

        found = false;

        for (String line : CommandExecutor.execute(ACCUREV + " show streams")) {

            if (!line.startsWith("MSC")) {
                continue;
            }

            String[] split = line.split("\\s+");
            String depot = split[0];

            if (!depot.endsWith("_Development")) {
                continue;
            }

            first = true;

            for (Iterator<String> i = CommandExecutor.execute(ACCUREV + " wip -s " + depot).iterator(); i.hasNext();) {
                String file = i.next().trim();
                String user = i.next().trim();

                if (ArrayUtils.isEmpty(DEVELOPERS)) {

                    if (first) {
                        System.out.println("\n" + depot);
                        first = false;
                        found = true;
                    }

                    System.out.println(file + " -> " + user);
                }
                else {

                    for (String developer : DEVELOPERS) {

                        if (user.toLowerCase().contains(developer.toLowerCase())) {

                            if (first) {
                                System.out.println("\n" + depot);
                                first = false;
                                found = true;
                            }

                            System.out.println(file + " -> " + user);
                        }
                    }
                }
            }
        }

        if (!found) {

            if (ArrayUtils.isEmpty(DEVELOPERS)) {
                System.out.println("\nno wip found for any user");
            }
            else {
                System.out.println("\nno wip found for user(s): " + Arrays.asList(DEVELOPERS));
            }
        }

        System.out.println("\ndone");
    }

    /**
     * A wrapper to execute any system commands.
     */
    private static final class CommandExecutor {

        /**
         * Executes the given command and returns the result.
         *
         * @param command the command to execute
         * @return the result; empty otherwise
         * @throws Exception on any exception
         */
        public static Collection<String> execute(String command) throws Exception {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdout = null;
            BufferedReader stderr = null;

            try {
                stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));

                String line = null;
                Collection<String> output = new ArrayList<>();

                while ((line = stdout.readLine()) != null) {
                    output.add(line);
                }

                int processResult = process.waitFor();

                if (processResult != 0) {
                    stderr = new BufferedReader(
                            new InputStreamReader(process.getErrorStream(), Charset.forName("UTF-8")));

                    while ((line = stderr.readLine()) != null) {
                        System.err.println(line);
                    }
                }
                else {
                    return output;
                }
            }
            finally {
                IOUtils.closeQuietly(stdout);
                IOUtils.closeQuietly(stderr);
            }

            return Collections.emptyList();
        }
    }
}
