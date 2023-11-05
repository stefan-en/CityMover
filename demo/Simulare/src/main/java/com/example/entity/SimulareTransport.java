package com.example.entity;

import com.example.data.DataAgregate;
import com.example.services.RuteService;
import com.example.services.StatieService;
import com.example.services.VehiculTransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
@RestController
@RequiredArgsConstructor
@Slf4j
public class SimulareTransport {
    private final List<Calator> calatori;
    private final List<DataAgregate> autovehicule;
     StringBuilder stringBuilder;


    public SimulareTransport() {
        this.calatori = new ArrayList<>();
        this.autovehicule = new ArrayList<>();


    }
    public SimulareTransport( List<DataAgregate> agregateList) {

        this.autovehicule = agregateList;
        this.calatori = new ArrayList<>();
        this.stringBuilder = new StringBuilder();

    }




    public void addPassenger(Calator calator) {
        calatori.add(calator);
        stringBuilder.append("Pasagerul " + calator.getId() + " merge din " + calator.getSourceStation() + " catre " + calator.getDestinationStation()+ "\n");
    }

    public List<String> getStationsForRoute(List<DataAgregate> dataAgregates, String numeTraseu) {
           List<String> lisy = new ArrayList<>();
        for(int i = 0; i < dataAgregates.size(); i++){
            if (dataAgregates.get(i).getNumeTraseu().equals(numeTraseu)) {
                lisy = dataAgregates.get(i).getStatii();
            }
        }
        return lisy;// Return an empty list if the route is not found
    }
    public static List<String> getRouteNames(List<DataAgregate> dataAgregatesList) {
        List<String> routeNames = new ArrayList<>();

        for (DataAgregate dataAgregate : dataAgregatesList) {
            String routeName = dataAgregate.getNumeTraseu();
            if (routeName != null && !routeName.isEmpty()) {
                routeNames.add(routeName);
            }
        }

        return routeNames;
    }

    public String simulate() {
        Random random = new Random();
        int numarCalatori = random.nextInt(20,50);
        int values;
        for (int i = 0; i < numarCalatori; i++) {
            List<String> rute = getRouteNames(autovehicule);
            int value = random.nextInt(rute.size());

            String randomRuta = rute.get(value);
            List<String> listaStatii = getStationsForRoute(autovehicule, randomRuta);
            value = random.nextInt(listaStatii.size());
            String sourceStation = listaStatii.get(value);
            String destinationStation = sourceStation;
            while (sourceStation.equals(destinationStation)) {
                values = random.nextInt(listaStatii.size());
                destinationStation = listaStatii.get(values);
            }
            Calator calator = new Calator("Passenger " + (i + 1), sourceStation, destinationStation);
            addPassenger(calator);
        }
        StringBuilder stringBuilder = new StringBuilder();
        List<Calator> pasageriInAsteptare = new ArrayList<>();
        List<Calator> pasageriImbarcati = new ArrayList<>();
        List<Calator> pasageriCoborati = new ArrayList<>();

        for (DataAgregate bus : autovehicule) {
            List<String> route = bus.getStatii();

            pasageriInAsteptare.clear();
            pasageriImbarcati.clear();

            for (int i = 0; i < route.size(); i++) {
                String currentStation = route.get(i);
                stringBuilder.append("Vehiculul de pe ruta ")
                        .append(bus.getNumeTraseu())
                        .append(" cu numarul traseului: ")
                        .append(bus.getNumarTraseu())
                        .append(" a pornit din statia ")
                        .append(currentStation)
                        .append("\n");

                for (Calator passenger : calatori) {
                    if (passenger.getSourceStation().equals(currentStation)) {
                        boolean passengerImbarcat = false;

                        for (int j = i + 1; j < route.size(); j++) {
                            if (passenger.getDestinationStation().equals(route.get(j))) {
                                pasageriImbarcati.add(passenger);
                                passengerImbarcat = true;
                                break;
                            } else {
                                pasageriInAsteptare.add(passenger);
                            }
                        }

                        if (passengerImbarcat) {
                            stringBuilder.append("Pasagerul: ")
                                    .append(passenger.getId())
                                    .append(" a urcat in vehiculul de pe ruta: ")
                                    .append(bus.getNumeTraseu())
                                    .append(" cu numarul traseului:")
                                    .append(bus.getNumarTraseu())
                                    .append(" din statia ")
                                    .append(passenger.getSourceStation())
                                    .append("\n");
                        }
                    }
                }

                calatori.removeAll(pasageriInAsteptare);
                calatori.removeAll(pasageriImbarcati);

                for (Calator pasagerCoborat : calatori) {
                    if (pasagerCoborat.getDestinationStation().equals(currentStation)) {
                        stringBuilder.append("Pasagerul: ")
                                .append(pasagerCoborat.getId())
                                .append(" a coborat in vehiculul de pe ruta: ")
                                .append(bus.getNumeTraseu())
                                .append(" cu numarul traseului: ")
                                .append(bus.getNumarTraseu())
                                .append(" in statia ")
                                .append(currentStation)
                                .append("\n");
                        pasageriCoborati.add(pasagerCoborat);
                    }
                }

                calatori.removeAll(pasageriCoborati);
            }
        }



        return stringBuilder.toString();

    }
}





