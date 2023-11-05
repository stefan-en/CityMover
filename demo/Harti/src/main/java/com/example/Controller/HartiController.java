package com.example.Controller;

import com.example.DTO.AutovehiculDTO;
import com.example.Entity.Autovehicul;
import com.example.Entity.Enum.AutoType;
import com.example.Service.AutovehiculService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
@Slf4j
public class HartiController {
    private final AutovehiculService autovehiculService;

    @GetMapping("/harti/autovehicule/tramvaie")
    public ResponseEntity<List<AutovehiculDTO>> getTrmavaie() {
        ModelMapper modelMapper = new ModelMapper();
        List<Autovehicul> ticketEntityList = null;
        ticketEntityList = autovehiculService.getAutovehiculByType(AutoType.TRAMVAI);

        List<AutovehiculDTO> mappedAuto = ticketEntityList.stream().map(ticket -> modelMapper.map(ticket, AutovehiculDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(mappedAuto);
    }
    @GetMapping("/harti/autovehicule/autobuze")
    public ResponseEntity<List<AutovehiculDTO>> getAutobuze() {
        ModelMapper modelMapper = new ModelMapper();
        List<Autovehicul> ticketEntityList = null;
        ticketEntityList = autovehiculService.getAutovehiculByType(AutoType.AUTOBUZ);

        List<AutovehiculDTO> mappedAuto = ticketEntityList.stream().map(ticket -> modelMapper.map(ticket, AutovehiculDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(mappedAuto);
    }
    @PostMapping("/harti/autovehicule")
    public ResponseEntity<String> saveDataAuto(@RequestBody String date){
        boolean result = autovehiculService.parsareJson(date);
        if(result) {
            log.info("Date parsate");
            return ResponseEntity.status(HttpStatus.OK).build();

        }else{
            log.info("Date eronate");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
