package com.example.BuildWeekFinal_BE_EpicEnergyServices.payload;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.TipoRagioneSociale;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RagioneSocialeDTO {
    @NotBlank(message = "Il nome è obbligatorio")
    private String nomeAttivita;
    private TipoRagioneSociale tipo;
    private String indirizzoSedeLegale;
    private String indirizzoSedeOperativa;
    private String tipoRagionaleSociale;
}
