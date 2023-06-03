package com.jkb.test;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public class XmlDataSource extends DataSource {
    private String xml;

    public XmlDataSource(String xml) {
        this.xml = xml;
    }
    @Override
    public String getValue(String path) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));

            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression xPathExpression = xPath.compile(path);
            NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

            if (nodeList.getLength() > 0) {
                Node node = nodeList.item(0);
                return node.getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

/**
 * class XmlDataSource {
 *     private String filePath;
 *     private DocumentBuilderFactory documentBuilderFactory;
 *
 *     public XmlDataSource(String filePath) {
 *         this.filePath = filePath;
 *         this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
 *     }
 *
 *     public String getValue(String xpath) {
 *         try {
 *             DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
 *             org.w3c.dom.Document document = documentBuilder.parse(new File(filePath));
 *
 *             org.w3c.dom.NodeList nodeList = (NodeList) document.evaluate(xpath, document, javax.xml.xpath.XPathConstants.NODESET);
 *             if (nodeList.getLength() > 0) {
 *                 org.w3c.dom.Node node = nodeList.item(0);
 *                 return node.getTextContent();
 *             }
 *         } catch (Exception e) {
 *             e.printStackTrace();
 *         }
 *         return null;
 *     }
 * }
 */
