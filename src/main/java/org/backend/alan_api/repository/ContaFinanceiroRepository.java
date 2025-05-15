package org.backend.alan_api.repository;


import org.backend.alan_api.model.ContaFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaFinanceiroRepository extends JpaRepository<ContaFinanceiro, Long> {
    List<ContaFinanceiro> findByClienteId(Long clienteId);
    Optional<ContaFinanceiro> findByIdAndClienteId(Long id, Long clienteId);
}