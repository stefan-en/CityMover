package com.example.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "rute")
public class Ruta {
    Integer routeId;
    String numeTraseu;
    String numarTraseu;
}
