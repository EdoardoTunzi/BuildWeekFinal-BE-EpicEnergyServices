package com.example.BuildWeekFinal_BE_EpicEnergyServices.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RagioneSociale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nomeAttivita;


    @OneToMany
    @JoinColumn(name = "indirizzo_id")
    private List<Indirizzo> indirizzi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRagioneSociale tipo;
}
