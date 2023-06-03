package com.jkb.test;

public class Action {
    private String type;
    private String search;
    private String replacement;
    private String delimiter;
    private int start;
    private int end;

    public Action(String type, String search, String replacement, String delimiter, int start, int end) {
        this.type = type;
        this.search = search;
        this.replacement = replacement;
        this.delimiter = delimiter;
        this.start = start;
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public String getSearch() {
        return search;
    }

    public String getReplacement() {
        return replacement;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

