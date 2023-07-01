package com.example.controller;

import com.example.services.RuteService;
import com.example.services.VehiculTransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
@Slf4j
public class SimulareController {
    private final VehiculTransportService vehiculTransportService;
    private final RuteService ruteService;

    @PostMapping("/simulare/autovehicule")
    public ResponseEntity<String> putVehicule(@RequestBody String date){
        boolean result = vehiculTransportService.parsareJson(date);
        if(result) {
            log.info("Date parsate");
            return ResponseEntity.status(HttpStatus.OK).build();

        }else{
            log.info("Date eronate");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/simulare/rute")
    public ResponseEntity<String> putRute(@RequestBody String date){
        boolean result = ruteService.parsareJson(date);
        if(result) {
            log.info("Date parsate");
            return ResponseEntity.status(HttpStatus.OK).build();

        }else{
            log.info("Date eronate");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
