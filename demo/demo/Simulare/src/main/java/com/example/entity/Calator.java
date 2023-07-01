package com.example.entity;

public class Calator {
    private final String id;
    private final Statie sourceStation;
    private final Statie destinationStation;

    public Calator(String name, Statie sourceStation, Statie destinationStation) {
        this.id = name;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public String getId() {
        return id;
    }

    public Statie getSourceStation() {
        return sourceStation;
    }

    public Statie getDestinationStation() {
        return destinationStation;
    }
}
