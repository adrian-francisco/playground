/*
 * Name   : TomcatConnection.java
 * Author : AdrianF
 * Created: 2014-04-02
 */
package playground;

import org.apache.commons.io.IOUtils;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * DOCUMENT ME!
 *
 * @author  AdrianF
 */
public class TomcatConnection {

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of TomcatConnection.
     */
    public TomcatConnection() {
    }

    /**
     * @param   args
     *
     * @throws  Exception  on any exception
     */
    public static void main(String[] args) throws Exception {
        int times = 10000;

        ExecutorService executors = Executors.newFixedThreadPool(times);

        for (int i = 0; i < times; i++) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String filename = generateRandomWords(1);
            URL url = new URL("http://dms-hot-deploy.to.on.ec.gc.ca:8180/data/bulletin/" + filename + "/" +
                    df.format(new Date()));

            executors.execute(new ConnectionThread(url));
        }

        executors.shutdown();

        while (!executors.isTerminated()) {
            System.out.println("main: awaiting termination");
            Thread.sleep(5000);
        }

        System.out.println("main: terminated");
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */

    /**
     * Generate random words
     *
     * @param   numberOfWords  the number of words
     *
     * @return  random words
     */
    public static String generateRandomWords(int numberOfWords) {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();

        for (int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(8) + 3];

            for (int j = 0; j < word.length; j++) {
                word[j] = (char)('a' + random.nextInt(26));
            }

            randomStrings[i] = new String(word);
        }

        StringBuffer sb = new StringBuffer();

        for (String letter : randomStrings) {
            sb.append(letter);
        }

        return sb.toString();
    }

    /**
     * DOCUMENT ME!
     */
    private static class ConnectionThread implements Runnable {

        /** DOCUMENT ME! */
        private URL url;

        /**
         * Creates a new ConnectionThread object.
         *
         * @param  url  DOCUMENT ME!
         */
        public ConnectionThread(URL url) {
            this.url = url;
        }

        /**
         * DOCUMENT ME!
         */
        public void run() {
            long id = Thread.currentThread().getId();

            System.out.println(id + ": running");

            HttpURLConnection connection = null;
            OutputStream stream = null;

            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setConnectTimeout(60000);
                connection.setReadTimeout(60000);
                connection.setDoOutput(true);

                stream = connection.getOutputStream();

                stream.write(createBytes(75000));

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println(id + ": " + connection.getResponseCode());
                }
                else {
                    System.err.println(id + ": " + connection.getResponseCode() + ": " + connection.getURL());
                }
            }
            catch (Exception e) {
                System.err.println(id + ": exception caught: " + e.getMessage());
                //e.printStackTrace();
            }
            finally {
                IOUtils.closeQuietly(stream);
                IOUtils.close(connection);
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   size  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private byte[] createBytes(int size) {
            StringBuffer sb = new StringBuffer();
            int count = 0;

            do {
                sb.append(count++).append(": hello world!\n");
            }
            while (sb.toString().getBytes().length < size);

            return sb.toString().getBytes();
        }
    }
}
