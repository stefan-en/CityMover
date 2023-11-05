package com.example.services;

import com.example.data.DataAgregate;
import com.example.data.VehiculRute;
import com.example.entity.SimulareTransport;

import com.example.entity.VehiculTransport;
import com.example.interfaces.VehiculTransportInterface;
import com.example.repository.FinalRepository;
import com.example.repository.VehiculTransportRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor

public class VehiculTransportService implements VehiculTransportInterface {

    private final VehiculTransportRepository vehiculTransportRepository;
    private final FinalRepository finalRepository;


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
        return getRouteNamesVerify(vehiculTransportRepository.getColectieRezultatWithStatii());
    }
    @Override
    public List<String> getStatii(String numeRuta){
        return parseJsonAndGetStatii(finalRepository.findStatiiByNumeTraseu(numeRuta).get(0));
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
    public static List<String> parseJsonAndGetStatii(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> statii = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode statiiNode = rootNode.get("statii");
            if (statiiNode != null && statiiNode.isArray()) {
                for (JsonNode node : statiiNode) {
                    statii.add(node.asText());
                }
            }
        } catch (Exception e) {
            log.info("eroare aici");
            // Tratează excepția în mod corespunzător, de exemplu, afișează un mesaj de eroare sau înregistrează eroarea într-un fișier de log.
            e.printStackTrace();
        }
        return statii;
    }

    public List<Map<String,String>> Simulare(){

        List<DataAgregate> agregateList = getColectieRezultatWithStatii();
        SimulareTransport simulare = new SimulareTransport(agregateList);

        return buildStringMap(simulare.simulate());


    }
    public static List<Map<String,String>> buildStringMap(String input) {
        List<Map<String,String>> resultMap = new ArrayList<>();

        String[] lines = input.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            String key = "mesaj:" + (i + 1);
            Map<String, String> map = new HashMap<>();
            map.put(key, line);

            resultMap.add(map);
        }
        return resultMap;
    }
    public List<DataAgregate> getRouteNamesVerify(List<DataAgregate> dataAgregatesList) {
        List<DataAgregate> routeNames = new ArrayList<>();

        for (DataAgregate dataAgregate : dataAgregatesList) {
            String routeName = dataAgregate.getNumeTraseu();
            List<String> st = dataAgregate.getStatii();
            String dr = dataAgregate.getNumarTraseu();
            if (!Objects.equals(routeName, null) && st!= null && dr != null){
                routeNames.add(dataAgregate);
            }
        }

        return routeNames;
    }

}

