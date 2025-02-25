package com.example.BuildWeekFinal_BE_EpicEnergyServices.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroAuthToken extends OncePerRequestFilter {

    @Autowired
    JwtUtils utils;

    @Autowired
    UserDetailsService userDetailsService;

    private String analizzaJwt(HttpServletRequest request){
        String headAutenticazione =request.getHeader("Authorization");

        // 1. Controllo sulla presenza di testo nel valore di Authorization
        // 2  Controlla se il valore recuperato inizia con "Bearer "
        // Bearer f7365dj38jkso34936djh73hd73
        if(StringUtils.hasText(headAutenticazione) && (headAutenticazione.startsWith("Bearer "))){
            // RECUPERO LA SOTTOSTRINGA ESCLUDENDO LA PRIMA SEQUENZA STANDARD
            return headAutenticazione.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = analizzaJwt(request);

            if (jwt != null && utils.validazioneJwtToken(jwt)) {
                String username = utils.recuperoUsernameDaToken(jwt);
                UserDetails dettagliUtente = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken autenticazione =
                        new UsernamePasswordAuthenticationToken(
                                dettagliUtente,
                                null,
                                dettagliUtente.getAuthorities());

                autenticazione.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autenticazione);
            }
        } catch (Exception e) {
            System.out.println("Errore durante l'autenticazione dell'utente: {}"+ e.getMessage());
        }

        filterChain.doFilter(request, response); // Continua la catena di filtri
    }





}
