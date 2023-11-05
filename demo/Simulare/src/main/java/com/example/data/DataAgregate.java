package com.example.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "final")
public class DataAgregate {
    String numeTraseu;
    String numarTraseu;
    List<String> statii;

    public String getNumarTraseu() {
        return this.numarTraseu;
    }

    public List<String> getStatii() {
        return this.statii;
    }
}
