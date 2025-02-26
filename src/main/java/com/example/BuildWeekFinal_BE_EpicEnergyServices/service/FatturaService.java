package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.EmailDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.NotFoundException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.PecDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Fattura;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.StatoFattura;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.ClienteDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.FatturaDTO;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.ClienteRepository;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.repository.FatturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    //crea metodo nuovafattura
    public String creaFattura(FatturaDTO fatturaDTO, long idCliente)  {

        Cliente clienteTrovato = clienteRepository.findById(idCliente).orElseThrow(()-> new RuntimeException("Cliente non trovato"));

        Fattura fattura = dto_entity(fatturaDTO);
        fattura.setCliente(clienteTrovato);

        long id_numeroFattura = fatturaRepository.save(fattura).getNumeroFattura_id();

        return "Fattura inserita nel DB con numero: " + id_numeroFattura;
    }

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

    // modifica stato fattura patch
    public void modificaFattura(StatoFattura stato, long idFattura) {

        Optional<Fattura> fatturaTrovata = fatturaRepository.findById(idFattura);

        if (fatturaTrovata.isPresent()) {
            Fattura fattura = fatturaTrovata.get();
            fattura.setStato(stato);

        } else {
            throw new NotFoundException("Errore nella modifica della fattura inserita. Fattura non trovata!");
        }
    }

    public String deleteFattura(long idfattura) {
        Fattura fatturaTrovato = fatturaRepository.findById(idfattura).orElseThrow(()->new RuntimeException("Fattura non trovato"));

        clienteRepository.deleteById(fatturaTrovato.getNumeroFattura_id());
        return "Fattura eliminata con successo";

    }


    // travaso DTO ----> ENTITY

    public Fattura dto_entity(FatturaDTO fatturaDTO) {
        Fattura fattura = new Fattura();
        fattura.setImporto(fatturaDTO.getImporto());
        fattura.setData(LocalDate.now());
        fattura.setStato(StatoFattura.EMESSA);

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
