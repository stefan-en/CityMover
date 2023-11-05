package com.example.repository;

import com.example.data.ReuniuneStatii;
import com.example.entity.Reuniune;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReuniuneRepository extends MongoRepository<Reuniune, String> {

    @Aggregation(pipeline = {
            "{$group: {_id: '$tripId', statii: {$push: '$stopId'}}}",
            "{$lookup: {from: 'statii', localField: 'statii', foreignField: 'idStatie', as: 'numeStatii'}}",
            "{$project: {_id: 0, tripId: '$_id', statii: '$numeStatii.name'}}",
            "{$out: reuniunestatii}"
    })
    List<ReuniuneStatii> getStatiiForTrip();
}
