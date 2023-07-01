package com.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulareTransport {
    private final List<Statie> statii;
    private final List<Calator> calatori;
    private final List<VehiculTransport> autovehicule;

    public SimulareTransport() {
        this.statii = new ArrayList<>();
        this.calatori = new ArrayList<>();
        this.autovehicule = new ArrayList<>();
    }

    public void addStation(Statie station) {
        statii.add(station);
    }

    public void addPassenger(Calator calator) {
        calatori.add(calator);
    }

    public void addBus(VehiculTransport bus) {
        autovehicule.add(bus);
    }

    public void simulate() {
        Random random = new Random();

        // Simulate passengers arriving at stations
        for (Statie station : statii) {
            int passengerCount = random.nextInt(5); // Random number of passengers
            for (int i = 0; i < passengerCount; i++) {
                Statie destinationStation = station;
                while (station == destinationStation) {
                    destinationStation = statii.get(random.nextInt(statii.size())); // Random destination station
                }
                Calator pasenger = new Calator("Passenger " + (i + 1), station, destinationStation);
                addPassenger(pasenger);
            }
        }

        // Simulate buses on routes
        for (VehiculTransport bus : autovehicule) {
            List<Statie> route = bus.getRoute();
            for (int i = 0; i < route.size(); i++) {
                Statie currentStation = route.get(i);
                System.out.println("Bus " + bus.getId() + " arrived at station " + currentStation.getName());

                // Load passengers going to the next station
                List<Calator> passengersToUnload = new ArrayList<>();
                for (Calator passenger : calatori) {
                    if (passenger.getSourceStation() == currentStation && passenger.getDestinationStation() == route.get(i + 1)) {
                        passengersToUnload.add(passenger);
                    }
                }
                for (Calator passenger : passengersToUnload) {
                    calatori.remove(passenger);
                    System.out.println("Passenger " + passenger.getId() + " unloaded from bus " + bus.getId());
                }

                // Load passengers waiting at the current station
                List<Calator> passengersToLoad = new ArrayList<>();
                for (Calator passenger : calatori) {
                    if (passenger.getSourceStation() == currentStation) {
                        passengersToLoad.add(passenger);
                    }
                }
                for (Calator passenger : passengersToLoad) {
                    calatori.remove(passenger);
                    System.out.println("Passenger " + passenger.getId() + " boarded bus " + bus.getId());
                }

                // Depart to the next station
                if (i < route.size() - 1) {
                    System.out.println("Bus " + bus.getId() + " departed to station " + route.get(i + 1).getName());
                }
            }
        }
    }
}
