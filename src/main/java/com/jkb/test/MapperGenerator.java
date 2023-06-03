package com.jkb.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperGenerator {

    public void main(String[] args) {
        List<FieldMapping> fieldMappings = new ArrayList<>();
        fieldMappings.add(new FieldMapping("JSON", "$.name", "XML/Name", "UPPERCASE"));
        fieldMappings.add(new FieldMapping("XML", "XML/Address/Street", "JSON/address_street", "LOWERCASE"));
        fieldMappings.add(new FieldMapping("JSON", "$.age", "XML/Age", null));

        List<Action> actions = new ArrayList<>();
        actions.add(new Action("UPPERCASE", "uppercase"));
        actions.add(new Action("LOWERCASE", "lowercase"));
        actions.add(new Action("JOIN", "join_fields"));

        List<TypeBasedMapping> typeBasedMappings = new ArrayList<>();
        typeBasedMappings.add(new TypeBasedMapping("JSON", "JSONPath"));
        typeBasedMappings.add(new TypeBasedMapping("XML", "XPATH"));

        MapperConfiguration mapperConfiguration = new MapperConfiguration(fieldMappings, actions, typeBasedMappings);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapperConfiguration);
            FileWriter fileWriter = new FileWriter("mapper.json");
            fileWriter.write(json);
            fileWriter.close();
            System.out.println("Mapper file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


class MapperConfiguration {
    private List<FieldMapping> fieldMappings;
    private List<Action> actions;
    private List<TypeBasedMapping> typeBasedMappings;

    public MapperConfiguration(List<FieldMapping> fieldMappings, List<Action> actions, List<TypeBasedMapping> typeBasedMappings) {
        this.fieldMappings = fieldMappings;
        this.actions = actions;
        this.typeBasedMappings = typeBasedMappings;
    }

    public List<FieldMapping> getFieldMappings() {
        return fieldMappings;
    }

    public void setFieldMappings(List<FieldMapping> fieldMappings) {
        this.fieldMappings = fieldMappings;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<TypeBasedMapping> getTypeBasedMappings() {
        return typeBasedMappings;
    }

    public void setTypeBasedMappings(List<TypeBasedMapping> typeBasedMappings) {
        this.typeBasedMappings = typeBasedMappings;
    }
}

class FieldMapping {
    private String type;
    private String source;
    private String target;
    private String action;

    public FieldMapping(String type, String source, String target, String action) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

class Action {
    private String name;
    private String methodName;

    public Action(String name, String methodName) {
        this.name = name;
        this.methodName = methodName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}

class TypeBasedMapping {
    private String type;
    private String method;

    public TypeBasedMapping(String type, String method) {
        this.type = type;
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
}
