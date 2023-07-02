package com.example.Interface;

import com.example.Entity.Autovehicul;
import com.example.Entity.Enum.AutoType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AutovehiculRepository extends MongoRepository<Autovehicul, String> {
    List<Autovehicul> findByTip(AutoType tip);

    Optional<Autovehicul> findByNumarTraseu(String numarTraseu);
}
