package com.example.BuildWeekFinal_BE_EpicEnergyServices.exception;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String message) {
        super(message);
    }
}
