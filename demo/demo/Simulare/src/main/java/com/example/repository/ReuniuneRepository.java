package com.example.repository;

import com.example.entity.Reuniune;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReuniuneRepository extends MongoRepository<Reuniune, Integer> {
}
