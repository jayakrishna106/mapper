package com.jkb.test;

import java.util.List;

public abstract class DataSource {
    // Common fields and methods for data sources
    private String type;
    private String path;
    private List<Mapping> mappings;

    public DataSource(String type, String path, List<Mapping> mappings) {
        this.type = type;
        this.path = path;
        this.mappings = mappings;
    }

    public DataSource() {

    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    public abstract String getValue(String path);

}
