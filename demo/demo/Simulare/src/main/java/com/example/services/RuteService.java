package com.example.services;

import com.example.entity.Ruta;
import com.example.interfaces.RutaInterface;
import com.example.repository.RutaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class RuteService implements RutaInterface {

    private final RutaRepository ruteRepository;

    @Override
    public Ruta saveRuta(Ruta ruta) {
        ruta.setNumeTraseu(ruta.getNumeTraseu());
        ruta.setNumarTraseu(ruta.getNumarTraseu());
        ruta.setRouteId(ruta.getRouteId());

        log.info("Am salvat ruta cu id: {} si numele {}.", ruta.getRouteId(),ruta.getNumeTraseu());
        return ruteRepository.save(ruta);
    }


    public boolean parsareJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Ruta ruta = new Ruta();
        boolean salvat = true;
        try {
            JsonNode rootNode = mapper.readTree(json);

            for (JsonNode node : rootNode) {
                String numarTraseu = node.get("route_short_name").asText();
                String numeTraseu = node.get("route_long_name").asText();
                int routeId = node.get("route_id").asInt();

                ruta.setRouteId(routeId);
                ruta.setNumeTraseu(numeTraseu);
                ruta.setNumarTraseu(numarTraseu);

                Ruta result = saveRuta(ruta);


                if (result == null){
                    log.info("Alert!!!! Nu s-a salvat ruta cu numele:{}",ruta.getNumarTraseu());
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
