package com.example.BuildWeekFinal_BE_EpicEnergyServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "fatture")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeroFattura_id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private double importo;
    @Enumerated(EnumType.STRING)
    private StatoFattura stato;

}
