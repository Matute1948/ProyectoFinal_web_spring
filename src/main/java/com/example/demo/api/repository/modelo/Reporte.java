package com.example.demo.api.repository.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reporte")
public class Reporte {
	
	@Id
	@GeneratedValue(generator = "seq_reporte",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_reporte", sequenceName = "seq_reporte", allocationSize = 1)
	@Column(name = "rep_id")
	private Integer id;
	
	@Column(name = "rep_numero_tarjeta")
	private String numeroTarjeta;
	
	@Column(name = "rep_fecha_de_cobro")
	private LocalDate fechaDeCobro;
	
	@OneToOne
	@JoinColumn(name = "rep_id_reserva")
	private Reservacion reservacion;

	@Override
	public String toString() {
		return "Reporte [id=" + id + ", numeroTarjeta=" + numeroTarjeta
				+ ", fechaDeCobro=" + fechaDeCobro + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public LocalDate getFechaDeCobro() {
		return fechaDeCobro;
	}

	public void setFechaDeCobro(LocalDate fechaDeCobro) {
		this.fechaDeCobro = fechaDeCobro;
	}

	public Reservacion getReserva() {
		return reservacion;
	}

	public void setReserva(Reservacion reservacion) {
		this.reservacion = reservacion;
	}

	

}
