package org.backend.alan_api.controller;

import org.backend.alan_api.dto.ContaDTO;
import org.backend.alan_api.dto.RespostaDTO;
import org.backend.alan_api.dto.TransacaoDTO;
import org.backend.alan_api.model.ContaFinanceiro;
import org.backend.alan_api.service.ContaService;
import org.backend.alan_api.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<RespostaDTO> criarConta(@RequestBody ContaDTO contaDTO) {
        try {
            ContaFinanceiro conta = contaService.criarConta(contaDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RespostaDTO("Conta criada com sucesso", conta));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RespostaDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaDTO>  buscarContaPorId(@PathVariable Long id) {
        return contaService.buscarContaPorId(id)
                .map(conta -> ResponseEntity.ok(new RespostaDTO("Conta encontrada", conta)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new RespostaDTO("Conta não encontrada", null)));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<RespostaDTO> listarContasPorCliente(@PathVariable Long clienteId) {
        List<ContaFinanceiro> contas = contaService.listarContasPorCliente(clienteId);
        return ResponseEntity.ok(new RespostaDTO("Contas do cliente", contas));
    }

    @PostMapping("/{id}/sacar")
    public ResponseEntity<RespostaDTO> sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            ContaFinanceiro conta = contaService.sacar(id, valor);

            // Registrar transação
            TransacaoDTO transacaoDTO = new TransacaoDTO();
            transacaoDTO.setContaId(id);
            transacaoDTO.setTipo("SAQUE");
            transacaoDTO.setValor(valor);
            transacaoDTO.setDescricao("Saque realizado");
            transacaoService.registrarTransacao(transacaoDTO);

            return ResponseEntity.ok(new RespostaDTO("Saque realizado com sucesso", conta));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RespostaDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/{id}/depositar")
    public ResponseEntity<RespostaDTO> depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            ContaFinanceiro conta = contaService.depositar(id, valor);

            // Registrar transação
            TransacaoDTO transacaoDTO = new TransacaoDTO();
            transacaoDTO.setContaId(id);
            transacaoDTO.setTipo("DEPOSITO");
            transacaoDTO.setValor(valor);
            transacaoDTO.setDescricao("Depósito realizado");
            transacaoService.registrarTransacao(transacaoDTO);

            return ResponseEntity.ok(new RespostaDTO("Depósito realizado com sucesso", conta));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RespostaDTO(e.getMessage(), null));
        }
    }
}