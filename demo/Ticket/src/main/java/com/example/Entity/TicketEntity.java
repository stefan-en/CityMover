package com.example.Entity;

import com.example.Entity.Enum.TicketType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String idUser;
    TicketType type;
    double price;
    Date expireDate;
    Date createDate;
    boolean isValid;

   public TicketEntity(String idUser,TicketType type){
       this.idUser = idUser;
       this.createDate = new Timestamp(System.currentTimeMillis());
       this.type = type;
       this.price = type.getPret();
   }

    public TicketEntity(String s, Timestamp valueOf, TicketType abonamentStudent) {
        this.idUser = s;
        this.createDate = valueOf;
        this.type = abonamentStudent;
        this.price = type.getPret();
    }
}
