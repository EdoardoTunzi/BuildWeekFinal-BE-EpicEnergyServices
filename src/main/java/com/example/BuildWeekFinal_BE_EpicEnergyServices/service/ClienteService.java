package com.example.BuildWeekFinal_BE_EpicEnergyServices.service;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.EmailDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.NotFoundException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.exception.PecDuplicateException;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.ClienteDTO;
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
public class ClienteService {

@Autowired
    ClienteRepository clienteRepository;


    //  crea Cliente

    public String creaCliente(ClienteDTO clienteDTO) throws PecDuplicateException, EmailDuplicateException {

        controlloDuplicati(clienteDTO.getPec(),clienteDTO.getEmail());

        Cliente cliente = dto_entity(clienteDTO);
        long id = clienteRepository.save(cliente).getId();


        return "Cliente inserito con ID: " + id;
    }

    // Trova Cliente tramite ID
    public ClienteDTO trovaCliente(long id){
        Optional<Cliente> clienteTrovato = clienteRepository.findById(id);
        if(clienteTrovato.isPresent()){
            return entity_dto(clienteTrovato.get());
        }else{
            throw new RuntimeException("Cliente non trovato");
        }

    }

    // Trova tutti i clienti
    public Page<ClienteDTO> trovaTuttiClienti(Pageable page){

        Page<Cliente> listaClienti = clienteRepository.findAll(page);

        List<ClienteDTO> listaClientiDTO = new ArrayList<>();
        // verifico e ciclo l elemento di destra tramite l appartenenza alla classe di sinistra
        for(Cliente cliente : listaClienti.getContent()){
            // travaso e aggiungo la lista di utenti
            ClienteDTO clienteDTO = entity_dto(cliente);
            listaClientiDTO.add(clienteDTO);
        }
        return new PageImpl<>(listaClientiDTO);
    }

    // Modifica Cliente
    public void modificaCliente(ClienteDTO clienteDto, long id) {

        Optional<Cliente> clienteTrovato = clienteRepository.findById(id);

        if (clienteTrovato.isPresent()) {
            Cliente cliente = clienteTrovato.get();
            //risolvi
            cliente.setRagioneSociale(clienteDto.getRagioneSociale());
            cliente.setPartitaIVA(clienteDto.getPartitaIVA());
            cliente.setEmail(clienteDto.getEmail());
            cliente.setDataInserimento(clienteDto.getDataInserimento());
            cliente.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
            cliente.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
            cliente.setPec(clienteDto.getPec());
            cliente.setTelefono(clienteDto.getTelefono());
            cliente.setEmailContatto(clienteDto.getEmailContatto());
            cliente.setNomeContatto(clienteDto.getNomeContatto());
            cliente.setCognomeContatto(clienteDto.getCognomeContatto());
            cliente.setTelefonoContatto(clienteDto.getTelefonoContatto());
            cliente.setLogoAziendale(clienteDto.getLogoAziendale());


        } else {
            throw new NotFoundException("Errore nella modifica del cliente inserito. Utente non trovato!");
        }
    }

    // Aggiorna parametro Cliente

    public String aggiornaEmailCliente(String email ,long id) {
        Optional<Cliente> clienteTrovato = clienteRepository.findById(id);
        // l'oggetto è agganciato al DB
        Cliente cliente = clienteTrovato.orElseThrow();
        // Hibernate effettua un update sulla tabella cliente
        cliente.setEmail(email);
        return "Email aggiornata correttamente --> " + email;
    }

    public String deleteCliente(long idCliente) {
        Cliente clienteTrovato = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("Cliente non trovato"));

        clienteRepository.deleteById(clienteTrovato.getId());
        return "Cliente eliminato con successo";

    }





    // controllo duplicato Username e Password
    public void controlloDuplicati(String pec, String email) throws PecDuplicateException, EmailDuplicateException {

        if(clienteRepository.existsByPec(pec)){
            throw new PecDuplicateException("Pec gia esistente nel sistema");
        }

        if(clienteRepository.existsByEmail(email)){
            throw new EmailDuplicateException("Email gia presente nel sistema");
        }

    }


    // travaso DTO ----> ENTITY

    public Cliente dto_entity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setRagioneSociale(clienteDTO.getRagioneSociale());
        cliente.setPartitaIVA(clienteDTO.getPartitaIVA());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setDataInserimento(clienteDTO.getDataInserimento());
        cliente.setDataUltimoContatto(clienteDTO.getDataUltimoContatto());
        cliente.setFatturatoAnnuale(clienteDTO.getFatturatoAnnuale());
        cliente.setPec(clienteDTO.getPec());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmailContatto(clienteDTO.getEmailContatto());
        cliente.setNomeContatto(clienteDTO.getNomeContatto());
        cliente.setCognomeContatto(clienteDTO.getCognomeContatto());
        cliente.setTelefonoContatto(clienteDTO.getTelefonoContatto());
        cliente.setLogoAziendale(clienteDTO.getLogoAziendale());

        return cliente;
    }

    // travaso ENTITY ---> DTO
    public ClienteDTO entity_dto(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setRagioneSociale(cliente.getRagioneSociale());
        clienteDTO.setPartitaIVA(cliente.getPartitaIVA());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setDataInserimento(cliente.getDataInserimento());
        clienteDTO.setDataUltimoContatto(cliente.getDataUltimoContatto());
        clienteDTO.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
        clienteDTO.setPec(cliente.getPec());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setEmailContatto(cliente.getEmailContatto());
        clienteDTO.setNomeContatto(cliente.getNomeContatto());
        clienteDTO.setCognomeContatto(cliente.getCognomeContatto());
        clienteDTO.setTelefonoContatto(cliente.getTelefonoContatto());
        clienteDTO.setLogoAziendale(cliente.getLogoAziendale());

        return clienteDTO;
    }

}
