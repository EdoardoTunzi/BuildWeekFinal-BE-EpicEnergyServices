package com.example.BuildWeekFinal_BE_EpicEnergyServices.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroreRes {
    private HttpStatus httpStatus;
    private String message;
}
