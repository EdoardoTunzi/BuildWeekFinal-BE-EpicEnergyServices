package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.NotFoundException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Fattura;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.FatturaDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.ClienteRepository;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.FatturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FatturaService {

    @Autowired
    FatturaRepository fatturaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // Trova tutte le Fatture
    public Page<FatturaDTO> trovaTutteLeFatture(Pageable page){

        Page<Fattura> listaFatture = fatturaRepository.findAll(page);

        List<FatturaDTO> listaFattureDTO = new ArrayList<>();
        // verifico e ciclo l elemento di destra tramite l appartenenza alla classe di sinistra
        for(Fattura fattura : listaFatture.getContent()){
            // travaso e aggiungo la lista di utenti
            FatturaDTO fatturaDTO = entity_dto(fattura);
            listaFattureDTO.add(fatturaDTO);
        }
        return new PageImpl<>(listaFattureDTO);
    }

    // Trova Fattura tramite ID
    public FatturaDTO trovaFattura(long id){
        Optional<Fattura> fatturaTrovata = fatturaRepository.findById(id);
        if(fatturaTrovata.isPresent()){
            return entity_dto(fatturaTrovata.get());
        }else{
            throw new RuntimeException("Fattura non trovata");
        }
    }

    // Modifica Fatture
    public void modificaFattura(FatturaDTO fatturaDTO, long id) {

        Optional<Fattura> fatturaTrovata = fatturaRepository.findById(id);

        if (fatturaTrovata.isPresent()) {
            Fattura fattura = fatturaTrovata.get();
            fattura.setCliente(fatturaDTO.getCliente());
            fattura.setImporto(fatturaDTO.getImporto());
            fattura.setData(fatturaDTO.getData());
            fattura.setStato(fatturaDTO.getStato());


        } else {
            throw new NotFoundException("Errore nella modifica della fattura inserita. Fattura non trovata!");
        }

    }


    // travaso DTO ----> ENTITY

    public Fattura dto_entity(FatturaDTO fatturaDTO) {
        Fattura fattura = new Fattura();
        fattura.setCliente(fatturaDTO.getCliente());
        fattura.setImporto(fatturaDTO.getImporto());
        fattura.setData(fatturaDTO.getData());
        fattura.setStato(fatturaDTO.getStato());

        return fattura;
    }

    // travaso ENTITY ---> DTO
    public FatturaDTO entity_dto(Fattura fattura) {
        FatturaDTO fatturaDTO = new FatturaDTO();
        fatturaDTO.setCliente(fattura.getCliente());
        fatturaDTO.setImporto(fattura.getImporto());
        fatturaDTO.setData(fattura.getData());
        fatturaDTO.setStato(fattura.getStato());

        return fatturaDTO;
    }



}
