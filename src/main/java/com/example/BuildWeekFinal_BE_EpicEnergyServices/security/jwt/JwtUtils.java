package com.example.BuildWeekFinal_BE_EpicEnergyServices.security.jwt;


import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
// Classe di utilità per la gestione dei JWT
public class JwtUtils {

    // Agganciare le costanti legate al JWT
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirations;

    // Metodo per generare un token JWT a partire da un'istanza di Authentication
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))// Imposta il nome utente come subject del token
                .setIssuedAt(new Date()) // Imposta la data di creazione del token
                .setExpiration(new Date((new Date()).getTime() + jwtExpirations))// Imposta la data di scadenza del token
                .signWith(recuperoChiave(), SignatureAlgorithm.HS256)// Firma il token con la chiave segreta
                .compact();// Crea il token come stringa compatta
    }


    // Metodo per estrarre lo username dal token JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(recuperoChiave()) // Imposta la chiave segreta per la decodifica
                .build()
                .parseClaimsJws(token)// Parsea il token per ottenere i claims
                .getBody()
                .getSubject();// Restituisce il subject (username)
    }
    // Metodo per validare un token JWT
    public boolean validazioneJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(recuperoChiave()).build().parseClaimsJws(authToken);
            return true;// Il token è valido
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature:" + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired:" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }

        return false; // Il token non è valido
    }

    // Metodo per ottenere la data di scadenza di un token JWT
    public Date recuperoScadenzaDaToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(recuperoChiave())// Usa la chiave segreta per la decodifica
                .build()
                .parseClaimsJwt(token)// Parsea il token
                .getBody()
                .getExpiration();// Restituisce la data di scadenza
    }

    // Metodo per recuperare la chiave segreta usata per firmare i token
    public Key recuperoChiave(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}
