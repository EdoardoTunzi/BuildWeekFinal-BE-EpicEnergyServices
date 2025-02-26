package com.example.BuildWeekFinal_BE_EpicEnergyServices.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

@Entity(name = "utenti")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="utente_ruolo",
            joinColumns = @JoinColumn(name="utente_id"),
            inverseJoinColumns =  @JoinColumn(name="ruolo_id"))
    private Set<Ruolo> ruolo= new HashSet<>();

    private String avatar;
}