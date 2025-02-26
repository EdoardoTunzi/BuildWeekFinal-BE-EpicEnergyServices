package com.example.BuildWeekFinal_BE_EpicEnergyServices.payload;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.RagioneSociale;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
public class ClienteDTO {
    @NotBlank(message = "Il campo è obbligatorio")
    private RagioneSociale ragioneSociale;

    @NotBlank(message = "Il campo è obbligatorio")
    private String partitaIVA;

    @NotBlank(message = "Il campo è obbligatorio")
    @Email
    private String email;

    @NotNull(message = "Inserisci Data Inserimento ") // il Local Date vuole il NotNull perchè il NotBlank va in conflitto
    private LocalDate dataInserimento;

    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;

    @NotBlank(message = "Il campo è obbligatorio")
    @Email
    private String pec;

    private String telefono;
    private String emailContatto;

    @NotNull(message = "Inserisci Nome Contatto ")
    private String nomeContatto;

    @NotNull(message = "Inserisci Nome Contatto ")
    private String cognomeContatto;
    private String telefonoContatto;
    @URL(protocol = "https")
    private String logoAziendale;
}
