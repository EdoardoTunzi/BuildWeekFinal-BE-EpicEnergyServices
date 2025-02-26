package com.example.BuildWeekFinal_BE_EpicEnergyServices.payload;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Cliente;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.StatoFattura;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FatturaDTO {

    @NotBlank(message = "Il campo è obbligatorio")
    private Cliente cliente;

    @NotNull(message = "Inserire la data")
    private LocalDate data;

    @NotBlank(message = "Il campo è obbligatorio")
    private double importo;

    @NotBlank(message = "Il campo è obbligatorio")
    private StatoFattura stato;
}
