package com.jkb.test;

import java.util.List;

public class Mapping {
    private String targetField;
    private String sourceField;
    private String sourcePath;

    private String dataType;
    private List<Action> actions;

    private String sourceType;

    public Mapping(String targetField, String sourceField, String sourcePath, String dataType, List<Action> actions, String sourceType) {
        this.targetField = targetField;
        this.sourceField = sourceField;
        this.sourcePath = sourcePath;
        this.dataType = dataType;
        this.actions = actions;
        this.sourceType = sourceType;
    }

    public String getTargetField() {
        return targetField;
    }

    public String getSourceField() {
        return sourceField;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getDataType() {
        return dataType;
    }

    public List<Action> getActions() {
        return actions;
    }
    public String getSourceType(){
        return sourceType;
    }
}
