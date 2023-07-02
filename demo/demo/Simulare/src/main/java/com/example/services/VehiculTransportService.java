package com.example.services;

import com.example.data.DataAgregate;
import com.example.data.VehiculRute;
import com.example.entity.VehiculTransport;
import com.example.interfaces.VehiculTransportInterface;
import com.example.repository.VehiculTransportRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class VehiculTransportService implements VehiculTransportInterface {

    private final VehiculTransportRepository vehiculTransportRepository;

    @Override
    public VehiculTransport  saveVehiculTransport(VehiculTransport vehiculTransport) {
        vehiculTransport.setId(vehiculTransport.getId());
        vehiculTransport.setRouteId(vehiculTransport.getRouteId());
        vehiculTransport.setTripId(vehiculTransport.getTripId());

        log.info("Am salvat vehiculul cu id: {} pentru ruta {}.", vehiculTransport.getId(),vehiculTransport.getTripId());
        return vehiculTransportRepository.save(vehiculTransport);
    }
    @Override
    public List<VehiculRute>  getVehiculeWithRute(){
        List<VehiculRute> re = vehiculTransportRepository.getVehiculeWithRute();
        for(VehiculRute i : re)
            System.out.println(i);
        return vehiculTransportRepository.getVehiculeWithRute();
    }

    @Override
    public List<DataAgregate> getColectieRezultatWithStatii(){
        return vehiculTransportRepository.getColectieRezultatWithStatii();
    }


    public boolean parsareJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        VehiculTransport vehicul = new VehiculTransport();
        boolean salvat = true;
        try {
            JsonNode rootNode = mapper.readTree(json);

            for (JsonNode node : rootNode) {
                String label = node.get("label").asText();
                int routeId = node.get("route_id").asInt();
                String tripId = node.get("trip_id").asText();

                vehicul.setId(label);
                vehicul.setRouteId(routeId);
                vehicul.setTripId(tripId);
                VehiculTransport result = saveVehiculTransport(vehicul);

                if (result == null){
                    log.info("Alert!!!!  Nu s-a salvat vehiculul cu id: {}.",vehicul.getId());

                    salvat = false;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!salvat)
        {
            return salvat;
        }
        else{
            return true;
        }
    }
}
