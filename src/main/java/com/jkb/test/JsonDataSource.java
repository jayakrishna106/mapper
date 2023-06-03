package com.jkb.test;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JsonDataSource extends DataSource {
    private String json;

    public JsonDataSource(String json) {
        this.json = json;
    }

    @Override
    public String getValue(String path) {
        try {
            DocumentContext documentContext = JsonPath.parse(json);
            Object result = documentContext.read(path);

            if (result != null) {
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}


/**
 *  class JsonDataSource {
 *     private String filePath;
 *     private ObjectMapper objectMapper;
 *
 *     public JsonDataSource(String filePath) {
 *         this.filePath = filePath;
 *         this.objectMapper = new ObjectMapper();
 *     }
 *
 *     public String getValue(String jsonPath) {
 *         try {
 *             File file = new File(filePath);
 *             JsonNode rootNode = objectMapper.readTree(file);
 *             JsonNode valueNode = rootNode.at(jsonPath);
 *             if (valueNode.isValueNode()) {
 *                 return valueNode.asText();
 *             }
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *         }
 *         return null;
 *     }
 * }
 */