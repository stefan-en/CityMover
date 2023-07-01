package com.example.services;


import com.example.entity.Reuniune;
import com.example.interfaces.ReuniuneInterface;
import com.example.repository.ReuniuneRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReunuineService implements ReuniuneInterface {

    private final ReuniuneRepository reuniuneRepository;
    @Override
    public Reuniune saveReuniune(Reuniune reuniune) {
        reuniune.setNumarOrdine(reuniune.getNumarOrdine());
        reuniune.setTripId(reuniune.getTripId());
        reuniune.setStopId(reuniune.getStopId());

        log.info("Am salvat trip_id: {}.", reuniune.getTripId());
        return reuniuneRepository.save(reuniune);
    }

    public boolean parsareJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Reuniune reuniune = new Reuniune();
        boolean salvat = true;
        try {
            JsonNode rootNode = mapper.readTree(json);

            for (JsonNode node : rootNode) {
                String trip = node.get("trip_id").asText();
                int stopId = node.get("stop_id").asInt();
                int ordine = node.get("stop_sequence").asInt();

                reuniune.setTripId(trip);
                reuniune.setNumarOrdine(ordine);
                reuniune.setStopId(stopId);
                Reuniune result = saveReuniune(reuniune);
                log.info("Am salvat trip_id: {} in colectia reuniune .", reuniune.getTripId());

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
