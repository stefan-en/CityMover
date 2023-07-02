package com.example.controller;

import com.example.data.DataAgregate;
import com.example.data.ReuniuneStatii;
import com.example.data.VehiculRute;
import com.example.entity.VehiculTransport;
import com.example.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
@Slf4j
public class SimulareController {
    private final VehiculTransportService vehiculTransportService;
    private final RuteService ruteService;
    private final StatieService statieService;
    private final ReunuineService reunuineService;

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
    @PostMapping("/simulare/statii")
    public ResponseEntity<String> putStatii(@RequestBody String date){
        boolean result = statieService.parsareJson(date);
        if(result) {
            log.info("Date parsate");
            return ResponseEntity.status(HttpStatus.OK).build();

        }else{
            log.info("Date eronate");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/simulare/reuniune")
    public ResponseEntity<String> putReuniune(@RequestBody String date){
        boolean result = reunuineService.parsareJson(date);
        if(result) {
            log.info("Date parsate");
            return ResponseEntity.status(HttpStatus.OK).build();

        }else{
            log.info("Date eronate");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/simulare/autoRute")
    public ResponseEntity<List<VehiculRute>> getFirs(){
        List<VehiculRute> list = vehiculTransportService.getVehiculeWithRute();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/simulare/reuniuneStatii")
    public ResponseEntity<List<ReuniuneStatii>> getListStatii(){
        List<ReuniuneStatii> list = reunuineService.getStatiiForTrip();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/simulare/agregate")
    public ResponseEntity<List<DataAgregate>> getLisData(){
        List<DataAgregate> list = vehiculTransportService.getColectieRezultatWithStatii();
        return ResponseEntity.ok().body(list);
    }



}
