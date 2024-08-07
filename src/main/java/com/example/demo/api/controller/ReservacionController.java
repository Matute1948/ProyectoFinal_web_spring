package com.example.demo.api.controller;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.repository.dto.ReservaRespuestaDTO;
import com.example.demo.api.service.IPagoService;
import com.example.demo.api.service.IReservacionService;
import com.example.demo.api.service.to.PagoTO;
import com.example.demo.api.service.to.ReservacionTO;
import com.example.demo.api.service.to.RespuestaReservaTO;
import com.example.demo.api.service.to.ResumenReservaTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping(path = "/reservas")
public class ReservacionController {

      @Autowired
    private IReservacionService reservacionService;

    @Autowired
    private IPagoService pagoService;

    

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaReservaTO> generarReserva(@RequestBody ReservacionTO reservacionTO) {
        try {
            // Obtén los datos desde el objeto ReservacionTO
            String placaVehiculo = reservacionTO.getPlacaVehiculo();
            String cedula = reservacionTO.getCedulaCliente();
            LocalDate fechaInicio = reservacionTO.getFechaDeInicio();
            LocalDate fechaFin = reservacionTO.getFechaDeFin();

            // Llama al servicio para procesar la reserva
            RespuestaReservaTO respuesta = reservacionService.reservarVehiculo(placaVehiculo, cedula, fechaInicio, fechaFin);

            // Devuelve la respuesta con la información de la reserva
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespuestaReservaTO(false, "Error al procesar la reserva", null));
        }
    }

    @PostMapping(path = "/pago", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<PagoTO> registrarCobro(@RequestBody PagoTO pagoTO) {
    try {
        String numeroTarjeta = pagoTO.getNumeroTarjeta();
        LocalDate fechaCobro = pagoTO.getFechaCobro();

        // Llama al servicio para procesar el pago
        PagoTO pago = pagoService.guardar(numeroTarjeta, fechaCobro);

        // Devuelve la respuesta con la información del pago
        return ResponseEntity.ok(pago);
    } catch (RuntimeException e) {
        // Manejo de errores
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}


    @GetMapping("/detalle/{placaVehiculo}")
    public ResponseEntity<ResumenReservaTO> obtenerResumenPorPlaca(@PathVariable String placaVehiculo) {
        try {
            ResumenReservaTO resumen = reservacionService.obtenerResumenPorPlaca(placaVehiculo);
            if (resumen == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(resumen);
        } catch (RuntimeException e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Endpoint funcionando");
    }



    //----------
    //http://localhost:8082/API/v1.0/Concesionario/reservas
    private static final Logger logger = LoggerFactory.getLogger(ReservacionController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<ReservacionTO>> obtenerReservasPorFecha(
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
) {
    if (fechaInicio.isAfter(fechaFin)) {
        return ResponseEntity.badRequest().body(null);
    }

    logger.info("Received fechaInicio: {}", fechaInicio);
    logger.info("Received fechaFin: {}", fechaFin);

    List<ReservacionTO> reservas = reservacionService.buscarReservasPorFecha(fechaInicio, fechaFin);
    return ResponseEntity.ok(reservas);
}



//----------------RETIRAR
//http://localhost:8082/API/v1.0/Concesionario/reservas/retirar/{reservaId}
@PostMapping(path = "/retirar/{reservaId}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<ReservacionTO> retirarVehiculoReservado(@PathVariable String reservaId) {
    try {
        
        ReservacionTO reservacionActualizada = reservacionService.retirarVehiculoReservado(reservaId);
        return ResponseEntity.ok(reservacionActualizada);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

    // http://localhost:8082/API/v1.0/Concesionario/reservaciones/{reservaId}
    @PostMapping("/{reservaId}")
    public ResponseEntity<Void> retirarVehiculo(@PathVariable Integer reservaId) {
        reservacionService.retirarVehiculoReservado(reservaId);
        return ResponseEntity.ok().build();
    }

}
