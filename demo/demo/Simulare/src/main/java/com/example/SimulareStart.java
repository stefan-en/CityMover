package com.example;

import com.example.data.DataAgregate;
import com.example.entity.Ruta;
import com.example.entity.SimulareTransport;
import com.example.entity.Statie;
import com.example.interfaces.StatieInterface;
import com.example.repository.VehiculTransportRepository;
import com.example.services.RuteService;
import com.example.services.StatieService;
import com.example.services.VehiculTransportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableMongoRepositories
public class SimulareStart {
    public static void main(String[] args)  {
        SpringApplication.run(SimulareStart.class, args);

    }
    @Bean
    CommandLineRunner simulateTransport(StatieService statieService, VehiculTransportService transportService) {
        return args -> {
            List<Statie> listaStatii = statieService.getStatii();
            List<DataAgregate> agregateList = transportService.getColectieRezultatWithStatii();

            // Creăm un ExecutorService pentru a gestiona thread-urile
            ExecutorService executorService = Executors.newFixedThreadPool(3); // 3 thread-uri pentru 3 vehicule de transport

            List<DataAgregate> currentChunk1 = agregateList.subList(0, 20);
            List<DataAgregate> currentChunk2 = agregateList.subList(21, 40);
            List<DataAgregate> currentChunk3 = agregateList.subList(41, 65);

            SimulareTransport simulare = new SimulareTransport(listaStatii, currentChunk1);
            SimulareTransport simulare2 = new SimulareTransport(listaStatii, currentChunk2);
            SimulareTransport simulare3 = new SimulareTransport(listaStatii, currentChunk3);

            // Creăm thread-urile pentru simularea transportului
            executorService.submit(simulare::simulate);

            executorService.submit(simulare2::simulate);

            executorService.submit(simulare3::simulate);

            // Așteptăm ca toate thread-urile să se încheie
            executorService.shutdown();
        };
    }
}