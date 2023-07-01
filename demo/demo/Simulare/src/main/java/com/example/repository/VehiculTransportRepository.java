package com.example.repository;

import com.example.entity.VehiculTransport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehiculTransportRepository extends MongoRepository<VehiculTransport, Integer> {
}
