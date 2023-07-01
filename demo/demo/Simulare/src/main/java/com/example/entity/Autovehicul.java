package com.example.entity;

import java.util.List;

public class Autovehicul {
    private final String id;
    private final List<Statie> route;

    public Autovehicul(String name, List<Statie> route) {
        this.id = name;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public List<Statie> getRoute() {
        return route;
    }
}
