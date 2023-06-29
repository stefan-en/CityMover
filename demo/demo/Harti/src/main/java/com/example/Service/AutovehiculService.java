package com.example.Service;

import com.example.Entity.Autovehicul;
import com.example.Entity.Enum.AutoType;
import com.example.Interface.AutovehiculInterface;
import com.example.Interface.AutovehiculRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AutovehiculService implements AutovehiculInterface {
    private final AutovehiculRepository autovehiculRepository;
    @Override
    public Optional<Autovehicul> findByNumarTraseu(String numarTraseu){
        return autovehiculRepository.findByNumarTraseu(numarTraseu);
    }

    @Override
    public List<Autovehicul> getAutovehiculByType(AutoType type) {
        switch (type){
            case AUTOBUZ -> {
                return autovehiculRepository.findByTip(AutoType.AUTOBUZ);
            }
            case TRAMVAI ->{
                return autovehiculRepository.findByTip(AutoType.TRAMVAI);
            }
            default -> {
                return new ArrayList<>();
            }
        }

    }

    @Override
    public Autovehicul saveAutovehicul(Autovehicul autovehicul) {
        autovehicul.setNumeTraseu(autovehicul.getNumeTraseu());
        autovehicul.setNumarTraseu(autovehicul.getNumarTraseu());
        autovehicul.setStatieInitiala(autovehicul.getStatieInitiala());
        autovehicul.setStatieFinala(autovehicul.getStatieFinala());
        autovehicul.setTip(autovehicul.getTip());

        return autovehiculRepository.save(autovehicul);
    }

    public boolean parsareJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Autovehicul internAuto = new Autovehicul();
        boolean salvat = true;
        try {
            JsonNode rootNode = mapper.readTree(json);

            for (JsonNode node : rootNode) {
                String numarTraseu = node.get("route_short_name").asText();
                String numeTraseu = node.get("route_long_name").asText();
                String[] routeLongNameWords = numeTraseu.split(" - ");
                String firstWord = routeLongNameWords[0];
                String lastWord = routeLongNameWords[routeLongNameWords.length - 1];
                int routeType = node.get("route_type").asInt();

                AutoType transportType = getTransportType(routeType);

                internAuto.setTip(transportType);
                internAuto.setNumeTraseu(numeTraseu);
                internAuto.setNumarTraseu(numarTraseu);
                internAuto.setStatieInitiala(firstWord);
                internAuto.setStatieFinala(lastWord);
                Autovehicul auto = mappedNameOfRoute(internAuto);
                log.info("Am salvat:{}",auto.getNumeTraseu());
                Autovehicul result = saveAutovehicul(auto);
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
    private static AutoType getTransportType(int routeType) {
        Map<Integer, AutoType> transportTypes = new HashMap<>();
        transportTypes.put(0, AutoType.TRAMVAI);
        transportTypes.put(3, AutoType.AUTOBUZ);

        return transportTypes.getOrDefault(routeType, AutoType.NECUNOSCUT);
    }
    private static Autovehicul mappedNameOfRoute(Autovehicul autovehicul){
        switch (autovehicul.getNumeTraseu()){
            case "101" -> {
                String numeTraseu = "Rond Targu Cucu - Dobrovat";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "102" ->{
                String numeTraseu = "Rond Targu Cucu - Aroneanu";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "104" -> {
                String numeTraseu = "Aroneanu - Rediu Aldei";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "202" ->{
                String numeTraseu = "Podu Ros - Paun";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "401" ->{
                String numeTraseu = "Tatarasi Sud - Rusenii Noi";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "501" ->{
                String numeTraseu = "Sala Polivalenta - Vorovesti";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "503" ->{
                String numeTraseu = "CUG I - Valea Adanca";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "601" ->{
                String numeTraseu = "Rond Targu Cucu - Popricani";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "701" ->{
                String numeTraseu = "Copou - Horlesti";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "702" ->{
                String numeTraseu = "Copou - Rediu";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "801" ->{
                String numeTraseu = "Rond Targu Cucu - Goruni via Tomesti";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }
            case "803" ->{
                String numeTraseu = "Rond Tutora - Opriseni via Tomesti";
                changeData(numeTraseu,autovehicul);
                return autovehicul;
            }

            default -> {
                return autovehicul;
            }
        }

    }
    private static void changeData(String nume, Autovehicul autovehicul){
        autovehicul.setNumeTraseu(nume);
        String[] routeLongNameWords = nume.split(" - ");
        autovehicul.setStatieInitiala(routeLongNameWords[0]);
        autovehicul.setStatieFinala(routeLongNameWords[routeLongNameWords.length - 1]);
    }
}
