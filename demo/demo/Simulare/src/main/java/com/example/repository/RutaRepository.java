package com.example.repository;

import com.example.entity.Ruta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RutaRepository extends MongoRepository<Ruta, String> {
    List<String> findAllByNumeTraseuNotNull();
}

