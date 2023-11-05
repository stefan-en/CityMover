package com.example.controller;

import com.example.data.DataAgregate;
import com.example.data.ReuniuneStatii;
import com.example.data.VehiculRute;
import com.example.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
@Slf4j
public class SimulareController {
    private final VehiculTransportService vehiculTransportService;
    private final RuteService ruteService;
    private final StatieService statieService;
    private final ReunuineService reunuineService;

    // parsare si inserate in colectia 'autovehicle'
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
    // parsare si inserate in colectia 'rute'
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

    // parsare si inserate in colectia 'statii'
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
    // parsare si inserate in colectia 'reuniune'
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
    // extragere date din colectia 'reuniunestatii'
    @GetMapping("/simulare/autoRute")
    public ResponseEntity<List<VehiculRute>> getFirs(){
        List<VehiculRute> list = vehiculTransportService.getVehiculeWithRute();
        return ResponseEntity.ok().body(list);
    }
    // extragere date din colectia 'vehiculrute'
    @GetMapping("/simulare/reuniuneStatii")
    public ResponseEntity<List<ReuniuneStatii>> getListStatii(){
        List<ReuniuneStatii> list = reunuineService.getStatiiForTrip();
        return ResponseEntity.ok().body(list);
    }
    //datele final procesate in colectia 'final'
    @GetMapping("/simulare/agregate")
    public ResponseEntity<List<DataAgregate>> getLisData(){
        List<DataAgregate> list = vehiculTransportService.getColectieRezultatWithStatii();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/simulare/start")
    public ResponseEntity<List<Map<String,String>>> getDataSimulate(){
        List<Map<String,String>> data = vehiculTransportService.Simulare();
        return ResponseEntity.ok().body(data);
    }



}
