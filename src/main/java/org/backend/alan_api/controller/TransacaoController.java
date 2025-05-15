package org.backend.alan_api.controller;

import org.backend.alan_api.dto.RespostaDTO;
import org.backend.alan_api.dto.TransacaoDTO;
import org.backend.alan_api.model.Transacao;
import org.backend.alan_api.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<RespostaDTO> registrarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        try {
            Transacao transacao = transacaoService.registrarTransacao(transacaoDTO);
            return ResponseEntity.ok(new RespostaDTO("Transação registrada com sucesso", transacao));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RespostaDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<RespostaDTO> listarTransacoesPorConta(@PathVariable Long contaId) {
        try {
            List<Transacao> transacoes = transacaoService.listarTransacoesPorConta(contaId);
            return ResponseEntity.ok(new RespostaDTO("Transações da conta", transacoes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RespostaDTO(e.getMessage(), null));
        }
    }
}