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

        List<AutovehiculDTO> mappedAuto = ticketEntityList.stream().map(ticket -> modelMapper.map(ticket, AutovehiculDTO.class)).toList();
        return ResponseEntity.ok().body(mappedAuto);
    }
    @GetMapping("/harti/autovehicule/autobuze")
    public ResponseEntity<List<AutovehiculDTO>> getAutobuze() {
        ModelMapper modelMapper = new ModelMapper();
        List<Autovehicul> ticketEntityList = null;
        ticketEntityList = autovehiculService.getAutovehiculByType(AutoType.AUTOBUZ);

        List<AutovehiculDTO> mappedAuto = ticketEntityList.stream().map(ticket -> modelMapper.map(ticket, AutovehiculDTO.class)).toList();
        return ResponseEntity.ok().body(mappedAuto);
    }
    @PostMapping("/harti/autovehicule")
    public ResponseEntity<String> saveDataAuto(@RequestBody String date){
        boolean result = autovehiculService.parsareJson(date);
        if(result) {
            return ResponseEntity.ok().body("Datele au fost parsate");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datele nu s-au procesat");
        }
    }
}
