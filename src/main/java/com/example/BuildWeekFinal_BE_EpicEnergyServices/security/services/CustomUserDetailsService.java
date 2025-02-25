package com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services;


import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Utente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//implementiamo una interfaccia di Spring Security per i servizio di autenticazione
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UtenteRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //autenticazione 
    	Optional<Utente> user = userRepository.findByUsername(username);

        // Viene atuenticato altrimenti viene sollevata un'eccezione
        Utente userAutenticato = user.orElseThrow();

        // Conversione Set<Ruolo> -> List<GrantedAuthority>
        List<GrantedAuthority> ruoliUtente = userAutenticato.getRuolo().stream()
                .map(ruolo -> new SimpleGrantedAuthority(ruolo.getNome().name())).collect(Collectors.toList());

        UserDetails dettagliUtente =  User.builder().username(user.get().getUsername())
                                                    .password(user.get().getPassword())
                                                    .roles(ruoliUtente.toArray(new String[0]))
                                                    .build();
        return dettagliUtente;
    }
}
