package com.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "reuniune")
public class Reuniune {

    String tripId;
    Integer stopId;
    Integer numarOrdine;
}
