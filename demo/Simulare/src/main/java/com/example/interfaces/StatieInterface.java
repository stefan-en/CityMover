package com.example.interfaces;

import com.example.entity.Statie;

import java.util.List;

public interface StatieInterface {
    Statie saveStatie(Statie statie);

    List<Statie> getStatii();
}
