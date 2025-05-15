package org.backend.alan_api.service;

import org.backend.alan_api.dto.TransacaoDTO;
import org.backend.alan_api.exception.ContaNaoEncontradaException;
import org.backend.alan_api.model.ContaFinanceiro;
import org.backend.alan_api.model.Transacao;
import org.backend.alan_api.repository.ContaFinanceiroRepository;
import org.backend.alan_api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaFinanceiroRepository contaRepository;

    @Autowired
    private ContaService contaService;

    @Transactional
    public Transacao registrarTransacao(TransacaoDTO transacaoDTO) {
        ContaFinanceiro conta = contaRepository.findById(transacaoDTO.getContaId())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setTipo(transacaoDTO.getTipo());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDescricao(transacaoDTO.getDescricao());

        // Atualiza saldo da conta
        if ("SAQUE".equalsIgnoreCase(transacaoDTO.getTipo())) {
            contaService.sacar(conta.getId(), transacaoDTO.getValor());
        } else if ("DEPOSITO".equalsIgnoreCase(transacaoDTO.getTipo())) {
            contaService.depositar(conta.getId(), transacaoDTO.getValor());
        }

        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listarTransacoesPorConta(Long contaId) {
        ContaFinanceiro conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));
        return transacaoRepository.findByConta(conta);
    }
}