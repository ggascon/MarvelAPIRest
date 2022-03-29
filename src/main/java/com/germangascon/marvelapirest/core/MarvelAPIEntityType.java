package com.germangascon.marvelapirest.core;

public enum MarvelAPIEntityType {
    CHARACTERS("characters"), COMICS("comics"), CREATORS("creators"),
    EVENTS("events"), SERIES("series"), STORIES("stories");
    private final String entityName;

    MarvelAPIEntityType(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return entityName;
    }
}
