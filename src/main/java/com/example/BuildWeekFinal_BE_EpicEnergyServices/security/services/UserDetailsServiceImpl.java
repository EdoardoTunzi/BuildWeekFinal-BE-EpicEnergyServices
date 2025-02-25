package com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Utente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UtenteRepository repoUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> utente = repoUser.findByUsername(username);

        // Recupero tutte le info dell'utente presente sul database
        Utente user = utente.orElseThrow();

        // Ritorna un oggetto di tipo UserDetailsImpl (-> implements UserDetails)
        // Contenitore delle info che vogliamo inserire nel token
        return UserDetailsImpl.costruisciDettagli(user);
    }
}
