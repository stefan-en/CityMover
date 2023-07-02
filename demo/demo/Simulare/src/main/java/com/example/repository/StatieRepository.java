package com.example.repository;

import com.example.entity.Statie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatieRepository extends MongoRepository<Statie, String> {
}
