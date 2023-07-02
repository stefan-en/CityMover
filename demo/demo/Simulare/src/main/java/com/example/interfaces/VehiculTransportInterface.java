package com.example.interfaces;

import com.example.data.DataAgregate;
import com.example.data.VehiculRute;
import com.example.entity.VehiculTransport;

import java.util.List;

public interface VehiculTransportInterface {
    VehiculTransport saveVehiculTransport(VehiculTransport vehiculTransport);

    List<VehiculRute> getVehiculeWithRute();

    List<DataAgregate> getColectieRezultatWithStatii();
}
