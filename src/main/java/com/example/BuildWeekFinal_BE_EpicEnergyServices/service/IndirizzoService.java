package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Indirizzo;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.ClienteDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.IndirizzoDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoService {

    @Autowired
    IndirizzoRepository indirizzoRepository;

    public Indirizzo saveIndirizzo(IndirizzoDTO indirizzoDTO){
        Indirizzo indirizzo = dto_entity(indirizzoDTO);
        indirizzoRepository.save(indirizzo);
        return indirizzo;
    }

    public Indirizzo dto_entity(IndirizzoDTO indirizzoDTO) {
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(indirizzoDTO.getVia());
        indirizzo.setComune(indirizzoDTO.getComune());
        indirizzo.setCap(indirizzoDTO.getCap());
        indirizzo.setCivico(indirizzoDTO.getCivico());
        indirizzo.setLocalita(indirizzoDTO.getLocalita());
        indirizzo.setTipoSede(indirizzoDTO.getTipoSede());


        return indirizzo;
    }
}
