package com.example.BuildWeekFinal_BE_EpicEnergyServices.repository;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.ERuolo;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(ERuolo nome);
}
