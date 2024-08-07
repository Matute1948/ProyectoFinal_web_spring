package com.example.demo.api.repository.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRespuestaDTO {

    private boolean exito;
    private String mensaje;
    private LocalDate fechaDisponible;

}