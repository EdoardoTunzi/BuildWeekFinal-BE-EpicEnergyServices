package com.example.BuildWeekFinal_BE_EpicEnergyServices.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idruolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERuolo nome;


}
