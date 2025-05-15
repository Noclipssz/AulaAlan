package org.backend.alan_api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transacao")
@Data
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transacao_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private ContaFinanceiro conta;

    @Column(name = "tipo", length = 10, nullable = false)
    private String tipo; // 'DEPOSITO' ou 'SAQUE'

    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @CreationTimestamp
    @Column(name = "data_transacao", updatable = false)
    private Date dataTransacao;
}