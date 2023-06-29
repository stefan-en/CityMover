package com.example.Service;

import com.example.Entity.TicketEntity;
import com.example.Entity.Enum.TicketType;
import com.example.Interface.TicketInterface;
import com.example.Interface.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class TicketService implements TicketInterface {

    private final TicketRepository ticketRepository;

    @Override
    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public TicketEntity saveTicket(TicketEntity ticketEntity) {
        ticketEntity.setType(TicketType.valueOf(ticketEntity.getType().name()));
        ticketEntity.setPrice(ticketEntity.getType().getPret());
        ticketEntity.setValid(true);
        ticketEntity.setIdUser(ticketEntity.getIdUser());
        ticketEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        LocalDateTime dataCurrenta = LocalDateTime.now();
        ticketEntity.setExpireDate(new Timestamp(dataCurrenta.plusDays(ticketEntity.getType().getDurataAbonament()).toEpochSecond(ZoneOffset.UTC) * 1000));

        log.info("Save a ticket in databases with value {}\n", ticketEntity);
        return ticketRepository.save(ticketEntity);
    }

    @Override
    public List<TicketEntity> getMax5Tickets(String id) {
        List<TicketEntity> ticketEntityList = ticketRepository.findFirst5ByIdUserOrderByCreateDateDesc(id);
        if (ticketEntityList.size() > 0){
            return ticketEntityList;
        }
        else{
            log.info("Bulit service de history");
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<TicketEntity> findById(Integer id) {
        if(id != null){
            return ticketRepository.findById(id);
        }
        else
            return Optional.empty();
    }
}
