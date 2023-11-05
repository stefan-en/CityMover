package com.example.interfaces;

import com.example.data.ReuniuneStatii;
import com.example.entity.Reuniune;

import java.util.List;

public interface ReuniuneInterface {
    Reuniune saveReuniune(Reuniune reuniune);

    List<ReuniuneStatii> getStatiiForTrip();
}
