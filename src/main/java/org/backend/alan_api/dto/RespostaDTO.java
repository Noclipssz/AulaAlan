package org.backend.alan_api.dto;

import lombok.Data;

@Data
public class RespostaDTO {
    private String mensagem;
    private Object dados;

    public RespostaDTO(String mensagem, Object dados) {
        this.mensagem = mensagem;
        this.dados = dados;
    }
}