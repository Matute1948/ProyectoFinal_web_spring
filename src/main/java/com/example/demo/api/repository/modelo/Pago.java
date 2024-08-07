package com.example.demo.api.repository.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pago")
public class Pago {
    @Id
	@GeneratedValue(generator = "seq_pago",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_pago", sequenceName = "seq_pago", allocationSize = 1)
	@Column(name = "pago_id")
    private Integer id;

    @Column(name = "pago_num_tarjeta")
    private String numeroTarjeta;

    @Column(name = "pago_fecha_cobro")

    private LocalDate fechaCobro;

    @Column(name = "pago_numero_reserva")

    private String numeroReserva;
@ManyToOne
@JoinColumn(name = "rese_id_reservacion") // Cambia esto para que coincida con la columna de clave foránea en la tabla de pagos
private Reservacion reservacion;

}