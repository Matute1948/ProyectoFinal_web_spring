package com.example.demo.api.service.to;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservaDTO {

    private Integer numReserva;
    private String placa;
    private String modelo;
    private String estado;
    private LocalDate fechaDeInicio;
    private LocalDate fechaDeFin;
    private String cedulaCliente;
    public ReservaDTO(String placa, String modelo, String estado, LocalDate fechaDeInicio, LocalDate fechaDeFin,
            String cedulaCliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.estado = estado;
        this.fechaDeInicio = fechaDeInicio;
        this.fechaDeFin = fechaDeFin;
        this.cedulaCliente = cedulaCliente;
    }
	public Integer getNumReserva() {
		return numReserva;
	}
	public void setNumReserva(Integer numReserva) {
		this.numReserva = numReserva;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public LocalDate getFechaDeInicio() {
		return fechaDeInicio;
	}
	public void setFechaDeInicio(LocalDate fechaDeInicio) {
		this.fechaDeInicio = fechaDeInicio;
	}
	public LocalDate getFechaDeFin() {
		return fechaDeFin;
	}
	public void setFechaDeFin(LocalDate fechaDeFin) {
		this.fechaDeFin = fechaDeFin;
	}
	public String getCedulaCliente() {
		return cedulaCliente;
	}
	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}




}
