package com.example.demo.api.service.to;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VehiculoTO extends RepresentationModel<VehiculoTO>{

    private Integer id;
	
	private String placa;
	
	private String modelo;
	
	private String marca;
	
	private String anio; 
	
	private String estado;
	
	private String paisOrigen;
	
	private Integer cilindraje;
	
	private BigDecimal avaluo;
	
	private BigDecimal valorPorDia;

     // Constructor
     public VehiculoTO(String placa, String modelo, String marca, String anio, String estado, BigDecimal valorPorDia) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.anio = anio;
        this.estado = estado;
        this.valorPorDia = valorPorDia;
    }
	
    
}
