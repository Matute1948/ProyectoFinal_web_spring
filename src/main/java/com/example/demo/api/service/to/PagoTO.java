package com.example.demo.api.service.to;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoTO extends RepresentationModel<PagoTO> {

     private Integer id;

    private String numeroTarjeta;

    private LocalDate fechaCobro;

    private String idReserva;

    
}