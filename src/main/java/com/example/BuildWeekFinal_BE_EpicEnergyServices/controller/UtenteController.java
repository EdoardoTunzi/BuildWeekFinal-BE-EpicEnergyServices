package com.example.BuildWeekFinal_BE_EpicEnergyServices.controller;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.EmailDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.UsernameDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.ClienteDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.LoginRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.RegistrazioneRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.response.JwtResponse;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.UtenteRepository;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.jwt.JwtUtils;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services.UserDetailsImpl;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService utenteService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtenteRepository userRepository;

   

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    ClienteService clienteService;


    @PostMapping("/new")
    public ResponseEntity<String> signUp(@Validated @RequestBody RegistrazioneRequest nuovoUtente, BindingResult validazione) {
        System.out.println("sono il metodo signup");
        try {
            if (validazione.hasErrors()) {
                StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

                for (ObjectError errore : validazione.getAllErrors()) {
                    errori.append(errore.getDefaultMessage()).append("\n");
                }
                return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
            }
            String messaggio = utenteService.newUtente(nuovoUtente);
            System.out.println(nuovoUtente);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException | EmailDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getUsername(),
                userDetails.getId(),
                userDetails.getEmail(),
                roles,
                jwt));
    }


    @PostMapping("/auth/cliente")
    @PreAuthorize("hasRole('USER')")
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
}

