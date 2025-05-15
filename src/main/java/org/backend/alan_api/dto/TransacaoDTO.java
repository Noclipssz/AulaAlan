package org.backend.alan_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {
    private Long contaId;
    private String tipo;
    private BigDecimal valor;
    private String descricao;
}