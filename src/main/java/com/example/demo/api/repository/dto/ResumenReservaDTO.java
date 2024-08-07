package com.example.demo.api.repository.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenReservaDTO {


    private BigDecimal valorSubtotal;
    private BigDecimal valorIVA;
    private BigDecimal valorTotalAPagar;

    
}
