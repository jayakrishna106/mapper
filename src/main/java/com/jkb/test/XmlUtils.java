package com.jkb.test;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringWriter;

public class XmlUtils {
    public static Object evaluateXPath(Document document, String xpathExpression) {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expression = xPath.compile(xpathExpression);
            return expression.evaluate(document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    public static void setValueByXPath(Document document, String xpathExpression, Object value) {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expression = xPath.compile(xpathExpression);
            Node node = (Node) expression.evaluate(document, XPathConstants.NODE);

            if (node != null) {
                // Remove existing child nodes
                NodeList childNodes = node.getChildNodes();
                for (int i = childNodes.getLength() - 1; i >= 0; i--) {
                    node.removeChild(childNodes.item(i));
                }

                // Set the new value as a text node
                node.appendChild(document.createTextNode(value.toString()));
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public static String convertXmlDocumentToString(Document document) {
        try {
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }
}

