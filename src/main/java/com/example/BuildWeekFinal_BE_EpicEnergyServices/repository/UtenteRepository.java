package com.example.BuildWeekFinal_BE_EpicEnergyServices.repository;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public Optional<Utente> findByUsername(String username);

    // check login
    public boolean existsByUsernameAndPassword(String username, String password);

    // check duplicate key
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
