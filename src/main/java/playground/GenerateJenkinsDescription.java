/*
 * Name   : GenerateJenkinsDescription.java
 * Author : AdrianF
 * Created: 2014-06-02
 */
package playground;

import java.io.BufferedReader;
import java.io.StringReader;


/**
 * Generates the Jenkins Descriptions.
 *
 * @author  Adrian Francisco
 */
public class GenerateJenkinsDescription {

    /** the input */
    private static String input = "116=DMS Codecon Retirement\r\n" +
        "109=DMS Testing and Deployment\r\n" +
        "112=MSC-DARTS-Dialler-PAKBUS\r\n" +
        "134=MSC-DMS-Application-Manager-RWIN\r\n" +
        "19=MSC-DMS-Commons\r\n" +
        "114=MSC-DMS-Config-ECAM\r\n" +
        "130=MSC-DMS-Config-MAF\r\n" +
        "110=MSC-DMS-Config-QA-Rules\r\n" +
        "98=MSC-DMS-Config-Sfc-Wx\r\n" +
        "108=MSC-DMS-Config-Warnings\r\n" +
        "96=MSC-DMS-Config-WXO\r\n" +
        "7=MSC-DMS-Core\r\n" +
        "125=MSC-DMS-Core-Generic-Metadata\r\n" +
        "117=MSC-DMS-Creator-Generic-Metadata\r\n" +
        "132=MSC-DMS-Creator-MAF-Alert\r\n" +
        "131=MSC-DMS-Creator-MAF-Event\r\n" +
        "71=MSC-DMS-Creator-SYNOP\r\n" +
        "14=MSC-DMS-Decoder-AMDAR\r\n" +
        "65=MSC-DMS-Decoder-AQHI\r\n" +
        "99=MSC-DMS-Decoder-Aviation-Warning\r\n" +
        "42=MSC-DMS-Decoder-BUFR-TDCF\r\n" +
        "88=MSC-DMS-Decoder-BUFR-WinIDE\r\n" +
        "62=MSC-DMS-Decoder-BUFR2XML\r\n" +
        "41=MSC-DMS-Decoder-CA\r\n" +
        "90=MSC-DMS-Decoder-Enhanced-BUFR-WinIDE\r\n" +
        "72=MSC-DMS-Decoder-Enhanced-CA\r\n" +
        "73=MSC-DMS-Decoder-Enhanced-RA\r\n" +
        "54=MSC-DMS-Decoder-Forestry-AB\r\n" +
        "37=MSC-DMS-Decoder-Forestry-BC\r\n" +
        "128=MSC-DMS-Decoder-Generic-Forestry-AB\r\n" +
        "127=MSC-DMS-Decoder-Generic-Forestry-BC\r\n" +
        "126=MSC-DMS-Decoder-Generic-METAR\r\n" +
        "129=MSC-DMS-Decoder-Generic-WBS\r\n" +
        "107=MSC-DMS-Decoder-GRIB\r\n" +
        "66=MSC-DMS-Decoder-Hurricane\r\n" +
        "43=MSC-DMS-Decoder-JICC\r\n" +
        "13=MSC-DMS-Decoder-METAR\r\n" +
        "113=MSC-DMS-Decoder-Moored-Buoy\r\n" +
        "119=MSC-DMS-Decoder-MT2\r\n" +
        "48=MSC-DMS-Decoder-NationalMFile\r\n" +
        "120=MSC-DMS-Decoder-NinJo-Alert2\r\n" +
        "81=MSC-DMS-Decoder-Official-METAR-Scheduler\r\n" +
        "47=MSC-DMS-Decoder-PIREP\r\n" +
        "60=MSC-DMS-Decoder-RA\r\n" +
        "56=MSC-DMS-Decoder-RWIN\r\n" +
        "102=MSC-DMS-Decoder-SARWinds\r\n" +
        "30=MSC-DMS-Decoder-SYNOP\r\n" +
        "40=MSC-DMS-Decoder-TAF\r\n" +
        "39=MSC-DMS-Decoder-UpperAir\r\n" +
        "34=MSC-DMS-Decoder-WarningsMFile\r\n" +
        "78=MSC-DMS-Encoder-ASCII-SYNOP\r\n" +
        "89=MSC-DMS-Encoder-BUFR-XML2BUFR\r\n" +
        "59=MSC-DMS-Encoder-METAR\r\n" +
        "97=MSC-DMS-Encoder-SA\r\n" +
        "105=MSC-DMS-Encoder-SARWinds-WIPS\r\n" +
        "135=MSC-DMS-Enhanced-Generic\r\n" +
        "35=MSC-DMS-Extractor-Archive\r\n" +
        "17=MSC-DMS-Importer-Bulletin\r\n" +
        "118=MSC-DMS-Ingester-Metadata\r\n" +
        "79=MSC-DMS-Load-Balancer-Scripts\r\n" +
        "103=MSC-DMS-Manager-SARWinds-Surface\r\n" +
        "67=MSC-DMS-Manager-SYNOP\r\n" +
        "61=MSC-DMS-Metadata-GUI\r\n" +
        "29=MSC-DMS-Metadata-Updater\r\n" +
        "51=MSC-DMS-Metadata-Warning\r\n" +
        "121=MSC-DMS-PG-Active-Alert-Summary2\r\n" +
        "57=MSC-DMS-PG-Barometry\r\n" +
        "50=MSC-DMS-PG-CAP\r\n" +
        "115=MSC-DMS-PG-ECAM-TwitterAlertGenerator\r\n" +
        "91=MSC-DMS-PG-External-XML\r\n" +
        "122=MSC-DMS-PG-MFile2\r\n" +
        "123=MSC-DMS-PG-MT2\r\n" +
        "15=MSC-DMS-PG-NinJo\r\n" +
        "92=MSC-DMS-PG-Official-METAR\r\n" +
        "46=MSC-DMS-PG-PRE-SYNOP\r\n" +
        "52=MSC-DMS-PG-Profile-AMDAR\r\n" +
        "63=MSC-DMS-PG-RWIN\r\n" +
        "64=MSC-DMS-PG-RWIN-Alert\r\n" +
        "104=MSC-DMS-PG-SARWinds-NinJo\r\n" +
        "106=MSC-DMS-PG-SARWinds-Web\r\n" +
        "16=MSC-DMS-PG-SYNOP\r\n" +
        "100=MSC-DMS-PG-Timeliness\r\n" +
        "124=MSC-DMS-PG-Warnings2\r\n" +
        "49=MSC-DMS-PG-WBS\r\n" +
        "85=MSC-DMS-PG-WXO-CanadaHotColdSpot\r\n" +
        "87=MSC-DMS-PG-WXO-HourlyObs\r\n" +
        "84=MSC-DMS-PG-WXO-RecordValues\r\n" +
        "82=MSC-DMS-PG-WXO-Summary\r\n" +
        "83=MSC-DMS-PG-WXO-Syno\r\n" +
        "24=MSC-DMS-Publisher\r\n" +
        "58=MSC-DMS-Quality-Assessment\r\n" +
        "33=MSC-DMS-Translator-BUFR\r\n" +
        "93=MSC-DW-Dataviewer\r\n" +
        "133=MSC-DW-Views\r\n" +
        "111=MSC-WBS";

    /**
     * The main.
     *
     * @param   args  the args
     *
     * @throws  Exception  on anything
     */
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new StringReader(input));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] split = line.split("=");

            String name = split[1];
            String mantis = split[0];
            String dir = name.toLowerCase().replace("msc-", "");

            String message = "<ul>\n" +
                "  <li><a href=\"http://ecollab.ncr.int.ec.gc.ca/theme/redsection/Projects/Forms/AllItems.aspx?RootFolder=%2ftheme%2fredsection%2fProjects%2f" +
                name + "\">Ecollab Documentation</a></li>\n" +
                "  <li><a href=\"http://dms-wiki.to.on.ec.gc.ca/wiki/index.php/" + name +
                "\">Wiki Documentation</a></li>\n" +
                "  <li><a href=\"http://nadm-mantis-dmf.ontario.int.ec.gc.ca/changelog_page.php?project_id=" + mantis +
                "\">Official Changes</a></li>\n" +
                "  <li><a href=\"http://dms-stability.to.on.ec.gc.ca/dms/" + dir +
                "\">Stability Installation</a></li>\n" +
                "  <li><a href=\"http://dms-dev6.to.on.ec.gc.ca/dms-regression/latest/components.php?chosen[]=" + dir +
                "\">Regression Results</a></li>\n" +
                "  <li><a href=\"http://dms-releases.to.on.ec.gc.ca/releases/" + name +
                "\">Releases Directory</a></li>\n" +
                "</ul>\n";

            System.out.println(name);
            System.out.println();
            System.out.println(message);
        }
    }
}
