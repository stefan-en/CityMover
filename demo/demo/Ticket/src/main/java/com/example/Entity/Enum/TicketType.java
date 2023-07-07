package com.example.Entity.Enum;

public enum TicketType {
    ABONAMENT_LUNAR(30, 60.0),
    ABONAMENT_STUDENT(30, 50.0),
    ABONAMENT_ELEV(30, 35.0),
    ABONAMENT_PENSIONAR(30, 30.0),
    ABONAMENT_ANUAL(365, 365.0),
    BILET_CALATORIE(1, 3);

    private final int durataAbonament;
    private final double pret;

    private TicketType(int durataAbonament, double pret) {
        this.durataAbonament = durataAbonament;
        this.pret = pret;
    }

    public int getDurataAbonament() {
        return durataAbonament;
    }

    public double getPret() {
        return pret;
    }
}