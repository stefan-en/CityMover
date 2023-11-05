package com.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@Document(collection = "vehicule")

public class VehiculTransport {
    String id;//label

    String tripId;//trip_id
    Integer routeId;// rout_id
    List<Statie> route;

    public VehiculTransport(String id, String tripId, Integer routeId, List<Statie> route) {
        this.id = id;
        this.tripId = tripId;
        this.routeId = routeId;
        this.route = route;
    }
}
