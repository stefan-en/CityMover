package com.example;

import com.example.Entity.Autovehicul;
import com.example.Entity.Enum.AutoType;
import com.example.Interface.AutovehiculRepository;
import com.example.Service.AutovehiculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class StartHarti{
    public static void main(String[] args)  {
        SpringApplication.run(StartHarti.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunnerHarti(AutovehiculService autovehiculService){
        return args -> {
            autovehiculService.saveAutovehicul(new Autovehicul("13","Copou-Tudor","Copou", "Tudor", AutoType.TRAMVAI));
//            autovehiculRepository.save(new Autovehicul("13","Copou-Tudor","Copou", "Tudor", AutoType.TRAMVAI));
        };
    }

//    @Override
//    public void run(String... args) throws Exception {
//        autovehiculRepository.save(new Autovehicul("13","Copou-Tudor","Copou", "Tudor", AutoType.TRAMVAI));
//    }
}