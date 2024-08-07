package com.example.demo.api.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.api.repository.dto.ResumenReservaDTO;
import com.example.demo.api.repository.modelo.Reservacion;


public interface IReservacionRepository {
    // Métodos CRUD básico
    public void insertar(Reservacion reservacion);
    public void actualizar(Reservacion reservacion);
    public Reservacion seleccionarPorId(Integer id);

    List<Reservacion> buscarPorPlacaVehiculoFechaInicioFin(String placaVehiculo,String cedula, LocalDate fechaInicio, LocalDate fechaFin);

    public List<Reservacion> buscarReservasPorVehiculo(String placaVehiculo);

    public ResumenReservaDTO seleccionarResumenPorPlaca(String placaVehiculo);

    Reservacion seleccionarUltimaReservacion();

    List<Reservacion> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin);

    public List<Reservacion> buscarReservacionesConDetalles(LocalDate fechaInicio, LocalDate fechaFin);

    public Reservacion seleccionarPorNumeroDeReserva(String numReserva);
    List<Reservacion> seleccionarFechasPorPlaca(String placa);

    public List<String>buscarCedulas();

    public List<Reservacion> seleccionarPorPlacaYFechas(String placa,LocalDate fehaInicio, LocalDate fechaFin );

    public List<Reservacion> seleccionarTodo();

    public LocalDate seleccionarPorPlacaUltimaFecha(String placa);
}
