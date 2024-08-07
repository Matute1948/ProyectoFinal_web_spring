package com.example.demo.api.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.api.repository.dto.ReservaRespuestaDTO;
import com.example.demo.api.service.to.ReservacionTO;
import com.example.demo.api.service.to.RespuestaReservaTO;
import com.example.demo.api.service.to.ResumenReservaTO;

public interface IReservacionService {

    public void agregar(ReservacionTO reservacion);

	public ReservacionTO buscarPorId(Integer Id);

	public ReservacionTO buscarPorNumeroDeReserva(String numeroReserva);

	public List<ReservacionTO> buscarTodo();

	
	public RespuestaReservaTO reservarVehiculo(String placa, String cedulaCliente, LocalDate fechaInicio, LocalDate fechaFin);

	public ResumenReservaTO obtenerResumenPorPlaca(String placaVehiculo);
	List<ReservacionTO> buscarReservasPorFecha(LocalDate fechaInicio, LocalDate fechaFin);

	ReservacionTO retirarVehiculoReservado(String numeroReserva);

	
    

    /*public ReservaDTO buscarPorNumReserva(Integer numFactura);
    
    public List<Reservacion> reportesValor(BigDecimal valorTotal);
    public List<ReservaDTO2> reportesClientesVIP();
    
    public List<ReservaDTO3> reporteVehiculosVIP(String mes, String anio);
    public List<VehiculosVIPDTO> reporteVehiculosVIP2(String mes, String anio);
    
    public List<ReservaDTO2> reporteClientesVIP2();
    public boolean estaDisponible(String placa, LocalDate fechaInicio,LocalDate fechaFin);
    public ReservaClienteRedirectDTO reservarVehiculo(ReservaClienteVehiculoDTO dto);*/


    public LocalDate buscarPorPlacaUltimaFecha(String placa);

    public void retirarVehiculoReservado(Integer reservaId);
}
