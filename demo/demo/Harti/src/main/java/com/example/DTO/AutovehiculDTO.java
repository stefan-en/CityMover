package com.example.DTO;

import com.example.Entity.Enum.AutoType;
import lombok.Data;

@Data
public class AutovehiculDTO {
    String numeTraseu;
    String numarTraseu;
    String statieInitiala;
    String statieFinala;
    AutoType tip;
}
