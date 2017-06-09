/*
 * Name : CheckAccuRev.java
 * Author : Adrian Francisco
 * Created: 2013-08-30
 */
package playground;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
    public static final String[] DEVELOPERS = {};

    /**
     * Main.
     *
     * @param args no arguments
     * @throws Exception on any exception
     */
    public static void main(String[] args) throws Exception {

        // uncomment the checks you wish to perform
        // checkActiveSessions();
        // checkWIP();
        // checkStranded();
        // checkSnapshot();

        System.out.println("\ndone");
    }

    /**
     * Check active sessions.
     *
     * @throws Exception the exception
     */
    private static void checkActiveSessions() throws Exception {
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
    }

    /**
     * Check WIP.
     *
     * @throws Exception the exception
     */
    private static void checkWIP() throws Exception {
        boolean found = false;
        boolean first = true;

        for (String line : CommandExecutor.execute(ACCUREV + " show -d -s MSC-DMS -ms streams")) {

            if (!line.startsWith("MSC")) {
                continue;
            }

            String[] split = line.split("\\s+");
            String stream = split[0];

            if (!stream.endsWith("_Development")) {
                continue;
            }

            first = true;

            for (Iterator<String> i = CommandExecutor.execute(ACCUREV + " wip -s " + stream).iterator(); i.hasNext();) {
                String file = i.next().trim();
                String user = i.next().trim();

                if (ArrayUtils.isEmpty(DEVELOPERS)) {

                    if (first) {
                        System.out.println("\n" + stream);
                        first = false;
                        found = true;
                    }

                    System.out.println(file + " -> " + user);
                }
                else {

                    for (String developer : DEVELOPERS) {

                        if (user.toLowerCase().contains(developer.toLowerCase())) {

                            if (first) {
                                System.out.println("\n" + stream);
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
    }

    /**
     * Check stranded.
     *
     * @throws Exception the exception
     */
    private static void checkStranded() throws Exception {
        boolean found = false;

        for (String line : CommandExecutor.execute(ACCUREV + " show -d -s MSC-DMS -ms streams")) {
            String[] split = line.split("\\s+");
            String stream = split[0];

            if (!stream.endsWith("_Development")) {
                continue;
            }

            boolean first = true;

            for (String stranded : CommandExecutor.execute(ACCUREV + " stat -s " + stream + " -i")) {
                found = true;

                if (first) {
                    System.out.println(stream);
                    first = false;
                }

                System.out.println("\t" + stranded.split("\\s+")[0]);
            }
        }

        if (!found) {
            System.out.println("\nno stranded files found");
        }
    }

    /**
     * Check snapshot.
     *
     * @throws Exception the exception
     */
    private static void checkSnapshot() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        int maxDepotLength = 0;
        int maxNameLength = 0;

        // depot (sorted) -> date (sorted) -> [name, date, time]
        Map<String, Map<String, String[]>> snapshots = new TreeMap<>();

        for (String line : CommandExecutor.execute(ACCUREV + " show -s MSC-DMS -ms streams")) {

            String[] split = line.split("\\s+");
            String time = split[split.length - 1];

            try {
                format.parse(time);
            }
            catch (ParseException e) {
                continue;
            }

            String name = split[0];
            String depot = split[2];

            // manual fixes for some poorly names snapshots

            if ("MSC-DMS-Core".equals(depot) && !name.startsWith("MSC-DMS-Core")) {
                name = name.replace("MSC-DMS", "MSC-DMS-Core");
            }

            if ("MSC-DMS".equals(name)) {
                name = "MSC-DMS-Core-2.0.0-Release";
                depot = "MSC-DMS-Core";
            }

            String date = split[split.length - 2];

            if (depot.length() > maxDepotLength) {
                maxDepotLength = depot.length();
            }

            if (name.length() > maxNameLength) {
                maxNameLength = name.length();
            }

            if (!snapshots.containsKey(depot)) {
                snapshots.put(depot, new TreeMap<>());
            }

            snapshots.get(depot).put(date, new String[] {name, date, time});
        }

        String output = "%-" + maxDepotLength + "s %-" + maxNameLength + "s %s %s Canada/Eastern%n";

        System.out.println("Generated on: " + new Date());
        System.out.println();
        for (Map.Entry<String, Map<String, String[]>> depotEntry : snapshots.entrySet()) {
            for (Map.Entry<String, String[]> snapshotEntry : depotEntry.getValue().entrySet()) {
                System.out.printf(output, depotEntry.getKey(), snapshotEntry.getValue()[0], snapshotEntry.getValue()[1],
                        snapshotEntry.getValue()[2]);
            }
        }
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
                stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

                String line = null;
                Collection<String> output = new ArrayList<>();

                while ((line = stdout.readLine()) != null) {
                    output.add(line);
                }

                int processResult = process.waitFor();

                if (processResult != 0) {
                    stderr = new BufferedReader(
                            new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));

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
