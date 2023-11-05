package com.example;

import com.example.data.DataAgregate;
import com.example.entity.SimulareTransport;
import com.example.entity.Statie;
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

}