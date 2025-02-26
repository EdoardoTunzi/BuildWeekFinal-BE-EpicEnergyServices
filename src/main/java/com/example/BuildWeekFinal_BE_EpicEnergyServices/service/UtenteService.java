package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.EmailDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.UsernameDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.ERuolo;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Ruolo;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Utente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.request.RegistrazioneRequest;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.RuoloRepository;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RuoloRepository ruoloRepo;


    public String newUtente(RegistrazioneRequest registrazione) {
        String passwordCodificata = passwordEncoder.encode(registrazione.getPassword());
        checkDuplicateKey(registrazione.getUsername(), registrazione.getEmail());
        Utente user = registrazioneRequest_Utente(registrazione);
        user.setPassword(passwordCodificata);
        // controllo assegnazione role
        if (registrazione.getRuoli() == null || registrazione.getRuoli().contains(ERuolo.USER_ROLE.name())) {
            Ruolo defaultRole = ruoloRepo.findByNome(ERuolo.USER_ROLE).orElseThrow(() -> new RuntimeException("Errore: Ruolo non trovato."));
            user.getRuolo().add(defaultRole);
        } else if (registrazione.getRuoli().contains(ERuolo.ADMIN_ROLE.name())) {
            Ruolo adminRole = ruoloRepo.findByNome(ERuolo.ADMIN_ROLE).orElseThrow(() -> new RuntimeException("Errore: Ruolo non trovato."));
            user.getRuolo().add(adminRole);
        } else {
            throw new RuntimeException("Errore: Il Valore inserito come ruolo non è valido!");
        }
        Long id = utenteRepo.save(user).getId();
        return "Nuovo utente " + user.getUsername() + "con id " + id + " è stato inserito correttamente";
    }

    public void checkDuplicateKey(String username, String email) throws UsernameDuplicateException, EmailDuplicateException {

        if (utenteRepo.existsByUsername(username)) {
            throw new UsernameDuplicateException("Username già utilizzato, non disponibile");
        }

        if (utenteRepo.existsByEmail(email)) {
            throw new EmailDuplicateException("Email già utilizzata da un altro utente");
        }
    }

    public Utente registrazioneRequest_Utente(RegistrazioneRequest request) {
        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setNome(request.getNome());
        utente.setUsername(request.getUsername());
        utente.setCognome(request.getCognome());
        utente.setAvatar("https://www.fotoarreda.com/quadro-su-tela/stampa/personalizzata/immagine/790443592_1-1.html");
        return utente;
    }
}
