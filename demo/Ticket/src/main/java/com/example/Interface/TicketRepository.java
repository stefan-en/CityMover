package com.example.Interface;

import com.example.Entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    Optional<TicketEntity> findById(Integer id);
    List<TicketEntity> findFirst5ByIdUserOrderByCreateDateDesc(String user);
}
