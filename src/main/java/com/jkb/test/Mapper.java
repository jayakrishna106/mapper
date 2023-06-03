package com.jkb.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Mapper {
    private final String mappingFilePath; // Path to the mapper JSON file

    public Mapper(String mappingFilePath) {
        this.mappingFilePath = mappingFilePath;
    }

    public String mapJsonToXml(String json) {
        // Parse the JSON input
        Document document = createEmptyXmlDocument();
        Map<String, Object> jsonMap = JsonPath.parse(json).read("$");

        // Read the mapper JSON file
        List<Map<String, Object>> mappings = readMapperJsonFile();

        // Apply field mappings
        for (Map<String, Object> mapping : mappings) {
            String type = (String) mapping.get("type");
            String source = (String) mapping.get("source");
            String target = (String) mapping.get("target");
            String action = (String) mapping.get("action");

            Object value = null;
            if ("JSON".equals(type)) {
                value = JsonPath.parse(jsonMap).read(source);
            } else if ("XML".equals(type)) {
                value = XmlUtils.evaluateXPath(document, source);
            }

            // Apply action (if specified)
            if (value != null && action != null) {
                value = applyAction(value, action);
            }

            // Set the value in the XML document
            XmlUtils.setValueByXPath(document, target, value);
        }

        // Convert the XML document to a string
        return XmlUtils.convertXmlDocumentToString(document);
    }

    public String mapXmlToJson(String xml) throws JsonProcessingException {
        // Parse the XML input
        Document document = createXmlDocument(xml);

        // Read the mapper JSON file
        List<Map<String, Object>> mappings = readMapperJsonFile();

        // Create a JSON object to hold the mapped values
        Map<String, Object> jsonMap = new LinkedHashMap<>();

        // Apply field mappings
        for (Map<String, Object> mapping : mappings) {
            String type = (String) mapping.get("type");
            String source = (String) mapping.get("source");
            String target = (String) mapping.get("target");
            String action = (String) mapping.get("action");

            Object value = null;
            if ("JSON".equals(type)) {
                value = JsonPath.parse(jsonMap).read(source);
            } else if ("XML".equals(type)) {
                value = XmlUtils.evaluateXPath(document, source);
            }

            // Apply action (if specified)
            if (value != null && action != null) {
                value = applyAction(value, action);
            }

            // Set the value in the JSON map
            jsonMap.put(target, value);
        }

        // Convert the JSON map to a string
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    private List<Map<String, Object>> readMapperJsonFile() {
        // Read and parse the mapper JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(mappingFilePath), new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return Collections.emptyList();
    }

    private Document createEmptyXmlDocument() {
        // Create an empty XML document
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    private Document createXmlDocument(String xml) {
        // Parse the XML string and create an XML document
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xml));
            return documentBuilder.parse(inputSource);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    private Object applyAction(Object value, String action) {
        // Apply the specified action on the value
        if ("UPPERCASE".equals(action)) {
            return value.toString().toUpperCase();
        } else if ("LOWERCASE".equals(action)) {
            return value.toString().toLowerCase();
        } else if ("JOIN".equals(action)) {
            // Assuming value is a List<String> for joining
            List<String> sourceValues = (List<String>) value;
            String separator = " - ";
            return String.join(separator, sourceValues);
        }
        return value;
    }


}
//
//import com.jayway.jsonpath.JsonPath;
//        import org.w3c.dom.Document;
//        import org.xml.sax.InputSource;
//        import org.xml.sax.SAXException;
//
//        import javax.xml.parsers.DocumentBuilder;
//        import javax.xml.parsers.DocumentBuilderFactory;
//        import javax.xml.parsers.ParserConfigurationException;
//        import java.io.File;
//        import java.io.IOException;
//        import java.io.StringReader;
//        import java.util.List;
//        import java.util.Map;
//
//public class Mapper {
//    private final String mappingFilePath; // Path to the mapper JSON file
//
//    public Mapper(String mappingFilePath) {
//        this.mappingFilePath = mappingFilePath;
//    }
//
//    public void processFiles(List<String> inputFiles) {
//        // Read the mapper JSON file
//        List<Map<String, Object>> mappings = readMapperJsonFile();
//
//        // Process each input file
//        for (String inputFile : inputFiles) {
//            File file = new File(inputFile);
//            String fileExtension = getFileExtension(file.getName());
//
//            if ("json".equalsIgnoreCase(fileExtension)) {
//                String json = readJsonFile(file);
//                String mappedXml = mapJsonToXml(json, mappings);
//                // Perform further operations with the mapped XML
//            } else if ("xml".equalsIgnoreCase(fileExtension)) {
//                String xml = readXmlFile(file);
//                String mappedJson = mapXmlToJson(xml, mappings);
//                // Perform further operations with the mapped JSON
//            } else {
//                // Handle unsupported file format
//            }
//        }
//    }
//
//    private String mapJsonToXml(String json, List<Map<String, Object>> mappings) {
//        // Parse the JSON input
//        Document document = createEmptyXmlDocument();
//        Map<String, Object> jsonMap = JsonPath.parse(json).read("$");
//
//        // Apply field mappings
//        for (Map<String, Object> mapping : mappings) {
//            // Mapping logic as before
//        }
//
//        // Convert the XML document to a string
//        return XmlUtils.convertXmlDocumentToString(document);
//    }
//
//    private String mapXmlToJson(String xml, List<Map<String, Object>> mappings) {
//        // Parse the XML input
//        Document document = createXmlDocument(xml);
//
//        // Create a JSON object to hold the mapped values
//        Map<String, Object> jsonMap = new LinkedHashMap<>();
//
//        // Apply field mappings
//        for (Map<String, Object> mapping : mappings) {
//            // Mapping logic as before
//        }
//
//        // Convert the JSON map to a string
//        return new ObjectMapper().writeValueAsString(jsonMap);
//    }
//
//    // Other helper methods as before
//
//    private String getFileExtension(String fileName) {
//        int lastDotIndex = fileName.lastIndexOf('.');
//        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
//            return fileName.substring(lastDotIndex + 1).toLowerCase();
//        }
//        return "";
//    }
//
//    private String readJsonFile(File file) {
//        // Read and return the content of the JSON file
//    }
//
//    private String readXmlFile(File file) {
//        // Read and return the content of the XML file
//    }
//}

