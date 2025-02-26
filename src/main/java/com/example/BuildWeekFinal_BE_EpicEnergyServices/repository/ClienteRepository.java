package com.example.BuildWeekFinal_BE_EpicEnergyServices.repository;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    boolean existsByPec(String pec);

    boolean existsByEmail(String email);
}
