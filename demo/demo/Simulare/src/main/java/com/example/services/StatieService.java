package com.example.services;

import com.example.entity.Statie;
import com.example.interfaces.StatieInterface;
import com.example.repository.StatieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class StatieService implements StatieInterface {

    private final StatieRepository statieRepository;

    @Override
    public Statie saveStatie(Statie statie) {
        statie.setIdStatie(statie.getIdStatie());
        statie.setName(statie.getName());
        statie.setLatitude(statie.getLatitude());
        statie.setLongitude(statie.getLongitude());

        log.info("Am salvat statia cu numele: {} si id {}.", statie.getName(),statie.getIdStatie());
        return statieRepository.save(statie);
    }
    @Override
    public List<Statie> getStatii(){
        return statieRepository.findAll();
    }

    public boolean parsareJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Statie statie = new Statie();
        boolean salvat = true;
        try {
            JsonNode rootNode = mapper.readTree(json);

            for (JsonNode node : rootNode) {
                Integer stopId = node.get("stop_id").asInt();
                String numeTraseu = node.get("stop_name").asText();
                double lat = node.get("stop_lat").asDouble();
                double lon = node.get("stop_lon").asDouble();

                statie.setLongitude(lon);
                statie.setIdStatie(stopId);
                statie.setLatitude(lat);
                statie.setName(numeTraseu);


                Statie result = saveStatie(statie);

                if (result == null){
                    log.info("Aleeert !!! Am salvat statia cu numele: {} .", statie.getName());
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
