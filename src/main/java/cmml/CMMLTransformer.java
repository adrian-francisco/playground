/*
 * CMML_Transformer.java
 * Created on August 29, 2007, 10:36 AM
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cmml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * CMML_Transformer class automatically detects new files in the specified source directory
 * and converts them into another version of CMML.
 * Converted files are sent to the specified destination directory, and source files are
 * moved to the specified archive directory.
 */
public class CMMLTransformer {

    /** The src dir. */
    private File srcDir;

    /** The archive dir. */
    private File archiveDir;

    /** The dest dir. */
    private File destDir;

    /** The xslt src dir. */
    private File xsltSrcDir;

    /** The xslt converter. */
    private File xsltConverter;

    /** The cmml out ver. */
    private String cmmlOutVer;

    /** The src files. */
    private ArrayList srcFiles;

    /**
     * Creates a new instance of CMML_Transformer.
     *
     * @param src the name of source directory
     * @param archive the name of archive directory
     * @param dst the name of destination directory for converted files
     * @param xsl_src the name of directory where xslt files are saved
     * @param ver the CMML version to convert to ( 1.0 or 2.02 )
     */
    public CMMLTransformer(String src, String archive, String dst, String xsl_src, String ver) {
        srcDir = new File(src);
        archiveDir = new File(archive);
        destDir = new File(dst);
        xsltSrcDir = new File(xsl_src);
        xsltConverter = null;
        cmmlOutVer = ver;
        srcFiles = null;
        String[] subfiles = xsltSrcDir.list();

        // if new files are detected
        if (subfiles != null) {
            for (int i = 0; i < subfiles.length; i++) {
                if (cmmlOutVer.equals("1.0") && subfiles[i].equalsIgnoreCase("cmmlv2.02-to-v1.0.xsl")) {
                    xsltConverter = new File(xsltSrcDir + "/" + subfiles[i]);
                }
                else if (cmmlOutVer.equals("2.02") && subfiles[i].equalsIgnoreCase("cmmlv1.0-to-v2.02.xsl")) {
                    xsltConverter = new File(xsltSrcDir + "/" + subfiles[i]);
                }
            }

        }
        if (!xsltConverter.exists()) {
            logMessage("Cannot find an xslt file for conversion in " + xsltSrcDir.getName(), "ERROR");
        }
    }

    /**
     * Retrieves a list of files ending in .cmml from the source directory. Use getFileCount() and getFileName()
     * to itterate through the list files.
     *
     * @return the files
     */
    public void getFiles() {
        FilenameFilter filter = new CMMLFilter();
        String[] tempList = srcDir.list(filter);
        srcFiles = new ArrayList();
        for (int i = 0; i < tempList.length; i++) {
            srcFiles.add(tempList[i]);
        }

    }

    /**
     * Gets the number of files found in the specified source directory.
     *
     * @return the number of files in the specified source directory
     */
    public int getFileCount() {
        return srcFiles.size();
    }

    /**
     * Gets the name of a file found in a previous getFiles() call.
     *
     * @param index the index of the filename to retrieve
     * @return the name of the file in the source directory
     */
    public String getFileName(int index) {
        if (index < srcFiles.size()) {
            return (String) srcFiles.get(index);
        }
        return null;
    }

    /**
     * Converts a CMML file from one version to another. If the conversion is successfull the converted file
     * can be found in the destination directory and the orginal file is moved to the archive directory.
     * <p>
     * If the specified CMML file to convert is already of the correct format it is copied to the destination
     * directory and then the original is moved to the archive directory.
     * <p>
     * This converter will only convert CMML 1.0 to 2.02 and CMML 2.02 to 1.0.
     *
     * @param srcFilename the file to convert
     * @return returns true if file was converted correctly
     */
    public boolean convert(String srcFilename) {
        boolean result = false;
        try {
            File src_file = new File(srcDir + "/" + srcFilename);
            logMessage("Working on file " + src_file.getAbsolutePath(), "INFO");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            InputSource in = new InputSource(new FileInputStream(src_file));
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document cmmlDoc = builder.parse(in);
            Element root = cmmlDoc.getDocumentElement();
            String cmmlInVer = root.getAttribute("version");

            if (cmmlInVer.equals(cmmlOutVer)) {
                logMessage(srcFilename + " is already version " + cmmlOutVer +
                        ". Copying source to destintation directory.", "INFO");
                File cmmlOutFile = new File(destDir + "/" + srcFilename);
                copyFile(src_file, cmmlOutFile);
            }
            else if (cmmlInVer.equals("1.0") && cmmlOutVer.equals("2.02")) {
                // creates a new converted file name

                FileOutputStream converted = new FileOutputStream(destDir + "/" + srcFilename);
                Source xslFile = new StreamSource(xsltConverter);
                Source cmmlSrc = new DOMSource(cmmlDoc);

                TransformerFactory transF = TransformerFactory.newInstance();
                Transformer trans = transF.newTransformer(xslFile);
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                trans.setOutputProperty(OutputKeys.METHOD, "xml");
                trans.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                // trans.setOutputProperty(OutputKeys.INDENT, "yes");
                // trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                trans.transform(cmmlSrc, new StreamResult(converted));
                logMessage(srcFilename + " is converted and saved to " + destDir, "INFO");

                // flush and close a converted file
                converted.flush();
                converted.close();
            }
            else if (cmmlInVer.equals("2.02") && cmmlOutVer.equals("1.0")) {
                // creates a new converted file name
                FileOutputStream converted = new FileOutputStream(destDir + "/" + srcFilename);
                Source xslFile = new StreamSource(xsltConverter);
                Source cmmlSrc = new DOMSource(cmmlDoc);

                TransformerFactory transF = TransformerFactory.newInstance();
                Transformer trans = transF.newTransformer(xslFile);
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                trans.setOutputProperty(OutputKeys.METHOD, "xml");
                trans.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

                trans.transform(cmmlSrc, new StreamResult(converted));
                logMessage(srcFilename + " is converted and saved to " + destDir, "INFO");

                // flush and close a converted file
                converted.flush();
                converted.close();

            }
            else {
                logMessage("Can not convert CMML from " + cmmlInVer + " to " + cmmlOutVer + ".", "ERROR");
                return false;
            }

            // Move file to new directory
            result = src_file.renameTo(new File(archiveDir, src_file.getName()));
            // if failed to move files, then show an error message
            if (!result) {
                logMessage("Failed to move the file: " + src_file.getName(), "ERROR");
            }
            else {
                logMessage(srcFilename + " is moved to archive directory " + archiveDir, "INFO");
            }
        }
        catch (Exception e) {
            logMessage(e.getMessage(), "ERROR");
        }
        return result;
    }

    /**
     * Validates parameters from the command line.
     *
     * @param args the list of parameter strings
     * @return true if parameters are valid
     */
    public static boolean validateParams(String[] args) {
        // Number of parameters
        if (args.length != 5) {
            System.err.println("Invalid number of Aguments: " + args.length);
            System.err.println("1: Source Directory");
            System.err.println("2: Archive Directory");
            System.err.println("3: Output Directory");
            System.err.println("4: CMML XSLT Directory");
            System.err.println("5: CMML Output Version");
            return false;
        }

        if (!directory_exists(args[0])) {
            return false;
        }
        if (!directory_exists(args[1])) {
            return false;
        }
        if (!directory_exists(args[2])) {
            return false;
        }
        if (!directory_exists(args[3])) {
            return false;
        }

        if (!args[4].equals("1.0") && !args[4].equals("2.02")) {
            logMessage("Only 1.0 and 2.02 are valid CMML Output Versions. " + args[4] + " is invalid", "ERROR");
            return false;
        }

        return true;
    }

    /**
     * Checks to see if a specified directory exists.
     *
     * @param Dir the name of the directory to check
     * @return true if the specified directory exists
     */
    public static boolean directory_exists(String Dir) {

        File myFile = new File(Dir);
        if (myFile.exists()) {
            if (myFile.isDirectory()) {
                return true;
            }
            else {
                logMessage("'" + Dir + "' is not a Directory.", "ERROR");
            }

        }
        else {
            logMessage("Directory '" + Dir + "' does not exist. ", "ERROR");
        }
        return false;
    }

    /**
     * Copies a file.
     *
     * @param src The file to be copied.
     * @param dst The destination filename.
     * @return true if the file was copied.
     */
    public static boolean copyFile(File src, File dst) {
        try {
            logMessage("copying " + src.getName() + " to " + dst.getName(), "INFO");
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        catch (FileNotFoundException fnfe) {
            logMessage(fnfe.getMessage(), "ERROR");
        }
        catch (IOException ioe) {
            logMessage(ioe.getMessage(), "ERROR");
        }
        return true;

    }

    /**
     * Outputs a message to standard output or standard error.
     * The format of the message is 'date/time : type : message'.
     * <p>
     * date/time is of the format yyyymmddhhmmss
     * <p>
     * type is either ERROR or INFO
     *
     * @param message the message to output
     * @param type the type of message. ERROR will be sent to standard error, otherwise standard out.
     */
    public static void logMessage(String message, String type) {
        Date curDate = new Date();
        Format dateformatter = new SimpleDateFormat("yyyyMMddHHmmss");
        if (type.equals("ERROR")) {
            System.err.println(dateformatter.format(curDate) + " : ERROR : " + message);
        }
        else {
            System.out.println(dateformatter.format(curDate) + " : INFO  : " + message);
        }
    }

    /**
     * The method main executes the following logic:
     * <p>
     * If the input parameters are valid then create a CMML_Transformer object with these parameters.
     * Get a list of CMML files in the source directory. For each file run the convert method to create
     * a converted CMML document and store the CMML document in the archive directory.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (!validateParams(args)) {
            System.exit(-1);
        } /*
           * (new File("C:/Yungjae/CMML/QC_samples/OQ.AB.TLVT.AB_DOT_1-02.20060131203938.cmml"),
           * new File("C:/Documents and Settings/choy/My Documents/temp"),
           * new File("C:/Yungjae/CMML/XSL/cmmlv2.02-to-v1.0.xsl"));
           * // new File("C:/Yungjae/CMML/MetaData_Samples"));
           * java.awt.List list = new java.awt.List();
           * trans.convertToCMML(list);
           */
        /*
         * CMML_Transformer transform = new CMML_Transformer("C:/workspace/src/OB.BC.MOT.11091.20040101120000.cmml",
         * "C:/workspace/archive",
         * "C:/workspace/converted_cmml",
         * "C:/workspace/xslt/",
         * "cmmlv2.02-to-v1.0.xsl" );
         */
        CMMLTransformer transform = new CMMLTransformer(args[0], args[1], args[2], args[3], args[4]);
        transform.getFiles();
        logMessage(transform.getFileCount() + " files found in " + args[0], "INFO");
        for (int index = 0; index < transform.getFileCount(); index++) {
            transform.convert(transform.getFileName(index));
        }
        logMessage("Transformation of CMML Files done", "INFO");
    }

    private class CMMLFilter implements FilenameFilter {

        public boolean accept(File dir, String name) {
            return name.endsWith(".cmml");
        }
    }
}
