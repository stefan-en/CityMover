package com.example.Interface;

import com.example.Entity.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface TicketInterface {
    List<TicketEntity> getAllTickets();
    TicketEntity saveTicket(TicketEntity ticketEntity);
    List<TicketEntity> getMax5Tickets(String id);
    Optional<TicketEntity> findById(Integer id);

}