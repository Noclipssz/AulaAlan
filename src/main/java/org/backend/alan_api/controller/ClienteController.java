package org.backend.alan_api.controller;

import org.backend.alan_api.dto.ClienteDTO;
import org.backend.alan_api.dto.RespostaDTO;
import org.backend.alan_api.model.Cliente;
import org.backend.alan_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<RespostaDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.criarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RespostaDTO("Cliente criado com sucesso", cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RespostaDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<RespostaDTO> buscarPorCpf(@PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
        return cliente.map(value -> ResponseEntity.ok(new RespostaDTO("Cliente encontrado", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new RespostaDTO("Cliente não encontrado", null)));
    }

    @GetMapping
    public ResponseEntity<RespostaDTO> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(new RespostaDTO("Lista de clientes", clientes));
    }
}