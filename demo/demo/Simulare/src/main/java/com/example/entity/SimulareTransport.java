package com.example.entity;

import com.example.data.DataAgregate;
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
    private final List<Statie> statii;
    private final List<Calator> calatori;
    private final List<DataAgregate> autovehicule;
    private int totalPasageriTransportati;

    private StatieService statieService;
    private VehiculTransportService transportService;

    public SimulareTransport() {
        this.statii = new ArrayList<>();
        this.calatori = new ArrayList<>();
        this.autovehicule = new ArrayList<>();
    }

    public SimulareTransport(List<Statie> listaStatii, List<DataAgregate> agregateList) {
        this.statii = listaStatii;
        this.calatori = new ArrayList<>();
        this.autovehicule = agregateList;
        this.totalPasageriTransportati = 0;
    }

    public void addStation() {
        List<Statie> listaStatii = statieService.getStatii();
        statii.addAll(listaStatii);
    }
    public void addTransportData(){
        List<DataAgregate> agregateList = transportService.getColectieRezultatWithStatii();
        autovehicule.addAll(agregateList);
    }
    private double calculateOcupareVehicul(int locuriOcupate) {
        return (double) locuriOcupate / 60;
    }

    public void showStatistici() {
        int totalLocuriAutovehicule = 0;
        int totalLocuriOcupate = 0;
        for (DataAgregate bus : autovehicule) {
            totalLocuriAutovehicule += 60; // Presupunem că toate autovehiculele au 60 de locuri
            int locuriOcupate = 60 - calatori.size(); // Calculăm locurile ocupate pentru fiecare vehicul
            totalLocuriOcupate += locuriOcupate;
            System.out.println("Autovehiculul cu traseul " + bus.getNumarTraseu() + " are grad de ocupare: " + calculateOcupareVehicul(locuriOcupate));
        }

        System.out.println("Numarul total de pasageri transportati: " + totalPasageriTransportati);
        System.out.println("Gradul de ocupare mediu al autovehiculelor: " + calculateOcupareVehicul(totalLocuriOcupate));
    }

    public void addPassenger(Calator calator) {
        calatori.add(calator);
    }


    public void simulate() {
        Random random = new Random();
        int numarCalatori = random.nextInt(10000);

        for (int i = 0; i < numarCalatori; i++) { // Generăm călători
            Statie sourceStation = statii.get(random.nextInt(statii.size()));
            Statie destinationStation = sourceStation;
            while (sourceStation == destinationStation) {
                destinationStation = statii.get(random.nextInt(statii.size())); // Destinație diferită de stația sursă
            }
            Calator calator = new Calator("Passenger " + (i + 1), sourceStation, destinationStation);
            addPassenger(calator);
        }

        for (DataAgregate bus : autovehicule) {
            List<String> route = bus.getStatii();

            for (int i = 0; i < route.size(); i++) {
                String currentStation = route.get(i);
               // System.out.println("Vehiculul de pe ruta " + bus.getNumeTraseu() + " cu numarul traseului: " + bus.getNumarTraseu() + " a pornit din statia " + currentStation);

                List<Calator> passengersToUnload = new ArrayList<>();
                List<Calator> passengersToLoad = new ArrayList<>();

                // Verificăm dacă autobuzul ajunge la stația dorită de călători și dacă aceștia ajung la destinația lor
                for (Calator passenger : calatori) {
                    if (passenger.getSourceStation().getName().equals(currentStation)) {
                        int nextStationIndex = i + 1;
                        if (nextStationIndex < route.size() && passenger.getDestinationStation().getName().equals(route.get(nextStationIndex))) {
                            passengersToUnload.add(passenger);
                        } else if (nextStationIndex >= route.size()) {
                            System.out.println("Atenție! Pasagerul " + passenger.getId() + " a rămas în vehiculul de pe ruta " + bus.getNumeTraseu() + " cu numărul traseului " + bus.getNumarTraseu() + " deoarece stația sa de destinație nu există în ruta vehiculului.");
                        } else {
                            passengersToLoad.add(passenger);
                        }
                    }
                }

                for (Calator passenger : passengersToUnload) {
                    calatori.remove(passenger);
                    System.out.println("Pasagerul: " + passenger.getId() + " a coborat din vehiculul de pe ruta: " + bus.getNumeTraseu() + " cu numarul traseului:"  + bus.getNumarTraseu() + " in statia " + passenger.getDestinationStation().getName());
                }

                for (Calator passenger : passengersToLoad) {
                    calatori.remove(passenger);
                    System.out.println("Pasagerul: " + passenger.getId() + " a urcat in vehiculul de pe ruta: " + bus.getNumeTraseu() + " cu numarul traseului:" + bus.getNumarTraseu() + " din statia " + passenger.getSourceStation().getName() + " cu destinatia: " + passenger.getDestinationStation().getName());
                }

                if (i < route.size() - 1) {
                    System.out.println("Vehiculul de pe ruta: " + bus.getNumeTraseu() + " cu numarul traseului: "+ bus.getNumarTraseu() + " a plecat catre statia " + route.get(i + 1));
                }
            }
        }
        totalPasageriTransportati = calatori.size(); // Actualizăm numărul total de pasageri transportați
        showStatistici(); // Afișăm statistici la finalul simulării

        try {
            Thread.sleep(1000); // Delay de 1 secundă între iterații pentru a simula trecerea timpului
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
