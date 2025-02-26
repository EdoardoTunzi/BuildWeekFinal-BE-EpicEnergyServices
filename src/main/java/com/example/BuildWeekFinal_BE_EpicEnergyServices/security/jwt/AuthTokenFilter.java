package com.example.BuildWeekFinal_BE_EpicEnergyServices.security.jwt;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
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
import java.util.logging.Logger;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Metodo principale del filtro: intercetta ogni richiesta HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Estrai il token JWT dall'header Authorization della richiesta
            String jwt = parseJwt(request);
            // Verifica che il token sia valido
            if (jwt != null && jwtUtils.validazioneJwtToken(jwt)) {
                // Estrai il nome utente dal token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // Carica i dettagli dell'utente dal database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Crea un oggetto di autenticazione basato sull'utente e sui suoi ruoli
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Imposta i dettagli della richiesta nell'autenticazione
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Salva l'autenticazione nel SecurityContext di Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e.getMessage());
        }
        // Continua con la catena di filtri
        filterChain.doFilter(request, response);
    }


    // Metodo per estrarre il token JWT dall'header Authorization
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        // Controlla se l'header contiene un token valido con prefisso "Bearer "
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);// Restituisce il token senza il prefisso "Bearer "
        }
        // Nessun token presente
        return null;
    }
}