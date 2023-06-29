package com.example.Entity;


import com.example.Entity.Enum.AutoType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "autovehicule")

public class Autovehicul {

    String numeTraseu;
    String numarTraseu;
    String statieInitiala;
    String statieFinala;
    AutoType tip;

    public Autovehicul( String numeTraseu, String numarTraseu, String statieInitiala, String statieFinala, AutoType tip) {
        this.numeTraseu = numeTraseu;
        this.numarTraseu = numarTraseu;
        this.statieInitiala = statieInitiala;
        this.statieFinala = statieFinala;
        this.tip = tip;
    }
}

