package cap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CAPMetadataGenerator {

    /** DOCUMENT ME! */
    private static final String RESOURCES = "src/main/resources/";

    /** DOCUMENT ME! */
    private static final String INPUT_FILE = RESOURCES + "CAP-CP_Event_Mapping_Table.csv";

    /** DOCUMENT ME! */
    private static final String WARNINGS_DECODER = RESOURCES + "MSC-DMS-Decoder-WarningsMFile/";

    /** DOCUMENT ME! */
    private static final String CAP_PG = RESOURCES + "MSC-DMS-PG-CAP/";

    // this is for Warnings MFile Decoder

    /** DOCUMENT ME! */
    private static final String CERTAINTY_FILE = WARNINGS_DECODER + "certainty.application.properties";

    /** DOCUMENT ME! */
    private static final String EVENT_NAME_EC_CODE_EN_FILE = WARNINGS_DECODER +
        "eventName.to.ecCode.en.application.properties";

    /** DOCUMENT ME! */
    private static final String EVENT_NAME_EC_CODE_FR_FILE = WARNINGS_DECODER +
        "eventName.to.ecCode.fr.application.properties";

    /** DOCUMENT ME! */
    private static final String EVENT_TYPE_FILE = WARNINGS_DECODER + "eventType.application.properties";

    /** DOCUMENT ME! */
    private static final String EXPIRES_DURATION_FILE = WARNINGS_DECODER + "expiresDuration.application.properties";

    /** DOCUMENT ME! */
    private static final String HAZARD_FILE = WARNINGS_DECODER + "hazard.application.properties";

    /** DOCUMENT ME! */
    private static final String MESSAGE_NAME_EN_FILE = WARNINGS_DECODER + "messageName.en.application.properties";

    /** DOCUMENT ME! */
    private static final String MESSAGE_NAME_FR_FILE = WARNINGS_DECODER + "messageName.fr.application.properties";

    /** DOCUMENT ME! */
    private static final String SEVERITY_FILE = WARNINGS_DECODER + "severity.application.properties";

    /** DOCUMENT ME! */
    private static final String URGENCY_FILE = WARNINGS_DECODER + "urgency.application.properties";

    /** DOCUMENT ME! */
    private static final String VALID_DURATION_FILE = WARNINGS_DECODER + "validDuration.application.properties";

    // this is for PG-CAP

    /** DOCUMENT ME! */
    private static final String AUDIENCE_FILE = CAP_PG + "audience.properties";

    /** DOCUMENT ME! */
    private static final String CATEGORY_FILE = CAP_PG + "category.properties";

    /** DOCUMENT ME! */
    private static final String CERTAINTY_ACTIVE_FILE = CAP_PG + "certainty.active.properties";

    /** DOCUMENT ME! */
    private static final String CERTAINTY_ENDED_FILE = CAP_PG + "certainty.ended.properties";

    /** DOCUMENT ME! */
    private static final String CERTAINTY_TRANSITION_FILE = CAP_PG + "certainty.transition.out.properties";

    /** DOCUMENT ME! */
    private static final String EVENT_EN_FILE = CAP_PG + "event.en.properties";

    /** DOCUMENT ME! */
    private static final String EVENT_FR_FILE = CAP_PG + "event.fr.properties";

    /** DOCUMENT ME! */
    private static final String EVENT_CODE_FILE = CAP_PG + "eventCode.properties";

    /** DOCUMENT ME! */
    private static final String EXPIRY_DEFAULT_FILE = CAP_PG + "expiryDuration.default.properties";

    /** DOCUMENT ME! */
    private static final String EXPIRY_ENDED_FILE = CAP_PG + "expiryDuration.ended.properties";

    /** DOCUMENT ME! */
    private static final String EXPIRY_TRANSITION_FILE = CAP_PG + "expiryDuration.transition.out.properties";

    /** DOCUMENT ME! */
    private static final String HAZARD_FR_FILE = CAP_PG + "hazard.fr.properties";

    /** DOCUMENT ME! */
    private static final String PHRASE_REF_TRANSITION_FILE = CAP_PG + "phrase.reference.transition.properties";

    /** DOCUMENT ME! */
    private static final String RESPONSE_TYPE_ACTIVE_FILE = CAP_PG + "responseType.active.properties";

    /** DOCUMENT ME! */
    private static final String RESPONSE_TYPE_DEFAULT_FILE = CAP_PG + "responseType.default.properties";

    /** DOCUMENT ME! */
    private static final String RESPONSE_TYPE_ENDED_FILE = CAP_PG + "responseType.ended.properties";

    /** DOCUMENT ME! */
    private static final String SAME_CODE_FILE = CAP_PG + "sameCode.properties";

    /** DOCUMENT ME! */
    private static final String SEVERITY_ACTIVE_FILE = CAP_PG + "severity.active.properties";

    /** DOCUMENT ME! */
    private static final String SEVERITY_ENDED_FILE = CAP_PG + "severity.ended.properties";

    /** DOCUMENT ME! */
    private static final String TEXT_FR_FILE = CAP_PG + "text.fr.properties";

    /** DOCUMENT ME! */
    private static final String URGENCY_ACTIVE_FILE = CAP_PG + "urgency.active.properties";

    /** DOCUMENT ME! */
    private static final String URGENCY_ENDED_FILE = CAP_PG + "urgency.ended.properties";

    /** DOCUMENT ME! */
    private static final String URGENCY_TRANSITION_FILE = CAP_PG + "urgency.transition.out.properties";

    /** DOCUMENT ME! */
    private static final String VALID_DURATION_DEFAULT_FILE = CAP_PG + "validDuration.default.properties";

    /** DOCUMENT ME! */
    private static final String VALID_DURATION_ENDED_FILE = CAP_PG + "validDuration.ended.properties";

    /** DOCUMENT ME! */
    private static final String VALID_DURATION_TRANSITION_FILE = CAP_PG + "validDuration.transition.out.properties";

    /**
     * @param  args
     */
    public static void main(String[] args) {
        File file = FileUtils.getFile(INPUT_FILE);

        if ((file == null) || !file.exists()) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE +
                " could not be encapulated in a File object and cannot be used.");
        }

        if (!file.canRead()) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE + " cannot be read and used.");
        }

        // Create the directories
        try {
            FileUtils.forceMkdir(new File(WARNINGS_DECODER));
            FileUtils.forceMkdir(new File(CAP_PG));
        }
        catch (IOException e1) {
            System.out.println("Could not create the necessary directories: " + WARNINGS_DECODER + ", " + CAP_PG);
        }


        FileInputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        BufferedWriter bw = null;
        BufferedWriter bw2 = null;
        BufferedWriter bw3 = null;
        BufferedWriter bw4 = null;
        BufferedWriter bw5 = null;
        BufferedWriter bw6 = null;
        BufferedWriter bw7 = null;
        BufferedWriter bw8 = null;
        BufferedWriter bw9 = null;
        BufferedWriter bw10 = null;
        BufferedWriter bw11 = null;

        BufferedWriter b1 = null;
        BufferedWriter b2 = null;
        BufferedWriter b3 = null;
        BufferedWriter b4 = null;
        BufferedWriter b5 = null;
        BufferedWriter b6 = null;
        BufferedWriter b7 = null;
        BufferedWriter b8 = null;
        BufferedWriter b9 = null;
        BufferedWriter b10 = null;
        BufferedWriter b11 = null;
        BufferedWriter b12 = null;
        BufferedWriter b13 = null;
        BufferedWriter b14 = null;
        BufferedWriter b15 = null;
        BufferedWriter b16 = null;
        BufferedWriter b17 = null;
        BufferedWriter b18 = null;
        BufferedWriter b19 = null;
        BufferedWriter b20 = null;
        BufferedWriter b21 = null;
        BufferedWriter b22 = null;
        BufferedWriter b23 = null;
        BufferedWriter b24 = null;
        BufferedWriter b25 = null;
        BufferedWriter b26 = null;

        int i = 0;

        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, "ISO8859_1");
            br = new BufferedReader(isr);

            // Decoder Warnings
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CERTAINTY_FILE), "UTF-8"));
            bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EVENT_NAME_EC_CODE_EN_FILE), "UTF-8"));
            bw3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EVENT_NAME_EC_CODE_FR_FILE), "UTF-8"));
            bw4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EVENT_TYPE_FILE), "UTF-8"));
            bw5 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EXPIRES_DURATION_FILE), "UTF-8"));
            bw6 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(HAZARD_FILE), "UTF-8"));
            bw7 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MESSAGE_NAME_EN_FILE), "UTF-8"));
            bw8 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MESSAGE_NAME_FR_FILE), "UTF-8"));
            bw9 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SEVERITY_FILE), "UTF-8"));
            bw10 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(URGENCY_FILE), "UTF-8"));
            bw11 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(VALID_DURATION_FILE), "UTF-8"));

            // PG CAP
            b1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(AUDIENCE_FILE), "UTF-8"));
            b2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CATEGORY_FILE), "UTF-8"));
            b3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CERTAINTY_ACTIVE_FILE), "UTF-8"));
            b4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CERTAINTY_ENDED_FILE), "UTF-8"));
            b5 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CERTAINTY_TRANSITION_FILE), "UTF-8"));
            b6 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EVENT_EN_FILE), "UTF-8"));
            b7 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EVENT_FR_FILE), "UTF-8"));
            b8 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EVENT_CODE_FILE), "UTF-8"));
            b9 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EXPIRY_DEFAULT_FILE), "UTF-8"));
            b10 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EXPIRY_ENDED_FILE), "UTF-8"));
            b11 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EXPIRY_TRANSITION_FILE), "UTF-8"));
            b12 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(HAZARD_FR_FILE), "UTF-8"));
            b13 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PHRASE_REF_TRANSITION_FILE), "UTF-8"));
            b14 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESPONSE_TYPE_ACTIVE_FILE), "UTF-8"));
            b15 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESPONSE_TYPE_DEFAULT_FILE), "UTF-8"));
            b16 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESPONSE_TYPE_ENDED_FILE), "UTF-8"));
            b17 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SAME_CODE_FILE), "UTF-8"));
            b18 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SEVERITY_ACTIVE_FILE), "UTF-8"));
            b19 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SEVERITY_ENDED_FILE), "UTF-8"));
            b20 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TEXT_FR_FILE), "UTF-8"));
            b21 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(URGENCY_ACTIVE_FILE), "UTF-8"));
            b22 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(URGENCY_ENDED_FILE), "UTF-8"));
            b23 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(URGENCY_TRANSITION_FILE), "UTF-8"));
            b24 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(VALID_DURATION_DEFAULT_FILE),
                        "UTF-8"));
            b25 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(VALID_DURATION_ENDED_FILE), "UTF-8"));
            b26 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(VALID_DURATION_TRANSITION_FILE),
                        "UTF-8"));

            String line = StringUtils.EMPTY;

            for (line = br.readLine(); line != null; line = br.readLine(), i++) {

                // Make sure to save the excel spreadsheets with the pipe delimiter, and not the comma delimiter!
                String[] entries = line.split("\\|");

                // let's see if we even have anything, otherwise, skip
                if (entries == null) {
                    continue;
                }

                System.out.println("Handling line: " + line + " with list of entries: " + entries +
                    " at line number: " + i);

                // the EC Code is mostly the key to everything
                String ecCode = entries[0];

                // DECODER WARNINGS

                // let's handle the certainty values
                String certainty = entries[22];
                bw.write("certainty." + ecCode + "=" + certainty + "\n");
                bw.flush();

                // let's handle event name to ec code EN
                String eventNameEN = entries[2];
                eventNameEN = eventNameEN.replaceAll(" ", "\\\\ ");
                eventNameEN = eventNameEN.toLowerCase();
                bw2.write("eventName." + eventNameEN + "=" + ecCode + "\n");
                bw2.flush();

                // let's handle event name to ec code FR
                String eventNameFR = entries[3];
                eventNameFR = eventNameFR.replaceAll(" ", "\\\\ ");
                eventNameFR = eventNameFR.replaceAll("é", "e");
                eventNameFR = eventNameFR.replaceAll("ê", "e");
                eventNameFR = eventNameFR.replaceAll("ô", "o");
                eventNameFR = eventNameFR.replaceAll("ç", "c");
                eventNameFR = eventNameFR.replaceAll("è", "e");
                eventNameFR = eventNameFR.toLowerCase();
                bw3.write("eventName." + eventNameFR + "=" + ecCode + "\n");
                bw3.flush();

                // let's handle event type
                String eventType = entries[32];
                bw4.write("eventType." + ecCode + "=" + eventType + "\n");
                bw4.flush();

                // let's handle the expires duration
                String expiresDuration = entries[34];
                bw5.write("expiresDuration." + ecCode + "=" + expiresDuration + "\n");
                bw5.flush();

                // let's handle the hazard
                String hazard = entries[1];
                bw6.write("hazard." + ecCode + "=" + hazard + "\n");
                bw6.flush();

                // let's handle the message name EN
                String messageNameEN = entries[2];
                bw7.write("en.messageName." + ecCode + "=" + messageNameEN + "\n");
                bw7.flush();

                // let's handle the message name FR
                String messageNameFR = entries[3];
                bw8.write("fr.messageName." + ecCode + "=" + messageNameFR + "\n");
                bw8.flush();

                // let's handle the severity
                String severity = entries[21];
                bw9.write("severity." + ecCode + "=" + severity + "\n");
                bw9.flush();

                // let's handle the urgency
                String urgency = entries[20];
                bw10.write("urgency." + ecCode + "=" + urgency + "\n");
                bw10.flush();

                // let's handle valid duration
                String validDuration = entries[33];
                bw11.write("validDuration." + ecCode + "=" + validDuration + "\n");
                bw11.flush();

                // PG CAP

                // let's handle the audience
                String audience = entries[29];
                b1.write("audience." + ecCode + "=" + audience + "\n");
                b1.flush();

                // let's handle the category
                String category = entries[9];
                b2.write("category." + ecCode + "=" + category + "\n");
                b2.flush();

                // let's handle the certainty active
                String certaintyActive = entries[22];
                b3.write("certainty.active." + ecCode + "=" + certaintyActive + "\n");
                b3.flush();

                // let's handle the certainty ended
                String certaintyEnded = entries[25];
                b4.write("certainty.ended." + ecCode + "=" + certaintyEnded + "\n");
                b4.flush();

                // let's handle the certainty transitioned
                String certaintyTransition = entries[28];
                b5.write("certainty.transition.out." + ecCode + "=" + certaintyTransition + "\n");
                b5.flush();

                // let's handle the event EN
                String eventEN = entries[5];
                b6.write("event.en." + ecCode + "=" + eventEN + "\n");
                b6.flush();

                // let's handle the event FR
                String eventFR = entries[6];
                b7.write("event.fr." + ecCode + "=" + eventFR + "\n");
                b7.flush();

                // let's handle the event code
                String eventCode = entries[7];
                b8.write("eventCode." + ecCode + "=" + eventCode + "\n");
                b8.flush();

                // let's handle the expiry duration default
                String expiryDefault = entries[34];
                b9.write("expiryDuration.default." + ecCode + "=" + expiryDefault + "\n");
                b9.flush();

                // let's handle the expiry duration ended
                String expiryEnded = entries[36];
                b10.write("expiryDuration.ended." + ecCode + "=" + expiryEnded + "\n");
                b10.flush();

                // let's handle the expiry duration transition
                String expiryTransition = entries[38];
                b11.write("expiryDuration.transition.out." + ecCode + "=" + expiryTransition + "\n");
                b11.flush();

                // let's handle the hazard FR
                String hazardFR = entries[6];
                b12.write("frHazard." + ecCode + "=" + hazardFR + "\n");
                b12.flush();

                // let's handle the phrase reference transition - THIS CAN'T BE DONE HERE
//                String phraseReference = entries[0];
//                b13.write("event.en." + ecCode + "=" + phraseReference + "\n");
//                b13.flush();

                // let's handle the response type active
                String responseActive = entries[11];
                b14.write("responseType.active." + ecCode + "=" + responseActive + "\n");
                b14.flush();

                // let's handle the response type default
                String responseDefault = entries[17];
                b15.write("responseType.default." + ecCode + "=" + responseDefault + "\n");
                b15.flush();

                // let's handle the response type ended
                String responseEnded = entries[14];
                b16.write("responseType.ended." + ecCode + "=" + responseEnded + "\n");
                b16.flush();

                // let's handle the same code
                String sameCode = entries[8];
                b17.write("sameCode." + ecCode + "=" + sameCode + "\n");
                b17.flush();

                // let's handle the severity active
                String severityActive = entries[21];
                b18.write("severity.active." + ecCode + "=" + severityActive + "\n");
                b18.flush();

                // let's handle the severity ended
                String severityEnded = entries[24];
                b19.write("severity.ended." + ecCode + "=" + severityEnded + "\n");
                b19.flush();

                // let's handle the text FR
                String textFR = entries[3];
                b20.write("frText." + ecCode + "=" + textFR + "\n");
                b20.flush();

                // let's handle the urgency active
                String urgencyActive = entries[20];
                b21.write("urgency.active." + ecCode + "=" + urgencyActive + "\n");
                b21.flush();

                // let's handle the urgency ended
                String urgencyEnded = entries[23];
                b22.write("urgency.ended." + ecCode + "=" + urgencyEnded + "\n");
                b22.flush();

                // let's handle the urgency transition
                String urgencyTransition = entries[26];
                b23.write("urgency.transition.out." + ecCode + "=" + urgencyTransition + "\n");
                b23.flush();

                // let's handle the valid duration default
                String validDefault = entries[33];
                b24.write("validDuration.default." + ecCode + "=" + validDefault + "\n");
                b24.flush();

                // let's handle the valid duration ended
                String validEnded = entries[35];
                b25.write("validDuration.ended." + ecCode + "=" + validEnded + "\n");
                b25.flush();

                // let's handle the valid duration transition
                String validTransition = entries[37];
                b26.write("validDuration.transition.out." + ecCode + "=" + validTransition + "\n");
                b26.flush();
            }
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE + " could not be read correctly at line: " +
                i + ".  No designation information could be used: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);

            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(bw2);
            IOUtils.closeQuietly(bw3);
            IOUtils.closeQuietly(bw4);
            IOUtils.closeQuietly(bw5);
            IOUtils.closeQuietly(bw6);
            IOUtils.closeQuietly(bw7);
            IOUtils.closeQuietly(bw8);
            IOUtils.closeQuietly(bw9);
            IOUtils.closeQuietly(bw10);
            IOUtils.closeQuietly(bw11);

            IOUtils.closeQuietly(b1);
            IOUtils.closeQuietly(b2);
            IOUtils.closeQuietly(b3);
            IOUtils.closeQuietly(b4);
            IOUtils.closeQuietly(b5);
            IOUtils.closeQuietly(b6);
            IOUtils.closeQuietly(b7);
            IOUtils.closeQuietly(b8);
            IOUtils.closeQuietly(b9);
            IOUtils.closeQuietly(b10);
            IOUtils.closeQuietly(b11);
            IOUtils.closeQuietly(b12);
            IOUtils.closeQuietly(b13);
            IOUtils.closeQuietly(b14);
            IOUtils.closeQuietly(b15);
            IOUtils.closeQuietly(b16);
            IOUtils.closeQuietly(b17);
            IOUtils.closeQuietly(b18);
            IOUtils.closeQuietly(b19);
            IOUtils.closeQuietly(b20);
            IOUtils.closeQuietly(b21);
            IOUtils.closeQuietly(b22);
            IOUtils.closeQuietly(b23);
            IOUtils.closeQuietly(b24);
            IOUtils.closeQuietly(b25);
            IOUtils.closeQuietly(b26);
        }
    }
}
