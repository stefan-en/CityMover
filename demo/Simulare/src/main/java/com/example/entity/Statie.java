package com.example.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "statii")
public class Statie {
    String name;
    Integer idStatie;
    double latitude;
    double longitude;

    public Statie(String name, Integer idStatie, double latitude, double longitude) {
        this.name = name;
        this.idStatie = idStatie;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
