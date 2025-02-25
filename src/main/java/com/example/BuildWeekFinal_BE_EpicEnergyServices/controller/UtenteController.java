package com.example.BuildWeekFinal_BE_EpicEnergyServices.controller;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.EmailDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.UsernameDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.LoginRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.RegistrazioneRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.response.JwtResponse;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService utenteService;



    @PostMapping("/public/new")
    public ResponseEntity<String> signUp (@Validated @RequestBody RegistrazioneRequest nuovoUtente, BindingResult validazione) {
        System.out.println("sono il metodo signup");
        try {
            if(validazione.hasErrors()){
                StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

                for(ObjectError errore : validazione.getAllErrors()){
                    errori.append(errore.getDefaultMessage()).append("\n");
                }
                return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
            }
            String messaggio =utenteService.newUtente(nuovoUtente);
            System.out.println(nuovoUtente);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException | EmailDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginDto, BindingResult validazione){

        // VALIDAZIONE
        if(validazione.hasErrors()){
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

            for(ObjectError errore : validazione.getAllErrors()){
                errori.append(errore.getDefaultMessage()).append("\n");
            }

            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);

        }

        try {
            JwtResponse responseJWT = utenteService.login(loginDto);

            // Gestione della risposta al Client -> ResponseEntity
            return new ResponseEntity<>(responseJWT, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Credenziali non valide", HttpStatus.UNAUTHORIZED);
        }

    }
}
