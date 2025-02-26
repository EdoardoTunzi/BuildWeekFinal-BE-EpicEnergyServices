package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Fattura;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.FatturaDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RagioneSocialeService {

    //crea metodo nuovafattura
    /*public String creaRagioneSoc(FatturaDTO fatturaDTO)  {


        Fattura fattura = dto_entity(fatturaDTO);
        fattura.setCliente(clienteTrovato);

        long id_numeroFattura = fatturaRepository.save(fattura).getNumeroFattura_id();

        return "Fattura inserita nel DB con numero: " + id_numeroFattura;
    }*/
}
