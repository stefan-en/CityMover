package com.example.repository;

import com.example.data.DataAgregate;
import com.example.data.VehiculRute;
import com.example.entity.VehiculTransport;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehiculTransportRepository extends MongoRepository<VehiculTransport, String> {

    @Aggregation(pipeline = {
            "{$lookup: {from: 'rute', localField: 'routeId', foreignField: 'routeId', as: 'joinedData'}}",
            "{$unwind: { path: '$joinedData', preserveNullAndEmptyArrays: true }}",
            "{$project: { _id: 0, tripId: 1, routeId: '$joinedData.routeId', numeTraseu: '$joinedData.numeTraseu', numarTraseu: '$joinedData.numarTraseu' }}",
            "{$out: vehiculrute}"
    })
    List<VehiculRute> getVehiculeWithRute();

    @Aggregation(pipeline = {
            "{$lookup: { from: 'reuniunestatii', localField: 'tripId', foreignField: 'tripId', as: 'joinedData' }}",
            "{$unwind: { path: '$joinedData', preserveNullAndEmptyArrays: true }}",
            "{$lookup: { from: 'vehiculrute', localField: 'tripId', foreignField: 'tripId', as: 'vehiculRuteData' }}",
            "{$unwind: { path: '$vehiculRuteData', preserveNullAndEmptyArrays: true }}",
            "{$group: { _id: '$tripId', numeTraseu: { $first: '$vehiculRuteData.numeTraseu' }, numarTraseu: { $first: '$vehiculRuteData.numarTraseu' }, statii: { $first: '$joinedData.statii' }}}",
            "{$project: { _id: 0, tripId: '$_id', numeTraseu: 1, numarTraseu: 1, statii: 1 }}",
            "{$out: final}"
        })
        List<DataAgregate> getColectieRezultatWithStatii();

}
