package com.example.BuildWeekFinal_BE_EpicEnergyServices.repository;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Fattura;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FatturaRepository extends JpaRepository<Fattura,Long> {
    
    List <Optional<Fattura>> findByClienteId(long id);
    
    List<Optional<Fattura>>findByStato(StatoFattura stato);
    
    List<Optional<Fattura>> findByData(LocalDate data);
    
    List<Optional<Fattura>> findByImporto(double importo);
    
    
}
