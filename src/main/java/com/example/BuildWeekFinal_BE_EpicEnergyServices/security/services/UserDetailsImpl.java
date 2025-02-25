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

    // stiamo personalizzando i dettagli da inserire nel token JWT

    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> ruoli;


    public static UserDetailsImpl costruisciDettagli(Utente user){

       // Conversione Set<Ruolo> -> List<GrantedAuthority>
       List<GrantedAuthority> ruoliUtente =user.getRuolo().stream()
               .map(ruolo -> new SimpleGrantedAuthority(ruolo.getNome().name())).collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(),user.getUsername(),user.getEmail(), user.getPassword(), ruoliUtente);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
