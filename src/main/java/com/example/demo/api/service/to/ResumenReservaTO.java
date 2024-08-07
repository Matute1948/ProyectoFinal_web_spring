package com.example.demo.api.service.to;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenReservaTO extends RepresentationModel<ResumenReservaTO>{

    
    private BigDecimal valorSubtotal;
    private BigDecimal valorIVA;
    private BigDecimal valorTotalAPagar;

    
}
