package com.example.demo.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.repository.IPagoRepository;
import com.example.demo.api.repository.IReservacionRepository;
import com.example.demo.api.repository.modelo.Pago;
import com.example.demo.api.repository.modelo.Reservacion;
import com.example.demo.api.service.to.PagoTO;
import com.example.demo.api.service.to.ReservacionTO;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Autowired
    private IReservacionRepository reservacionRepository;

    @Override
   // Asegura que esta operación sea transaccional
    public PagoTO guardar(String numeroTarjeta, LocalDate fechaCobro) {
        // Obtener la última reservación registrada
        Reservacion reservacion = reservacionRepository.seleccionarUltimaReservacion();
        if (reservacion == null) {
            throw new RuntimeException("Reservación no encontrada");
        }

        // Generar el número de reserva si no existe
        if (reservacion.getNumeroReserva() == null) {
            String numeroReserva = generarNumeroReserva();
            reservacion.setNumeroReserva(numeroReserva);
            reservacionRepository.actualizar(reservacion); // Actualiza la reservación
        }

        // Crear un nuevo pago
        Pago pago = new Pago();
        pago.setNumeroTarjeta(numeroTarjeta);
        pago.setFechaCobro(fechaCobro);
        pago.setReservacion(reservacion);
        pagoRepository.insertar(pago);

        return convertirTO(pago);
    }

    private String generarNumeroReserva() {
        return "RES" + java.util.UUID.randomUUID().toString();
    }

    private PagoTO convertirTO(Pago pago) {
        if (pago == null) {
            return null;
        }

        return new PagoTO(
            pago.getId(),
            pago.getNumeroTarjeta(),
            pago.getFechaCobro(),
            pago.getReservacion().getNumeroReserva()
        );
    }
}
