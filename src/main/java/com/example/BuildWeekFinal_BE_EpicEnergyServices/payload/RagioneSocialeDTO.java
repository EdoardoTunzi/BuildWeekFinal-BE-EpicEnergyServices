package com.example.BuildWeekFinal_BE_EpicEnergyServices.payload;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.Indirizzo;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.TipoRagioneSociale;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RagioneSocialeDTO {
    @NotBlank(message = "Il nome è obbligatorio")
    private String nomeAttivita;
    @NotNull(message = "il campo è obbligatorio")
    private TipoRagioneSociale tipo;
    @NotNull(message = "il campo è obbligatorio")
    private List<IndirizzoDTO> indirizzi;

//    private String indirizzoSedeLegale;
//    private String indirizzoSedeOperativa;
//    @NotBlank(message = "il campo è obbligatorio")
//    private String tipoRagionaleSociale;
}
