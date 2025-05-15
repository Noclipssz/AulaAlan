package org.backend.alan_api.repository;

import org.backend.alan_api.model.ContaFinanceiro;
import org.backend.alan_api.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByConta(ContaFinanceiro conta);
}