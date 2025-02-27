package com.example.BuildWeekFinal_BE_EpicEnergyServices.payload;

import com.example.BuildWeekFinal_BE_EpicEnergyServices.model.TipoSede;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IndirizzoDTO {
    @NotNull(message= "il campo tipo sede è obbligatorio")
    private TipoSede tipoSede;

    @NotBlank(message = "il campo via è obbligatorio")
    private String via;

    @NotBlank(message = "il campo civico è obbligatorio")
    private String civico;

    @NotBlank(message = "il campo localita è obbligatorio")
    private String localita;

    @NotNull(message = "il campo cap è obbligatorio")
    private int cap;

    @NotBlank(message = "il campo comune è obbligatorio")
    private String comune;
}
