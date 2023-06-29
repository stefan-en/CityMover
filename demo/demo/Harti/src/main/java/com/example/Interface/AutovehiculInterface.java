package com.example.Interface;

import com.example.Entity.Autovehicul;
import com.example.Entity.Enum.AutoType;

import java.util.List;
import java.util.Optional;

public interface AutovehiculInterface {

    Optional<Autovehicul> findByNumarTraseu(String numarTraseu);

    List<Autovehicul> getAutovehiculByType(AutoType type);

    Autovehicul saveAutovehicul(Autovehicul autovehicul);
}
