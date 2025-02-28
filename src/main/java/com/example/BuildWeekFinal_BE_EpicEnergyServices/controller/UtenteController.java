package com.example.BuildWeekFinal_BE_EpicEnergyServices.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.EmailDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.UsernameDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.ClienteDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.LoginRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.RegistrazioneRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.response.JwtResponse;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.response.LoginResponse;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.UtenteRepository;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.service.ClienteService;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    Cloudinary cloudinaryConfig;


    @PostMapping("/new")
    public ResponseEntity<String> signUp(@Validated @RequestBody RegistrazioneRequest nuovoUtente, BindingResult validazione) {
        System.out.println("sono il metodo signup");
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }

        try {

            String messaggio = utenteService.newUtente(nuovoUtente);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException | EmailDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginDTO, BindingResult checkValidazione) {

        try {

            if (checkValidazione.hasErrors()) {
                StringBuilder erroriValidazione = new StringBuilder("Problemi nella validazione\n");
                for (ObjectError errore : checkValidazione.getAllErrors()) {
                    erroriValidazione.append(errore.getDefaultMessage());
                }

                return new ResponseEntity<>(erroriValidazione.toString(), HttpStatus.BAD_REQUEST);
            }


            LoginResponse response = utenteService.login(loginDTO.getUsername(), loginDTO.getPassword());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/auth/cliente")
    public ResponseEntity<?> postCliente(@Validated @RequestBody ClienteDTO clienteDTO, BindingResult validazione) {
        System.out.println("richiesta arrivata a /user/cliente");
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String messaggio = clienteService.creaCliente(clienteDTO);

        return new ResponseEntity<>(messaggio, HttpStatus.CREATED);


    }

    @PatchMapping("/auth/avatar/{idUtente}")
    public ResponseEntity<?> cambiaAvatarUtente(@RequestPart("avatar") MultipartFile avatar, @PathVariable long idUtente) {
        try {
            Map mappa = cloudinaryConfig.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mappa.get("secure_url").toString();
            utenteService.modificaAvatar(idUtente, urlImage);
            return new ResponseEntity<>("Immagine avatar sostituita", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'immagine: " + e);
        }
    }

    @DeleteMapping("/admin/deleteCliente/{idCliente}")
    public ResponseEntity<?> deleteCliente(@PathVariable long idCliente) {
        try {
            clienteService.deleteCliente(idCliente);
            return new ResponseEntity<>("Cliente eliminato con successo", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Errore durante eliminazione. Cliente non trovato" + e, HttpStatus.BAD_REQUEST);
        }
    }
}

