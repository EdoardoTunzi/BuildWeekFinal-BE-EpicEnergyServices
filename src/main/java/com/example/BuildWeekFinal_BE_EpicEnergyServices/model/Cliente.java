package com.example.BuildWeekFinal_BE_EpicEnergyServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clienti")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private RagioneSociale ragioneSociale;
    @Column(nullable = false)
    private String partitaIVA;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;
    @Column(nullable = false)
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;
    @OneToMany
    private List<Fattura> listaFatture;


}
