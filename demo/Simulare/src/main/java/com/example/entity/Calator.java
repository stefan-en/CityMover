package com.example.entity;

import java.util.List;

public class Calator {
    private final String id;

    private final String sourceStation;

    private final String destinationStation;

    public Calator(String name, String sourceStation, String destinationStation) {
        this.id = name;

        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public String getId() {
        return id;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }
}
