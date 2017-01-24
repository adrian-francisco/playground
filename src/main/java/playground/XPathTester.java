/*
 * Name   : XPathTester.java
 * Author : Adrian Francisco
 * Created: 2015-07-24
 */
package playground;

import org.apache.commons.io.IOUtils;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;


/**
 * All things XPath.
 *
 * <p>XPath works nice an easy when the XML is simple. However; once you start working with more complicated XMLs with
 * one or more namespaces, XPath gets pretty funny. Recommend reading this article:
 * http://www.ibm.com/developerworks/library/x-nmspccontext</p>
 *
 * @author  Adrian Francisco
 */
public class XPathTester {

    /**
     * The main method.
     *
     * @param   args  the args
     *
     * @throws  Exception  on any exception
     */
    public static void main(String[] args) throws Exception {

        // test using a basic xml without namesspaces
        // notice that the xpath is clean and simple with no colons
        System.out.println(evaluate("unit-conversion.xml", "/unitConversionResult/unitConversion/fromUnitCode"));

        // test using an xml with namespaces, extracting an element value
        // notice that elements must be prefixed with a ":" to tell xpath to use the default namespace
        System.out.println(evaluate("point-obs-small.xml",
                "/om:ObservationCollection/om:member/om:Observation/om:result/:orig-header"));

        // we should code to allow specifying either ":orig-header" OR "orig-header", meaning you should be prefixing
        // elements with colons, if necessary
        // this test currently does not work, and will leave to you to complete
        System.out.println(evaluate("point-obs-small.xml",
                "/om:ObservationCollection/om:member/om:Observation/om:result/orig-header"));

        // test using an xml with namespaces, extracting an attribute value
        // notice that elements must be prefixed with a ":" but attributes do not
        // attributes inherit the namespace of their parent element
        System.out.println(evaluate("point-obs-small.xml",
                "/om:ObservationCollection/om:member/om:Observation/om:metadata/:set/:general/:author/@name"));
    }

    /**
     * Evaluate the xpath for the given filename and expression.
     *
     * @param   filename    the filename
     * @param   expression  the expression
     *
     * @return  the xpath value; empty string otherwise
     *
     * @throws  Exception  on any exception
     */
    private static String evaluate(String filename, String expression) throws Exception {
        String xml = IOUtils.toString(XPathTester.class.getResourceAsStream("/" + filename));

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        // this flag tells DOM to be aware of the namespaces and include them in the parsing,
        // without this, only simple XMLs will work
        builderFactory.setNamespaceAware(true);

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));

        XPath xpath = XPathFactory.newInstance().newXPath();

        // this tells XPath about the different namespaces it should be expecting,
        // typically, this is hardcoded to the known namespaces of your XML, but since we are working on any XML, the
        // namespace context has to evaluate its values from the parsed document
        xpath.setNamespaceContext(new UniversalNamespaceContext(document));

        String result = xpath.compile(expression).evaluate(document);

        return result;
    }

    /**
     * The Univeral Namespace Context.
     */
    public static class UniversalNamespaceContext implements NamespaceContext {

        /** the document */
        private Document document;

        /**
         * Creates a new object.
         *
         * @param  document  the document
         */
        public UniversalNamespaceContext(Document document) {
            this.document = document;
        }

        /**
         * {@inheritDoc}
         */
        public String getNamespaceURI(String prefix) {

            if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
                return document.lookupNamespaceURI(null);
            }
            else {
                return document.lookupNamespaceURI(prefix);
            }
        }

        /**
         * {@inheritDoc}
         */
        public String getPrefix(String namespaceURI) {
            throw new UnsupportedOperationException("not used");
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("rawtypes")
        public Iterator getPrefixes(String namespaceURI) {
            throw new UnsupportedOperationException("not used");
        }
    }
}
