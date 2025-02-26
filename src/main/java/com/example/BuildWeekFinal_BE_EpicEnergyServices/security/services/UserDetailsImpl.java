package com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services;


import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Utente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;// Serve per la serializzazione dell'oggetto

    private Long id;

    private String username;

    private String email;

    @JsonIgnore// Evita che la password venga inclusa nelle risposte JSON
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    // Metodo statico per creare un'istanza di UserDetailsImpl a partire da un Utente
    public static UserDetailsImpl build(Utente user) {
        // Converte i ruoli dell'utente in una lista di GrantedAuthority, che Spring Security usa per gestire i permessi

        List<GrantedAuthority> authorities = user.getRuolo().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
    // Metodi richiesti dall'interfaccia UserDetails, che indicano se l'account è attivo o bloccato

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
