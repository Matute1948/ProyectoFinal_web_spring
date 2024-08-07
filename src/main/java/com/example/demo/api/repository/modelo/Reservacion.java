package com.example.demo.api.repository.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservacion")
public class Reservacion {

	@Id
	@GeneratedValue(generator = "seq_reservacion",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_reservacion", sequenceName = "seq_reservacion", allocationSize = 1)
	@Column(name = "rese_id")
	private Integer id;

	@Column(name = "rese_placa_vehiculo")
	private String placaVehiculo;

	@Column(name = "rese_marca_vehiculo")
	private String marcaVehiculo;

	@Column(name = "rese_modelo_vehiculo")
	private String modeloVehiculo;

	@Column(name = "rese_cedula_cliente")
	private String cedulaCliente;

	@Column(name = "rese_nombre_cliente")
	private String nombreCliente;
	

	@Column(name = "rese_fecha_de_inicio")
	private LocalDate fechaDeInicio;

	@Column(name = "rese_fecha_de_fin")
	private LocalDate fechaDeFin;

	@Column(name = "rese_valor_subtotal")
	private BigDecimal valorSubtotal;

	@Column(name = "rese_valor_iva")
	private BigDecimal valorIVA;

	@Column(name = "rese_valor_total_a_pagar")
	private BigDecimal valorTotalAPagar;

	@Column(name = "rese_estado")
	private String estado;

	@Column(name = "rese_num_reserva")
	private String numeroReserva;

	//CLAVES FORANEAS
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rese_id_cliente")
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rese_id_vehiculo")
	private Vehiculo vehiculo;

	@OneToOne(mappedBy = "reservacion", cascade = CascadeType.ALL) 
	//Va ciudadano porque es con el que estamos mapeanod en la otra ENTIDAD
	private Reporte reporte;
	//-----

	@OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;



}
