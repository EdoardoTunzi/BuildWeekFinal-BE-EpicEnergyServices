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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//implementiamo una interfaccia di Spring Security per i servizio di autenticazione
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private UtenteRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
