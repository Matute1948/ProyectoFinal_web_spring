package com.example.demo.api.service.to;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RespuestaReservaTO {
      private boolean exito;
    private String mensaje;
    private LocalDate fechaDisponible;
}
