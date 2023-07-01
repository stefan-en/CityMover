package com.example.services;

import com.example.entity.VehiculTransport;
import com.example.interfaces.VehiculTransportInterface;
import com.example.repository.VehiculTransportRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        log.info("Am salvat vehiculul cu id: {} pentru ruta {}.", vehiculTransport.getId(),vehiculTransport.getRoute());
        return vehiculTransportRepository.save(vehiculTransport);
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
                log.info("Am salvat vehiculul cu id: {} si trip_id-ul: {}",vehicul.getId(), vehicul.getTripId());

                if (result == null){
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
