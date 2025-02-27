package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Fattura;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Indirizzo;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.RagioneSociale;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.ClienteDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.FatturaDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.RagioneSocialeDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.RagioneSocialeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RagioneSocialeService {

    @Autowired
    RagioneSocialeRepository ragioneSocialeRepository;


    //salvataggio in db
    public RagioneSociale saveRagioneSociale(RagioneSocialeDTO ragioneSocialeDto, List<Indirizzo> lista){
        //travaso e creazione dell oggetto ragione sociale
        RagioneSociale ragioneSociale = dto_entity(ragioneSocialeDto);
        //set della lista di indirizzi
        ragioneSociale.setIndirizzi(lista);
        //salvataggio
        ragioneSocialeRepository.save(ragioneSociale);
        return ragioneSociale;

    }


    public RagioneSociale dto_entity(RagioneSocialeDTO ragioneSocialeDTO) {
        RagioneSociale ragioneSociale = new RagioneSociale();
        ragioneSociale.setNomeAttivita(ragioneSocialeDTO.getNomeAttivita());
        ragioneSociale.setTipo(ragioneSocialeDTO.getTipo());

        return ragioneSociale;
    }
}
