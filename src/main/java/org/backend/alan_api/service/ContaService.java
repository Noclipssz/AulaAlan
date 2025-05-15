package org.backend.alan_api.service;

import org.backend.alan_api.dto.ContaDTO;
import org.backend.alan_api.exception.ClienteNaoEncontradoException;
import org.backend.alan_api.exception.SaldoInsuficienteException;
import org.backend.alan_api.model.Cliente;
import org.backend.alan_api.model.ContaFinanceiro;
import org.backend.alan_api.repository.ClienteRepository;
import org.backend.alan_api.repository.ContaFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaFinanceiroRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public ContaFinanceiro criarConta(ContaDTO contaDTO) {
        Cliente cliente = clienteRepository.findById(contaDTO.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

        ContaFinanceiro conta = new ContaFinanceiro();
        conta.setCliente(cliente);
        conta.setSaldo(contaDTO.getSaldo() != null ? contaDTO.getSaldo() : BigDecimal.ZERO);
        conta.setLimiteCredito(contaDTO.getLimiteCredito() != null ? contaDTO.getLimiteCredito() : BigDecimal.ZERO);
        conta.setTipoConta(contaDTO.getTipoConta());

        return contaRepository.save(conta);
    }

    public Optional<ContaFinanceiro> buscarContaPorId(Long id) {
        return contaRepository.findById(id);
    }

    public List<ContaFinanceiro> listarContasPorCliente(Long clienteId) {
        return contaRepository.findByClienteId(clienteId);
    }

    @Transactional
    public ContaFinanceiro sacar(Long contaId, BigDecimal valor) {
        ContaFinanceiro conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Conta não encontrada"));

        BigDecimal saldoDisponivel = conta.getSaldo().add(conta.getLimiteCredito());

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo");
        }

        if (saldoDisponivel.compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para o saque");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }

    @Transactional
    public ContaFinanceiro depositar(Long contaId, BigDecimal valor) {
        ContaFinanceiro conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Conta não encontrada"));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo");
        }

        conta.setSaldo(conta.getSaldo().add(valor));
        return contaRepository.save(conta);
    }
}