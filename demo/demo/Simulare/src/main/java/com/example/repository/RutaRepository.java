package com.example.repository;

import com.example.entity.Ruta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RutaRepository extends MongoRepository<Ruta, String> {
}
