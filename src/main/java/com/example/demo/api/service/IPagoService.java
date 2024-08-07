package com.example.demo.api.service;

import java.time.LocalDate;

import com.example.demo.api.service.to.PagoTO;


public interface IPagoService {

    PagoTO guardar(String numeroTarjeta, LocalDate fechaCobro);  
    
}
