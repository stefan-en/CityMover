package com.example.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vehiculrute")
public class VehiculRute {
    String tripId;
    String routeId;
    String numeTraseu;
    String numarTraseu;
}
