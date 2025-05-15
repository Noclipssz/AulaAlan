package org.backend.alan_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContaDTO {
    private Long clienteId;
    private BigDecimal saldo;
    private BigDecimal limiteCredito;
    private String tipoConta;
}