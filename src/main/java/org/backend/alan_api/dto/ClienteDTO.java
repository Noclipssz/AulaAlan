package org.backend.alan_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteDTO {
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
}