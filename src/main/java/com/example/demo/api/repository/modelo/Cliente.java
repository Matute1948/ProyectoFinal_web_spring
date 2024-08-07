package com.example.demo.api.repository.modelo;

import java.time.LocalDate;
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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1)
	@GeneratedValue(generator = "seq_cliente", strategy = GenerationType.SEQUENCE)
	@Column(name = "clie_id")
	private Integer id;
	
	@Column(name = "clie_cedula")
	private String cedula;
	
	@Column(name = "clie_nombre")
	private String nombre;
	
	@Column(name = "clie_apellido")
	private String apellido;
	
	@Column(name = "clie_fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@Column(name = "clie_genero")
	private String genero;
	
	@Column(name = "clie_registro")
	private String registro;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	private List<Reservacion> reservasC;
	
	//SET Y GET

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}
	
	public List<Reservacion> getReservasC() {
		return reservasC;
	}

	public void setReservasC(List<Reservacion> reservasC) {
		this.reservasC = reservasC;
	}
	
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", fechaDeNacimiento=" + fechaNacimiento + ", genero=" + genero + ", registro=" + registro + "]";
	}

}
