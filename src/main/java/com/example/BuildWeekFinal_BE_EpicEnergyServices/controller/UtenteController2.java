package com.example.BuildWeekFinal_BE_EpicEnergyServices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user2")
public class UtenteController2 {

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String prova() {
        return "Prova";
    }
}
