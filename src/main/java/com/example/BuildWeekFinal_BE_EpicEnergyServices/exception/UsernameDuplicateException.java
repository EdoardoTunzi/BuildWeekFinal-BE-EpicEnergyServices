package com.example.BuildWeekFinal_BE_EpicEnergyServices.exception;

public class UsernameDuplicateException extends RuntimeException {
    public UsernameDuplicateException(String message) {
        super(message);
    }
}
