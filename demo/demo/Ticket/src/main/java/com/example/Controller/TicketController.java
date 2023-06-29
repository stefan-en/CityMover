package com.example.Controller;

import com.example.DTO.TicketEntityDTO;
import com.example.Entity.TicketEntity;
import com.example.Service.TicketService;
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
public class TicketController {
    private final TicketService ticketService;
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketEntityDTO>> getTickets(){
        ModelMapper modelMapper = new ModelMapper();
        List<TicketEntity> ticketEntityList = null;
        ticketEntityList = ticketService.getAllTickets();

        List<TicketEntityDTO> mappedTickets  = ticketEntityList.stream().map(ticket -> modelMapper.map(ticket, TicketEntityDTO.class)).toList();
        return ResponseEntity.ok().body(mappedTickets);
    }
    @GetMapping("/tickets/history/{user}")
    public ResponseEntity<List<TicketEntityDTO>> getHistory(@PathVariable("user") String id){
        ModelMapper modelMapper = new ModelMapper();
        List<TicketEntity> ticketEntityList = null;
        ticketEntityList = ticketService.getMax5Tickets(id);

        List<TicketEntityDTO> mappedTickets  = ticketEntityList.stream().map(ticket -> modelMapper.map(ticket, TicketEntityDTO.class)).toList();
        return ResponseEntity.ok().body(mappedTickets);
    }
    @PostMapping("/tickets/buy")
    public ResponseEntity<String> buyTicket(@RequestBody TicketEntity ticket){
        ticketService.saveTicket(ticket);
        if(ticketService.saveTicket(ticket) != null ) {
            return ResponseEntity.ok().body("The ticket are deliver in you bucket");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Try again to buy");
        }
    }
}
