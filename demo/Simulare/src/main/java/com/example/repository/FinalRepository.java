package com.example.repository;

import com.example.data.DataAgregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FinalRepository extends MongoRepository<DataAgregate, String> {
    @Query(value = "{'numeTraseu': ?0}", fields = "{'statii': 1}, limit=1")
    List<String> findStatiiByNumeTraseu(String numeTraseu);
}
