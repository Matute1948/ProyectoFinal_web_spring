	package com.example.demo.api.service.to;

	import java.math.BigDecimal;
	import java.time.LocalDate;

	import org.springframework.hateoas.RepresentationModel;

	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ReservacionTO extends RepresentationModel<ReservacionTO>{

		private Integer id;

		private LocalDate fechaDeInicio;
		
		private LocalDate fechaDeFin;

		private String estado;

		private String placaVehiculo;

		private String marca;

		private String modelo;

		private String nombreCliente;

		private String cedulaCliente;
		
		
		private BigDecimal valorSubtotal;
		
		private BigDecimal valorIVA;
		
		private BigDecimal valorTotalAPagar;

		

		private String numeroReseva;

		public ReservacionTO(Integer id) {
			this.id = id;
		}



	
	
	}
