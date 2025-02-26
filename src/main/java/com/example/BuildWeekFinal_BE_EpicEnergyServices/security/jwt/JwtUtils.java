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
// Funzionlità Utilities del TOKEN
public class JwtUtils {

    // Agganciare le costanti legate al JWT
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirations;

    // Creazione del JWT
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirations))
                .signWith(recuperoChiave(), SignatureAlgorithm.HS256)
                .compact();
    }


    // Recupera l'username dal JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(recuperoChiave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validazioneJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(recuperoChiave()).build().parseClaimsJws(authToken);
            return true;
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

        return false;
    }

    // Recupera la scadenza dal JWT
    public Date recuperoScadenzaDaToken(String token){
        return Jwts.parserBuilder().setSigningKey(recuperoChiave()).build().parseClaimsJwt(token).getBody().getExpiration();
    }

    // Validazione del TOKEN JWT

    // Recupero della chiave
    public Key recuperoChiave(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}
