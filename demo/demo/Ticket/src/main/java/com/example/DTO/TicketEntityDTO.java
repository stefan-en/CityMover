package com.example.DTO;

import com.example.Entity.Enum.TicketType;
import lombok.Data;

import java.util.Date;

@Data
public class TicketEntityDTO {
    Integer id;
    String idUser;
    TicketType type;
    Date expireDate;
    Date createDate;
    boolean isValid;
}
