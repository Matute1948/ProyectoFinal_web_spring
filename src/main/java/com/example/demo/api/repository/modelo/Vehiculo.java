package com.example.demo.api.repository.modelo;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "vehiculo")
public class Vehiculo {

	@Id
	@GeneratedValue(generator = "seq_vehiculo",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_vehiculo", sequenceName = "seq_vehiculo", allocationSize = 1)
	@Column(name = "vehi_id")
	private Integer id;

	@Column(name = "vehi_placa")
	private String placa;

	@Column(name = "vehi_modelo")
	private String modelo;

	@Column(name = "vehi_marca")
	private String marca;

	@Column(name = "vehi_anio")
	private String anio; 

	@Column(name = "vehi_estado")
	private String estado;

	@Column(name = "vehi_pais_origen")
	private String paisOrigen;

	@Column(name = "vehi_cilindraje")
	private Integer cilindraje;

	@Column(name = "vehi_avaluo")
	private BigDecimal avaluo;

	@Column(name = "vehi_idvalor_por_dia")
	private BigDecimal valorPorDia;


	@OneToMany(mappedBy = "vehiculo",  fetch = FetchType.EAGER)
	private List<Reservacion> reservasV;

	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", placa=" + placa + ", modelo=" + modelo + ", marca=" + marca + ", anio=" + anio
				+ ", estado=" + estado + ", valorPorDia=" + valorPorDia + ", paisOrigen=" + paisOrigen + ", cilindraje="
				+ cilindraje + ", avaluo=" + avaluo + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValorPorDia() {
		return valorPorDia;
	}

	public void setValorPorDia(BigDecimal valorPorDia) {
		this.valorPorDia = valorPorDia;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public BigDecimal getAvaluo() {
		return avaluo;
	}

	public void setAvaluo(BigDecimal avaluo) {
		this.avaluo = avaluo;
	}



	public List<Reservacion> getReservasV() {
		return reservasV;
	}

	public void setReservasV(List<Reservacion> reservasV) {
		this.reservasV = reservasV;
	}

}
