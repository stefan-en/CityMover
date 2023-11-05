package com.example.services;

import com.example.entity.Ruta;
import com.example.interfaces.RutaInterface;
import com.example.repository.RutaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    public List<String>  getRute(){
        return extractName(ruteRepository.findAllByNumeTraseuNotNull());
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
    public List<String> extractName(List<String> documents) {
        List<String> routeNames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String document : documents) {
            try {
                JsonNode jsonNode = objectMapper.readTree(document);
                String numeTraseu = jsonNode.get("numeTraseu").asText();
                if (numeTraseu.equals( "3 e") || numeTraseu.equals("")){
                continue;
                }
                else {
                    routeNames.add(mappedNameOfRoute(numeTraseu));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return routeNames;
    }
    private static String mappedNameOfRoute(String ruta){
        switch (ruta){
            case "101" -> {
                return  "Rond Targu Cucu - Dobrovat";
            }
            case "102" ->{
                return"Rond Targu Cucu - Aroneanu";
            }
            case "104" -> {
                return "Aroneanu - Rediu Aldei";

            }
            case "202" ->{
                return"Podu Ros - Paun";
            }
            case "401" ->{
                return"Tatarasi Sud - Rusenii Noi";
            }
            case "501" ->{
                return"Sala Polivalenta - Vorovesti";
            }
            case "503" ->{
                return "CUG I - Valea Adanca";
            }
            case "601" ->{
                return  "Rond Targu Cucu - Popricani";
            }
            case "701" ->{
                return "Copou - Horlesti";
            }
            case "702" ->{
                return  "Copou - Rediu";

            }
            case "801" ->{
                return  "Rond Targu Cucu - Goruni via Tomesti";

            }
            case "803" ->{
               return "Rond Tutora - Opriseni via Tomesti";
            }
            case "9b" ->{
                return "Tg. Cucu - Podu Roș - CUG 2";
            }
            case "3 e" -> {
                return  "";

            }

            default -> {
                return ruta;
            }
        }
    }
}
