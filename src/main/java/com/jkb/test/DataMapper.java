package com.jkb.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {
    private List<DataSource> dataSources;
    private List<Action> actions;

    public DataMapper(List<DataSource> datasources, List<Action> actions) {
        this.dataSources = datasources;
        this.actions = actions;
    }

    public void mapData() {
        for (DataSource dataSource : dataSources) {
            List<TargetField> targetFields = new ArrayList<>();  // Create a list to store the target fields

            if (dataSource instanceof JsonDataSource) {
                JsonDataSource jsonDataSource = (JsonDataSource) dataSource;

                ObjectMapper objectMapper = new ObjectMapper();
                for (Mapping mapping : dataSource.getMappings()) {
                    if (mapping.getSourceType().equalsIgnoreCase("json")) {
                        String sourceValue = jsonDataSource.getValue(mapping.getSourcePath());
                        Object convertedValue = convertValue(sourceValue, mapping.getDataType());
                        applyActions(convertedValue, mapping.getActions());

                        TargetField targetField = new TargetField(mapping.getTargetField(), convertedValue);
                        targetFields.add(targetField);  // Add the target field to the list
                    }
                }
            } else if (dataSource instanceof XmlDataSource) {
                XmlDataSource xmlDataSource = (XmlDataSource) dataSource;
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.parse(new File(xmlDataSource.getPath()));
                    for (Mapping mapping : dataSource.getMappings()) {
                        if (mapping.getSourceType().equalsIgnoreCase("xml")) {
                            String sourceValue = xmlDataSource.getValue(mapping.getSourcePath());
                            Object convertedValue = convertValue(sourceValue, mapping.getDataType());
                            applyActions(convertedValue, mapping.getActions());

                            TargetField targetField = new TargetField(mapping.getTargetField(), convertedValue);
                            targetFields.add(targetField);  // Add the target field to the list
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Process the target fields (e.g., write to file, store in a database, etc.)
            processTargetFields(targetFields);
        }
    }

    private void processTargetFields(List<TargetField> targetFields) {
        // Load the template file
        File templateFile = new File("path/to/template.xml");  // Replace with the actual path to the template file
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(templateFile);

            // Update the target fields in the template document
            for (TargetField targetField : targetFields) {
                String targetPath = targetField.getPath();
                Object value = targetField.getValue();
                XPath xPath = XPathFactory.newInstance().newXPath();
                XPathExpression xPathExpression = xPath.compile(targetPath);

//                NodeList nodes = (NodeList) document.evaluate(targetPath, document, XPathConstants.NODESET);
                NodeList nodes = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);

                    // Set the value of the target field in the template document
                    if (value instanceof String) {
                        node.setTextContent((String) value);
                    } else {
                        // Handle other data types if necessary
                    }
                }
            }

            // Save the updated template document to a new file
            File outputFile = new File("path/to/output.xml");  // Replace with the desired path for the output file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

            System.out.println("Output file generated successfully: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Object convertValue(String value, String dataType) {
        Object convertedValue = value;

        // Perform data type conversion based on the specified dataType
        if (dataType.equalsIgnoreCase("integer")) {
            convertedValue = Integer.parseInt(value);
        } else if (dataType.equalsIgnoreCase("float")) {
            convertedValue = Float.parseFloat(value);
        } else if (dataType.equalsIgnoreCase("double")) {
            convertedValue = Double.parseDouble(value);
        } else if (dataType.equalsIgnoreCase("boolean")) {
            convertedValue = Boolean.parseBoolean(value);
        }

        return convertedValue;
    }

    private void applyActions(Object value, List<Action> actions) {
        for (Action action : actions) {
            String actionType = action.getType();
            if (actionType.equals("toUpperCase")) {
                if (value instanceof String) {
                    String transformedValue = ((String) value).toUpperCase();
                    value = transformedValue;
                }
            } else if (actionType.equals("toLowerCase")) {
                if (value instanceof String) {
                    String transformedValue = ((String) value).toLowerCase();
                    value = transformedValue;
                }
            } else if (actionType.equals("join")) {
                if (value instanceof String) {
                    String delimiter = action.getDelimiter();
                    List<String> parts = new ArrayList<>();
                    parts.add((String) value);
//                    for (String additionalField : action.getAdditionalFields()) {
//                        if (additionalField instanceof String) {
//                            parts.add((String) additionalField);
//                        }
//                    }
                    String transformedValue = String.join(delimiter, parts);
                    value = transformedValue;
                }
            } else if (actionType.equals("replace")) {
                if (value instanceof String) {
                    String search = action.getSearch();
                    String replacement = action.getReplacement();
                    String transformedValue = ((String) value).replace(search, replacement);
                    value = transformedValue;
                }
            } else if (actionType.equals("replaceAll")) {
                if (value instanceof String) {
                    String regex = action.getSearch();
                    String replacement = action.getReplacement();
                    String transformedValue = ((String) value).replaceAll(regex, replacement);
                    value = transformedValue;
                }
            } else if (actionType.equals("normalize")) {
                // Apply normalization logic to the value
                // ...
            } else if (actionType.equals("substring")) {
                if (value instanceof String) {
                    int start = action.getStart();
                    int end = action.getEnd();
                    if (start >= 0 && end >= 0 && start <= end && start < ((String) value).length()) {
                        String transformedValue = ((String) value).substring(start, end);
                        value = transformedValue;
                    }
                }
            } else {
                // Custom action
                // ...
            }
        }
    }


/*    public static void main(String[] args) {
        // Parse the JSON configuration file
        // ...

        // Create DataMapper instance
        DataMapper dataMapper = new DataMapper(datasources, actions);

        // Perform data mapping
        dataMapper.mapData();
    }*/
}


