package com.example;

import com.example.Entity.TicketEntity;
import com.example.Service.TicketService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.Entity.Enum.TicketType.*;

@SpringBootApplication
public class TicketEntryPoint {
    public static void main(String[] args) {
        SpringApplication.run(EntryEndpoint.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(TicketService ticketService){
        return args -> {
            ticketService.saveTicket(new TicketEntity("uuiid--xx",ABONAMENT_STUDENT));
        };
    }
}